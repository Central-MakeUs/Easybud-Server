package com.friends.easybud.financial.service;

import com.friends.easybud.financial.dto.FinancialResponse.AvailableFundsDto;
import com.friends.easybud.financial.dto.FinancialResponse.FinancialStatementDto;
import com.friends.easybud.financial.dto.FinancialResponse.IncomeStatementDto;
import com.friends.easybud.financial.dto.FinancialResponse.IncomeStatementSummaryDto;
import com.friends.easybud.financial.dto.FinancialResponse.ProfitLossDto;
import java.time.LocalDateTime;
import java.util.List;

public interface FinancialService {

    AvailableFundsDto getAvailableFunds();

    FinancialStatementDto getFinancialStatement();

    IncomeStatementDto getIncomeStatement(LocalDateTime startDate, LocalDateTime endDate);

    IncomeStatementSummaryDto getIncomeStatementSummary(LocalDateTime startDate, LocalDateTime endDate);

    List<ProfitLossDto> getDailyIncomeStatementSummaries(int year, int month);

}
