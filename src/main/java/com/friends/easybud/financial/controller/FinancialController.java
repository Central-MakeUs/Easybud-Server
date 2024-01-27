package com.friends.easybud.financial.controller;

import static com.friends.easybud.financial.dto.FinancialRequest.FinancialDateDto;
import static com.friends.easybud.financial.dto.FinancialResponse.AvailableFundsDto;
import static com.friends.easybud.financial.dto.FinancialResponse.FinancialStatementDto;
import static com.friends.easybud.financial.dto.FinancialResponse.IncomeStatementDto;

import com.friends.easybud.financial.service.FinancialService;
import com.friends.easybud.global.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/financials")
@RestController
@Tag(name = "Financial API", description = "장부 API")
public class FinancialController {

    private final FinancialService financialService;

    @Operation(summary = "가용자금 조회", description = "사용자의 가용자금을 조회합니다.")
    @GetMapping("/available-funds")
    public ResponseDto<AvailableFundsDto> getAvailableFunds() {
        return ResponseDto.onSuccess(financialService.getAvailableFunds());
    }

    @Operation(summary = "재무 상태 조회", description = "사용자의 재무 상태를 조회합니다.")
    @GetMapping("/financial-statement")
    public ResponseDto<FinancialStatementDto> getFinancialStatement() {
        return ResponseDto.onSuccess(financialService.getFinancialStatement());
    }

    @Operation(summary = "손익현황 조회", description = "사용자의 손익현황을 조회합니다.")
    @GetMapping("/income-statement")
    public ResponseDto<IncomeStatementDto> getIncomeStatement(@RequestBody FinancialDateDto request) {
        LocalDateTime startOfDay = request.getStartDate().atStartOfDay();
        LocalDateTime endOfDay = request.getEndDate().atTime(23, 59, 59);
        return ResponseDto.onSuccess(financialService.getIncomeStatement(startOfDay, endOfDay));
    }

}
