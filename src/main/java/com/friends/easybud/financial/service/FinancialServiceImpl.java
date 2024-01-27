package com.friends.easybud.financial.service;

import com.friends.easybud.financial.dto.FinancialResponse.AvailableFundsDto;
import com.friends.easybud.financial.dto.FinancialResponse.FinancialStatementDto;
import com.friends.easybud.financial.dto.FinancialResponse.IncomeStatementDto;
import com.friends.easybud.transaction.repository.AccountRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FinancialServiceImpl implements FinancialService {

    private final AccountRepository accountRepository;

    @Override
    public AvailableFundsDto getAvailableFunds() {
        Long memberId = 1L;
        BigDecimal cash = accountRepository.sumOfAccountsBySecondaryCategoryContentAndMemberId(
                "현금",
                memberId
        );
        if (cash == null) {
            cash = BigDecimal.ZERO;
        }

        BigDecimal ordinaryDeposits = accountRepository.sumOfAccountsBySecondaryCategoryContentAndMemberId(
                "보통예금",
                memberId
        );
        if (ordinaryDeposits == null) {
            ordinaryDeposits = BigDecimal.ZERO;
        }

        BigDecimal scheduledDisbursements = accountRepository.sumOfAccountsBySecondaryCategoryContentAndMemberId(
                "카드대금",
                memberId
        );
        if (scheduledDisbursements == null) {
            scheduledDisbursements = BigDecimal.ZERO;
        }

        BigDecimal availableFunds = cash.add(ordinaryDeposits).subtract(scheduledDisbursements);

        return AvailableFundsDto.builder()
                .cash(cash)
                .ordinaryDeposits(ordinaryDeposits)
                .scheduledDisbursements(scheduledDisbursements)
                .availableFunds(availableFunds).build();
    }

    @Override
    public FinancialStatementDto getFinancialStatement() {
        Long memberId = 1L;
        BigDecimal totalAssets = accountRepository.sumOfAccountsByPrimaryCategoryContentAndMemberId(
                "자산",
                memberId
        );

        if (totalAssets == null) {
            totalAssets = BigDecimal.ZERO;
        }

        BigDecimal totalLiabilities = accountRepository.sumOfAccountsByPrimaryCategoryContentAndMemberId(
                "부채",
                memberId
        );
        if (totalLiabilities == null) {
            totalLiabilities = BigDecimal.ZERO;
        }

        BigDecimal netAssets = totalAssets.subtract(totalLiabilities);
        return FinancialStatementDto.builder()
                .totalAssets(totalAssets)
                .totalLiabilities(totalLiabilities)
                .netAssets(netAssets).build();
    }

    @Override
    public IncomeStatementDto getIncomeStatement(LocalDateTime startDate, LocalDateTime endDate) {
        Long memberId = 1L;
        BigDecimal revenue = accountRepository.sumOfRevenueAccountsByMemberIdAndTransactionDateRangeWithLike(
                memberId,
                startDate,
                endDate
        );

        BigDecimal expense = accountRepository.sumOfExpenseAccountsByMemberIdAndTransactionDateRange(
                memberId,
                startDate,
                endDate
        );

        return IncomeStatementDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .revenue(revenue)
                .expense(expense).build();
    }
}
