package com.friends.easybud.transaction.dto;

import com.friends.easybud.transaction.domain.AccountType;
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

public class TransactionResponse {

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionListDto {
        List<TransactionDto> transactions;
    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionDto {
        private Long id;
        private LocalDateTime date;
        private String summary;
        private TransactionType type;
        private List<AccountDto> accounts;
    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountDto {
        private AccountType accountType;
        private String secondaryCategoryContent;
        private BigDecimal amount;
    }

}