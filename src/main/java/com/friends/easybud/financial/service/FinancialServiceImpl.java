package com.friends.easybud.financial.service;

import com.friends.easybud.card.domain.Card;
import com.friends.easybud.card.repository.CardRepository;
import com.friends.easybud.financial.dto.FinancialResponse.AvailableFundsDto;
import com.friends.easybud.financial.dto.FinancialResponse.FinancialStatementDto;
import com.friends.easybud.financial.dto.FinancialResponse.IncomeStatementDto;
import com.friends.easybud.transaction.domain.AccountName;
import com.friends.easybud.transaction.repository.AccountCustomRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public AvailableFundsDto getAvailableFunds() {
        Long memberId = 1L;
        BigDecimal cash = getSumBySecondaryCategory("현금", memberId);
        BigDecimal ordinaryDeposits = getSumBySecondaryCategory("보통예금", memberId);

        List<Card> cards = cardRepository.findByMemberId(memberId);
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
        LocalDate nextPaymentDate = referenceDate.withDayOfMonth(card.getPaymentDate());
        if (nextPaymentDate.isBefore(referenceDate) || nextPaymentDate.isEqual(referenceDate)) {
            nextPaymentDate = nextPaymentDate.plusMonths(1);
        }
        return nextPaymentDate;
    }


    private LocalDate findUsageStartDate(Card card, LocalDate nextPaymentDate) {
        LocalDate usageStartDate = nextPaymentDate.minusMonths(1).withDayOfMonth(card.getStartDate());

        if (card.getEndDate() >= card.getPaymentDate()) {
            usageStartDate = usageStartDate.minusMonths(1);
        }

        return usageStartDate;
    }

    private LocalDate findUsageEndDate(Card card, LocalDate nextPaymentDate) {
        LocalDate usageEndDate;

        if (card.getEndDate() < card.getPaymentDate()) {
            usageEndDate = nextPaymentDate.withDayOfMonth(card.getEndDate());
        } else {
            usageEndDate = nextPaymentDate.minusMonths(1).withDayOfMonth(card.getEndDate());
        }

        return usageEndDate;
    }

    @Override
    public FinancialStatementDto getFinancialStatement() {
        Long memberId = 1L;
        BigDecimal totalAssets = getSumByPrimaryCategory("자산", memberId);
        BigDecimal totalLiabilities = getSumByPrimaryCategory("부채", memberId);
        BigDecimal netAssets = totalAssets.subtract(totalLiabilities);

        return FinancialStatementDto.builder()
                .totalAssets(totalAssets)
                .totalLiabilities(totalLiabilities)
                .netAssets(netAssets)
                .initialNetAssetDefined(accountCustomRepository.existsInitialNetAsset(memberId))
                .build();
    }

    @Override
    public IncomeStatementDto getIncomeStatement(LocalDateTime startDate, LocalDateTime endDate) {
        Long memberId = 1L;
        BigDecimal revenue = getSumOfRevenueAccounts(memberId, startDate, endDate);
        BigDecimal expense = getSumOfExpenseAccounts(memberId, startDate, endDate);

        return IncomeStatementDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .revenue(revenue)
                .expense(expense).build();
    }

    private BigDecimal getSumByPrimaryCategory(String category, Long memberId) {
        return accountCustomRepository.sumByPrimaryCategoryAndMember(category, memberId)
                .orElse(BigDecimal.ZERO);
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
