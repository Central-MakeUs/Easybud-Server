package com.friends.easybud.transaction.converter;

import static com.friends.easybud.transaction.dto.TransactionResponse.TransactionDto;

import com.friends.easybud.global.exception.GeneralException;
import com.friends.easybud.global.response.code.ErrorStatus;
import com.friends.easybud.transaction.domain.Account;
import com.friends.easybud.transaction.domain.AccountState;
import com.friends.easybud.transaction.domain.AccountType;
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
        List<AccountDto> debitAccounts = transaction.getAccounts().stream()
                .filter(account -> isDebitAccount(account.getAccountType())) // 차변 계정인지 확인
                .map(TransactionConverter::toAccountDto)
                .collect(Collectors.toList());

        List<AccountDto> creditAccounts = transaction.getAccounts().stream()
                .filter(account -> !isDebitAccount(account.getAccountType())) // 대변 계정인지 확인
                .map(TransactionConverter::toAccountDto)
                .collect(Collectors.toList());

        return TransactionDto.builder()
                .transactionId(transaction.getId())
                .date(transaction.getDate())
                .summary(transaction.getSummary())
                .debitAccounts(debitAccounts)
                .creditAccounts(creditAccounts)
                .build();
    }

    public static AccountDto toAccountDto(Account account) {
        Long primaryCategoryId = null;
        String primaryCategoryContent = null;
        Long secondaryCategoryId = null;
        String secondaryCategoryContent = null;
        Long tertiaryCategoryId = null;
        String tertiaryCategoryContent = null;

        if (account.getTertiaryCategory() != null) {
            primaryCategoryId = account.getTertiaryCategory().getId();
            primaryCategoryContent = account.getTertiaryCategory().getContent();
            secondaryCategoryId = account.getTertiaryCategory().getSecondaryCategory().getId();
            secondaryCategoryContent = account.getTertiaryCategory().getSecondaryCategory().getContent();
            tertiaryCategoryId = account.getTertiaryCategory().getSecondaryCategory().getPrimaryCategory().getId();
            tertiaryCategoryContent = account.getTertiaryCategory().getSecondaryCategory().getPrimaryCategory()
                    .getContent();

        } else if (account.getCard() != null) {
            primaryCategoryId = 2L;
            primaryCategoryContent = "부채";
            secondaryCategoryId = 8L;
            secondaryCategoryContent = "카드대금";
            tertiaryCategoryId = account.getCard().getId();
            tertiaryCategoryContent = account.getCard().getName();
        }

        return AccountDto.builder()
                .accountId(account.getId())
                .accountType(account.getAccountType())
                .primaryCategoryId(primaryCategoryId)
                .primaryCategoryContent(primaryCategoryContent)
                .secondaryCategoryId(secondaryCategoryId)
                .secondaryCategoryContent(secondaryCategoryContent)
                .tertiaryCategoryId(tertiaryCategoryId)
                .tertiaryCategoryContent(tertiaryCategoryContent)
                .amount(account.getAmount())
                .build();

    }

    private static boolean isDebitAccount(AccountType accountType) {
        switch (accountType.getTypeName()) {
            case ASSET:
                return accountType.getTypeState() == AccountState.INCREASE;
            case LIABILITY:
            case EQUITY:
                return accountType.getTypeState() == AccountState.DECREASE;
            case EXPENSE:
                return accountType.getTypeState() == AccountState.OCCURRENCE;
            case REVENUE:
                return false; // 수익은 항상 대변
            default:
                throw new GeneralException(ErrorStatus.ACCOUNT_NOT_FOUND);
        }
    }


}
