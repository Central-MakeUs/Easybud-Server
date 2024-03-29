package com.friends.easybud.financial.controller;

import static com.friends.easybud.financial.dto.FinancialResponse.AvailableFundsDto;
import static com.friends.easybud.financial.dto.FinancialResponse.FinancialStatementDto;
import static com.friends.easybud.financial.dto.FinancialResponse.IncomeStatementDto;

import com.friends.easybud.financial.converter.FinancialConverter;
import com.friends.easybud.financial.dto.FinancialResponse.IncomeStatementSummaryDto;
import com.friends.easybud.financial.dto.FinancialResponse.ProfitLossListDto;
import com.friends.easybud.financial.service.FinancialService;
import com.friends.easybud.global.annotation.ApiErrorCodeExample;
import com.friends.easybud.global.annotation.AuthMember;
import com.friends.easybud.global.response.ResponseDto;
import com.friends.easybud.global.response.code.ErrorStatus;
import com.friends.easybud.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/financials")
@RestController
@ApiResponse(responseCode = "2000", description = "성공")
@Tag(name = "Financial API", description = "장부 API")
public class FinancialController {

    private final FinancialService financialService;

    @ApiErrorCodeExample({
            ErrorStatus.MEMBER_NOT_FOUND,
            ErrorStatus.TOKEN_INVALID,
            ErrorStatus.TOKEN_EXPIRED,
            ErrorStatus.TOKEN_UNSUPPORTED,
            ErrorStatus.TOKEN_CLAIMS_EMPTY,
            ErrorStatus.AUTHENTICATION_REQUIRED,
            ErrorStatus._INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "가용자금 조회", description = "사용자의 가용자금을 조회합니다.")
    @GetMapping("/available-funds")
    public ResponseDto<AvailableFundsDto> getAvailableFunds(@AuthMember Member member) {
        return ResponseDto.onSuccess(financialService.getAvailableFunds(member));
    }

    @ApiErrorCodeExample({
            ErrorStatus.MEMBER_NOT_FOUND,
            ErrorStatus.TOKEN_INVALID,
            ErrorStatus.TOKEN_EXPIRED,
            ErrorStatus.TOKEN_UNSUPPORTED,
            ErrorStatus.TOKEN_CLAIMS_EMPTY,
            ErrorStatus.AUTHENTICATION_REQUIRED,
            ErrorStatus._INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "재무 상태 조회", description = "사용자의 재무 상태를 조회합니다.")
    @GetMapping("/financial-statement")
    public ResponseDto<FinancialStatementDto> getFinancialStatement(@AuthMember Member member) {
        return ResponseDto.onSuccess(financialService.getFinancialStatement(member));
    }

    @ApiErrorCodeExample({
            ErrorStatus.MEMBER_NOT_FOUND,
            ErrorStatus.TOKEN_INVALID,
            ErrorStatus.TOKEN_EXPIRED,
            ErrorStatus.TOKEN_UNSUPPORTED,
            ErrorStatus.TOKEN_CLAIMS_EMPTY,
            ErrorStatus.AUTHENTICATION_REQUIRED,
            ErrorStatus._INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "손익현황 조회", description = "사용자의 손익현황을 조회합니다.")
    @Parameter(name = "startDate", example = "2024-02-01")
    @Parameter(name = "endDate", example = "2024-02-02")
    @GetMapping("/income-statement")
    public ResponseDto<IncomeStatementDto> getIncomeStatement(@AuthMember Member member,
                                                              @RequestParam LocalDate startDate,
                                                              @RequestParam LocalDate endDate) {
        LocalDateTime startOfDay = startDate.atStartOfDay();
        LocalDateTime endOfDay = endDate.atTime(23, 59, 59);
        return ResponseDto.onSuccess(financialService.getIncomeStatement(member, startOfDay, endOfDay));
    }

    @ApiErrorCodeExample({
            ErrorStatus.MEMBER_NOT_FOUND,
            ErrorStatus.TOKEN_INVALID,
            ErrorStatus.TOKEN_EXPIRED,
            ErrorStatus.TOKEN_UNSUPPORTED,
            ErrorStatus.TOKEN_CLAIMS_EMPTY,
            ErrorStatus.AUTHENTICATION_REQUIRED,
            ErrorStatus._INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "월간 손익현황 요약 조회", description = "특정 연도와 월에 대한 손익현황을 조회합니다.")
    @Parameter(name = "year", example = "2024")
    @Parameter(name = "month", example = "2")
    @GetMapping("/income-statement/summary/monthly")
    public ResponseDto<IncomeStatementSummaryDto> getMonthlyIncomeStatementSummary(@AuthMember Member member,
                                                                                   @RequestParam int year,
                                                                                   @RequestParam int month) {
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endOfMonth = YearMonth.of(year, month)
                .atEndOfMonth()
                .atTime(LocalTime.MAX)
                .withNano(0);
        return ResponseDto.onSuccess(financialService.getIncomeStatementSummary(member, startOfMonth, endOfMonth));
    }

    @ApiErrorCodeExample({
            ErrorStatus.MEMBER_NOT_FOUND,
            ErrorStatus.TOKEN_INVALID,
            ErrorStatus.TOKEN_EXPIRED,
            ErrorStatus.TOKEN_UNSUPPORTED,
            ErrorStatus.TOKEN_CLAIMS_EMPTY,
            ErrorStatus.AUTHENTICATION_REQUIRED,
            ErrorStatus._INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "월간 일별 손익현황 요약 조회", description = "특정 연도와 월에 대해 해당 월의 모든 일자별 손익현황을 조회합니다.")
    @Parameter(name = "year", example = "2024")
    @Parameter(name = "month", example = "2")
    @GetMapping("/income-statement/summary/daily")
    public ResponseDto<ProfitLossListDto> getDailyIncomeStatementSummary(@AuthMember Member member,
                                                                         @RequestParam int year,
                                                                         @RequestParam int month) {
        return ResponseDto.onSuccess(
                FinancialConverter.toProfitLossListDto(
                        financialService.getDailyIncomeStatementSummaries(member, year, month)));
    }


}
