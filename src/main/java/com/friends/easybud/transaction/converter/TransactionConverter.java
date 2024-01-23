package com.friends.easybud.transaction.converter;

import static com.friends.easybud.transaction.dto.TransactionResponse.TransactionDto;

import com.friends.easybud.transaction.domain.Account;
import com.friends.easybud.transaction.domain.Transaction;
import com.friends.easybud.transaction.dto.TransactionResponse.AccountDto;
import com.friends.easybud.transaction.dto.TransactionResponse.TransactionListDto;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionConverter {

    public static TransactionListDto toTransactionListDto(List<Transaction> transactions) {
        List<TransactionDto> transactionDtoList = transactions.stream().map(TransactionConverter::toTransactionDto)
                .collect(Collectors.toList());
        return TransactionListDto.builder()
                .transactions(transactionDtoList).build();
    }

    public static TransactionDto toTransactionDto(Transaction transaction) {
        List<AccountDto> accountDtos = transaction.getAccounts().stream()
                .map(TransactionConverter::toAccountDto)
                .collect(Collectors.toList());

        return TransactionDto.builder()
                .id(transaction.getId())
                .date(transaction.getDate())
                .summary(transaction.getSummary())
                .type(transaction.getType())
                .accounts(accountDtos)
                .build();
    }

    public static AccountDto toAccountDto(Account account) {
        return AccountDto.builder()
                .accountType(account.getAccountType())
                .secondaryCategoryContent(account.getTertiaryCategory().getSecondaryCategory().getContent())
                .amount(account.getAmount()).build();
    }

}
