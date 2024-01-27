package com.friends.easybud.financial.controller;

import static com.friends.easybud.financial.dto.FinancialResponse.AvailableFundsDto;
import static com.friends.easybud.financial.dto.FinancialResponse.FinancialStatementDto;
import static com.friends.easybud.financial.dto.FinancialResponse.IncomeStatementDto;

import com.friends.easybud.global.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/financials")
@RestController
@Tag(name = "FinancialReport API", description = "장부 API")
public class FinancialController {

    @Operation(summary = "가용자금 조회", description = "사용자의 가용자금을 조회합니다.")
    @PostMapping("/available-funds")
    public ResponseDto<AvailableFundsDto> getAvailableFunds() {
        return ResponseDto.onSuccess(null);
    }

    @Operation(summary = "재무 상태 조회", description = "사용자의 재무 상태를 조회합니다.")
    @PostMapping("/financial-statement")
    public ResponseDto<FinancialStatementDto> getFinancialStatement() {
        return ResponseDto.onSuccess(null);
    }

    @Operation(summary = "손익현황 조회", description = "사용자의 손익현황을 조회합니다.")
    @PostMapping("/income-statement")
    public ResponseDto<IncomeStatementDto> getIncomeStatement() {
        return ResponseDto.onSuccess(null);
    }

}