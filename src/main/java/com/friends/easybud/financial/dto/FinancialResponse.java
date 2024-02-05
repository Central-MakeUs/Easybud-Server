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

        @Schema(description = "현금", example = "100000")
        private BigDecimal cash;

        @Schema(description = "보통예금", example = "30000000")
        private BigDecimal ordinaryDeposits;

        @Schema(description = "카드대금", example = "600000")
        private BigDecimal scheduledDisbursements;

        @Schema(description = "가용자금", example = "29500000")
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

        @Schema(description = "자산", example = "500000000")
        private BigDecimal totalAssets;

        @Schema(description = "부채", example = "10000000")
        private BigDecimal totalLiabilities;

        @Schema(description = "자본", example = "490000000")
        private BigDecimal netAssets;

        @Schema(description = "기초순자산 설정 여부", example = "false")
        private boolean initialNetAssetDefined;

    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "손익현황 조회 DTO")
    public static class IncomeStatementDto {

        @Schema(description = "시작 날짜", example = "2024-01-01T00:00:00")
        private LocalDateTime startDate;

        @Schema(description = "종료 날짜", example = "2024-01-27T17:30:20")
        private LocalDateTime endDate;

        @Schema(description = "수익", example = "5000000")
        private BigDecimal revenue;

        @Schema(description = "비용", example = "1000000")
        private BigDecimal expense;

        @Schema(description = "수익 백분율", example = "80")
        private BigDecimal revenuePercentage;

        @Schema(description = "비용 백분율", example = "20")
        private BigDecimal expensePercentage;

    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "손익현황 요약 조회 DTO")
    public static class IncomeStatementSummaryDto {

        @Schema(description = "수익", example = "5000000")
        private BigDecimal revenue;

        @Schema(description = "비용", example = "1000000")
        private BigDecimal expense;

        @Schema(description = "손익", example = "4000000")
        private BigDecimal profitLoss;

    }

}
