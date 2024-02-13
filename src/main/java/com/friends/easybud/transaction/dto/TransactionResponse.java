package com.friends.easybud.transaction.dto;

import com.friends.easybud.transaction.domain.AccountType;
import com.friends.easybud.transaction.domain.TransactionType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

        @ArraySchema(schema = @Schema(description = "거래 목록", implementation = TransactionDto.class))
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

        @Schema(description = "거래 ID", example = "15")
        private Long transactionId;

        @Schema(description = "거래 일자", example = "2024-01-27T17:30:20")
        private LocalDateTime date;

        @Schema(description = "적요", example = "스타벅스")
        private String summary;

        @Schema(description = "거래 유형", example = "EXPENSE_TRANSACTION")
        private TransactionType type;

        @ArraySchema(schema = @Schema(description = "차변 계정 목록", implementation = AccountDto.class))
        private List<AccountDto> debitAccounts;

        @ArraySchema(schema = @Schema(description = "대변 계정 목록", implementation = AccountDto.class))
        private List<AccountDto> creditAccounts;

    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "계정 조회 DTO")
    public static class AccountDto {

        @Schema(description = "계정 ID", example = "1")
        private Long accountId;

        @Schema(description = "계정 유형")
        private AccountType accountType;

        @Schema(description = "대분류 ID", example = "6")
        private Long primaryCategoryId;

        @Schema(description = "대분류", example = "비용")
        private String primaryCategoryContent;

        @Schema(description = "중분류 ID", example = "18")
        private Long secondaryCategoryId;

        @Schema(description = "중분류", example = "생활비")
        private String secondaryCategoryContent;

        @Schema(description = "소분류 or 카드 ID", example = "47")
        private Long tertiaryCategoryId;

        @Schema(description = "소분류 or 카드", example = "카페/간식")
        private String tertiaryCategoryContent;

        @Schema(description = "금액", example = "12000")
        private BigDecimal amount;

    }

}