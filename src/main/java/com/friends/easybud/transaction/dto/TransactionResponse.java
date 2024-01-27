package com.friends.easybud.transaction.dto;

import com.friends.easybud.transaction.domain.AccountType;
import com.friends.easybud.transaction.domain.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class TransactionResponse {

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "거래 목록 조회 DTO")
    public static class TransactionListDto {

        @Schema(description = "거래 목록")
        List<TransactionDto> transactions;

    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "거래 조회 DTO")
    public static class TransactionDto {

        @Schema(description = "거래 ID")
        private Long transactionId;

        @Schema(description = "거래 일자")
        private LocalDateTime date;

        @Schema(description = "적요")
        private String summary;

        @Schema(description = "거래 유형")
        private TransactionType type;

        @Schema(description = "계정 목록")
        private List<AccountDto> accounts;

    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "계정 조회 DTO")
    public static class AccountDto {

        @Schema(description = "계정 ID")
        private Long accountId;

        @Schema(description = "계정 유형")
        private AccountType accountType;

        @Schema(description = "대분류 ID")
        private Long primaryCategoryId;

        @Schema(description = "대분류")
        private String primaryCategoryContent;

        @Schema(description = "중분류 ID")
        private Long secondaryCategoryId;

        @Schema(description = "중분류")
        private String secondaryCategoryContent;

        @Schema(description = "소분류 ID")
        private Long tertiaryCategoryId;

        @Schema(description = "소분류")
        private String tertiaryCategoryContent;

        @Schema(description = "금액")
        private BigDecimal amount;

    }

}