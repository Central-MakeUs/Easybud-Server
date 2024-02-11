package com.friends.easybud.financial.service;

import com.friends.easybud.card.domain.Card;
import com.friends.easybud.card.repository.CardRepository;
import com.friends.easybud.financial.dto.AccountInfo;
import com.friends.easybud.financial.dto.FinancialResponse.AvailableFundsDto;
import com.friends.easybud.financial.dto.FinancialResponse.FinancialStatementDto;
import com.friends.easybud.financial.dto.FinancialResponse.IncomeStatementDto;
import com.friends.easybud.financial.dto.FinancialResponse.IncomeStatementSummaryDto;
import com.friends.easybud.financial.dto.FinancialResponse.ProfitLossDto;
import com.friends.easybud.member.domain.Member;
import com.friends.easybud.transaction.domain.AccountName;
import com.friends.easybud.transaction.repository.AccountCustomRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FinancialServiceImpl implements FinancialService {

    private final CardRepository cardRepository;
    private final AccountCustomRepository accountCustomRepository;

    @Override
    public AvailableFundsDto getAvailableFunds(Member member) {
        BigDecimal cash = getSumBySecondaryCategory("현금", member.getId());
        BigDecimal ordinaryDeposits = getSumBySecondaryCategory("보통예금", member.getId());

        List<Card> cards = cardRepository.findByMemberId(member.getId());
        BigDecimal cardPayment = cards.stream()
                .map(card -> calculateUpcomingCardPayment(card, LocalDate.now()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal availableFunds = cash.add(ordinaryDeposits).subtract(cardPayment);

        return AvailableFundsDto.builder()
                .cash(cash)
                .ordinaryDeposits(ordinaryDeposits)
                .scheduledDisbursements(cardPayment)
                .availableFunds(availableFunds).build();
    }

    private BigDecimal getSumBySecondaryCategory(String category, Long memberId) {
        return accountCustomRepository.sumBySecondaryCategoryAndMember(category, memberId)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal calculateUpcomingCardPayment(Card card, LocalDate today) {
        LocalDate nextPaymentDate = findNextPaymentDate(card, today);
        LocalDate usageStartDate = findUsageStartDate(card, nextPaymentDate);
        LocalDate usageEndDate = findUsageEndDate(card, nextPaymentDate);

        return accountCustomRepository.sumByCardAndDateRange(
                card.getId(),
                usageStartDate.atStartOfDay(),
                usageEndDate.atTime(23, 59, 59)
        ).orElse(BigDecimal.ZERO);
    }

    private LocalDate findNextPaymentDate(Card card, LocalDate referenceDate) {
        LocalDate nextPaymentDate;
        if (card.getPaymentDate() == -1) {
            nextPaymentDate = referenceDate.plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        } else {
            nextPaymentDate = referenceDate.withDayOfMonth(card.getPaymentDate());
            if (nextPaymentDate.isBefore(referenceDate) || nextPaymentDate.isEqual(referenceDate)) {
                nextPaymentDate = nextPaymentDate.plusMonths(1);
            }
        }
        return nextPaymentDate;
    }


    private LocalDate findUsageStartDate(Card card, LocalDate nextPaymentDate) {
        LocalDate usageStartDate;
        if (card.getStartDate() == -1) {
            usageStartDate = nextPaymentDate.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        } else {
            usageStartDate = nextPaymentDate.minusMonths(1).withDayOfMonth(card.getStartDate());
        }

        boolean isEndDateAfterOrEqualPaymentDate =
                card.getEndDate() >= card.getPaymentDate() || card.getEndDate() == -1;
        boolean isPaymentDateLastDayOfMonth = card.getPaymentDate() == -1;

        if (isEndDateAfterOrEqualPaymentDate || isPaymentDateLastDayOfMonth) {
            usageStartDate = usageStartDate.minusMonths(1);
        }

        return usageStartDate;
    }

    private LocalDate findUsageEndDate(Card card, LocalDate nextPaymentDate) {
        LocalDate usageEndDate;

        if (card.getEndDate() == -1) {
            usageEndDate = nextPaymentDate.with(TemporalAdjusters.lastDayOfMonth());
        } else {
            if (card.getPaymentDate() == -1) {
                usageEndDate = nextPaymentDate.minusMonths(1).withDayOfMonth(card.getEndDate());
            } else if (card.getEndDate() < card.getPaymentDate()) {
                usageEndDate = nextPaymentDate.withDayOfMonth(card.getEndDate());
            } else {
                usageEndDate = nextPaymentDate.minusMonths(1).withDayOfMonth(card.getEndDate());
            }
        }

        return usageEndDate;
    }


    @Override
    public FinancialStatementDto getFinancialStatement(Member member) {
        BigDecimal totalAssets = getSumByPrimaryCategory(AccountName.ASSET, member.getId());
        BigDecimal totalLiabilities = getSumByPrimaryCategory(AccountName.LIABILITY, member.getId());
        BigDecimal netAssets = totalAssets.subtract(totalLiabilities);

        return FinancialStatementDto.builder()
                .totalAssets(totalAssets)
                .totalLiabilities(totalLiabilities)
                .netAssets(netAssets)
                .initialNetAssetDefined(accountCustomRepository.existsInitialNetAsset(member.getId()))
                .build();
    }

    @Override
    public IncomeStatementDto getIncomeStatement(Member member, LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal revenue = getSumOfRevenueAccounts(member.getId(), startDate, endDate);
        BigDecimal expense = getSumOfExpenseAccounts(member.getId(), startDate, endDate);

        LocalDateTime lastMonthStart = startDate.minusMonths(1).withDayOfMonth(1);
        LocalDateTime lastMonthEnd = lastMonthStart.plusMonths(1).minusDays(1);

        BigDecimal lastMonthRevenue = getSumOfRevenueAccounts(member.getId(), lastMonthStart, lastMonthEnd);
        BigDecimal lastMonthExpense = getSumOfExpenseAccounts(member.getId(), lastMonthStart, lastMonthEnd);

        BigDecimal revenueChangePercentage = calculateChangePercentage(revenue, lastMonthRevenue);
        BigDecimal expenseChangePercentage = calculateChangePercentage(expense, lastMonthExpense);

        BigDecimal expensePercentage = BigDecimal.ZERO;
        BigDecimal revenuePercentage = BigDecimal.ZERO;

        if (revenue.compareTo(BigDecimal.ZERO) > 0 && expense.compareTo(BigDecimal.ZERO) > 0) {
            expensePercentage = expense.divide(revenue.add(expense), 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(100));
            revenuePercentage = revenue.divide(revenue.add(expense), 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(100));
        }

        return IncomeStatementDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .revenue(revenue)
                .expense(expense)
                .expensePercentage(expensePercentage)
                .revenuePercentage(revenuePercentage)
                .expenseChangePercentage(expenseChangePercentage)
                .revenueChangePercentage(revenueChangePercentage)
                .build();
    }

    private BigDecimal calculateChangePercentage(BigDecimal current, BigDecimal previous) {
        if (previous.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO; // 이전 값이 0이면 변화율을 계산할 수 없음
        }
        return current.subtract(previous)
                .divide(previous, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100));
    }

    @Override
    public IncomeStatementSummaryDto getIncomeStatementSummary(Member member, LocalDateTime startDate,
                                                               LocalDateTime endDate) {
        BigDecimal revenue = getSumOfRevenueAccounts(member.getId(), startDate, endDate);
        BigDecimal expense = getSumOfExpenseAccounts(member.getId(), startDate, endDate);

        return IncomeStatementSummaryDto.builder()
                .revenue(revenue)
                .expense(expense)
                .profitLoss(revenue.subtract(expense))
                .build();
    }

    @Override
    public List<ProfitLossDto> getDailyIncomeStatementSummaries(Member member, int year, int month) {
        List<ProfitLossDto> profitLossDtos = new ArrayList<>();
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = LocalDate.of(year, month, day);
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(23, 59, 59);

            BigDecimal revenue = getSumOfRevenueAccounts(member.getId(), startOfDay, endOfDay);
            BigDecimal expense = getSumOfExpenseAccounts(member.getId(), startOfDay, endOfDay);

            ProfitLossDto profitLossDto = ProfitLossDto.builder()
                    .date(date)
                    .profitLoss(revenue.subtract(expense))
                    .build();

            profitLossDtos.add(profitLossDto);
        }

        return profitLossDtos;
    }

    private BigDecimal getSumByPrimaryCategory(AccountName accountName, Long memberId) {
        List<AccountInfo> accountInfos = accountCustomRepository.getAccountInfosByAccountNameAndMember(accountName,
                memberId);

        return accountInfos.stream()
                .map(accountInfo -> {
                    if (accountInfo.isDecrease()) {
                        return accountInfo.getAmount().negate();
                    } else {
                        return accountInfo.getAmount();
                    }
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private BigDecimal getSumOfRevenueAccounts(Long memberId, LocalDateTime startDate, LocalDateTime endDate) {
        return accountCustomRepository.sumOfAccountsByTypeAndMember(AccountName.REVENUE, memberId, startDate, endDate)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal getSumOfExpenseAccounts(Long memberId, LocalDateTime startDate, LocalDateTime endDate) {
        return accountCustomRepository.sumOfAccountsByTypeAndMember(AccountName.EXPENSE, memberId, startDate, endDate)
                .orElse(BigDecimal.ZERO);
    }

}
