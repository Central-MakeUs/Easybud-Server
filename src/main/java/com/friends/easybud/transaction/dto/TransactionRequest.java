package com.friends.easybud.transaction.dto;

import com.friends.easybud.account.domain.AccountType;
import com.friends.easybud.transaction.domain.TransactionType;
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
    public static class TransactionCreateDto {
        private LocalDateTime date;
        private String summary;
        private TransactionType type;
        private List<AccountCreateDto> accounts;
    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountCreateDto {
        private AccountType accountType;
        private BigDecimal amount;
        private Long tertiaryCategoryId;
        private Long cardId;
    }

}
