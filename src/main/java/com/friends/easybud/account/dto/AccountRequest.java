package com.friends.easybud.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class AccountRequest {

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "계정 생성 (카드) DTO")
    public static class AccountWithCardCreateDto {

        @Schema(description = "거래 ID")
        private Long transactionId;

        @Schema(description = "계정 유형 이름")
        private String typeName;

        @Schema(description = "계정 유형 상태")
        private String typeState;

        @Schema(description = "금액")
        private BigDecimal amount;

        @Schema(description = "카드 ID")
        private Long cardId;

    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "계정 생성 (소분류) DTO")
    public static class AccountWithTertiaryCategoryCreateDto {

        @Schema(description = "거래 ID")
        private Long transactionId;

        @Schema(description = "계정 유형 이름")
        private String typeName;

        @Schema(description = "계정 유형 상태")
        private String typeState;

        @Schema(description = "금액")
        private BigDecimal amount;

        @Schema(description = "소분류 ID")
        private Long tertiaryCategoryId;

    }

}
