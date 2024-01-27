package com.friends.easybud.financial.service;

import com.friends.easybud.financial.dto.FinancialResponse.AvailableFundsDto;
import com.friends.easybud.financial.dto.FinancialResponse.FinancialStatementDto;
import com.friends.easybud.financial.dto.FinancialResponse.IncomeStatementDto;
import com.friends.easybud.transaction.repository.AccountRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
    가용자금 = 현금 + 보통예금 - 카드대금

    자산 = 모든 자산 카테고리 계정 잔액의 합
    부채 = 모든 부채 카테고리 계정 잔액의 합
    자본 = 자산 - 부채

    수익/비용 = 특정 기간동안 수익 카테고리 계정 거래액의 합
*/

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FinancialServiceImpl implements FinancialService {

    private final AccountRepository accountRepository;

    @Override
    public AvailableFundsDto getAvailableFunds() {
        Long memberId = 1L;
        BigDecimal cash = accountRepository.sumOfAccountsBySecondaryCategoryContentAndMemberId("현금", memberId);
        if (cash == null) {
            cash = BigDecimal.ZERO;
        }

        BigDecimal ordinaryDeposits = accountRepository.sumOfAccountsBySecondaryCategoryContentAndMemberId("보통예금",
                memberId);
        if (ordinaryDeposits == null) {
            ordinaryDeposits = BigDecimal.ZERO;
        }

        BigDecimal scheduledDisbursements = accountRepository.sumOfAccountsBySecondaryCategoryContentAndMemberId("카드대금",
                memberId);
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
        BigDecimal totalAssets = accountRepository.sumOfAccountsByPrimaryCategoryContentAndMemberId("자산", memberId);
        BigDecimal totalLiabilities = accountRepository.sumOfAccountsByPrimaryCategoryContentAndMemberId("부채",
                memberId);
        BigDecimal netAssets = totalAssets.subtract(totalLiabilities);
        return FinancialStatementDto.builder()
                .totalAssets(totalAssets)
                .totalLiabilities(totalLiabilities)
                .netAssets(netAssets).build();
    }

    @Override
    public IncomeStatementDto getIncomeStatement() {

        return null;
    }
}
