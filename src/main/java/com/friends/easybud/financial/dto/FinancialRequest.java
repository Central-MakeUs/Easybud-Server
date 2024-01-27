package com.friends.easybud.financial.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class FinancialRequest {

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "장부 날짜 DTO")
    public static class FinancialDateDto {

        @Schema(description = "시작 날짜")
        private LocalDate startDate;

        @Schema(description = "종료 날짜")
        private LocalDate endDate;

    }

}
