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
        private Long transactionId;
        private String typeName;
        private String typeStatus;
        private BigDecimal amount;
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
        private Long transactionId;
        private String typeName;
        private String typeStatus;
        private BigDecimal amount;
        private Long tertiaryCategoryId;
    }

}
