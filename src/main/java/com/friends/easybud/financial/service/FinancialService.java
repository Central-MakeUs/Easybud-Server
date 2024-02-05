package com.friends.easybud.financial.service;

import com.friends.easybud.financial.dto.FinancialResponse.AvailableFundsDto;
import com.friends.easybud.financial.dto.FinancialResponse.FinancialStatementDto;
import com.friends.easybud.financial.dto.FinancialResponse.IncomeStatementDto;
import com.friends.easybud.financial.dto.FinancialResponse.IncomeStatementSummaryDto;
import com.friends.easybud.financial.dto.FinancialResponse.ProfitLossDto;
import com.friends.easybud.member.domain.Member;
import java.time.LocalDateTime;
import java.util.List;

public interface FinancialService {

    AvailableFundsDto getAvailableFunds(Member member);

    FinancialStatementDto getFinancialStatement(Member member);

    IncomeStatementDto getIncomeStatement(Member member, LocalDateTime startDate, LocalDateTime endDate);

    IncomeStatementSummaryDto getIncomeStatementSummary(Member member, LocalDateTime startDate, LocalDateTime endDate);

    List<ProfitLossDto> getDailyIncomeStatementSummaries(Member member, int year, int month);

}
