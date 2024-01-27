package com.friends.easybud.financial.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class FinancialResponse {

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "가용자금 조회 DTO")
    public static class AvailableFundsDto {

        @Schema(description = "현금")
        private BigDecimal cash;

        @Schema(description = "보통예금")
        private BigDecimal ordinaryDeposits;

        @Schema(description = "카드대금")
        private BigDecimal scheduledDisbursements;

        @Schema(description = "가용자금")
        private BigDecimal availableFunds;

    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "재무 상태 조회 DTO")
    public static class FinancialStatementDto {

        @Schema(description = "자산")
        private BigDecimal totalAssets;

        @Schema(description = "부채")
        private BigDecimal totalLiabilities;

        @Schema(description = "자본")
        private BigDecimal netAssets;

    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "수익현황 조회 DTO")
    public static class IncomeStatementDto {

        @Schema(description = "시작 날짜")
        private LocalDateTime startDate;

        @Schema(description = "종료 날짜")
        private LocalDateTime endDate;

        @Schema(description = "수익")
        private BigDecimal revenue;

        @Schema(description = "비용")
        private BigDecimal expense;

    }

}
