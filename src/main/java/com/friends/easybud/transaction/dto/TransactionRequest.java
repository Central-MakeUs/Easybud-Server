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

public class TransactionRequest {

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "거래 생성 DTO")
    public static class TransactionCreateDto {

        @Schema(description = "거래 일자")
        private LocalDateTime date;

        @Schema(description = "적요")
        private String summary;

        @Schema(description = "거래 유형")
        private TransactionType type;

        @Schema(description = "계정 목록")
        private List<AccountCreateDto> accounts;
    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "계정 생성 DTO")
    public static class AccountCreateDto {

        @Schema(description = "계정 유형")
        private AccountType accountType;

        @Schema(description = "금액")
        private BigDecimal amount;

        @Schema(description = "소분류 ID")
        private Long tertiaryCategoryId;

        @Schema(description = "카드 ID")
        private Long cardId;
    }

}
