package com.friends.easybud.financial.service;

import com.friends.easybud.financial.dto.FinancialResponse.AvailableFundsDto;
import com.friends.easybud.financial.dto.FinancialResponse.FinancialStatementDto;
import com.friends.easybud.financial.dto.FinancialResponse.IncomeStatementDto;
import java.time.LocalDateTime;

public interface FinancialService {

    AvailableFundsDto getAvailableFunds();

    FinancialStatementDto getFinancialStatement();

    IncomeStatementDto getIncomeStatement(LocalDateTime startDate, LocalDateTime endDate);

}
