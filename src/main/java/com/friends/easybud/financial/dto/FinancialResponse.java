package com.friends.easybud.financial.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    public static class AvailableFunds {

        private BigDecimal cashAndCashEquivalents;
        private BigDecimal ordinaryDeposits;
        private BigDecimal scheduledDisbursements;
        private BigDecimal availableFunds;

    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "재무 상태 조회 DTO")
    public static class FinancialStatement {

        private BigDecimal netAssets;
        private BigDecimal totalAssets;
        private BigDecimal totalLiabilities;

    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "수익현황 조회 DTO")
    public static class IncomeStatement {

        private LocalDate startDate;
        private LocalDate endDate;
        private BigDecimal revenue;
        private BigDecimal expense;

    }

}
