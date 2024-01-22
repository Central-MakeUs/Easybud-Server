package com.friends.easybud.account.converter;

import com.friends.easybud.account.domain.Account;
import com.friends.easybud.account.dto.AccountResponse.AccountDetailDto;
import com.friends.easybud.account.dto.AccountResponse.AccountDetailListDto;
import com.friends.easybud.account.dto.AccountResponse.AccountSummaryDto;
import com.friends.easybud.account.dto.AccountResponse.AccountSummaryListDto;
import java.util.List;
import java.util.stream.Collectors;

public class AccountConverter {

    public static AccountDetailDto toAccountDetailDto(Account account) {
        return AccountDetailDto.builder()
                .typeName(account.getAccountType().getTypeName().name())
                .typeState(account.getAccountType().getTypeState().name())
                .amount(account.getAmount())
                .transactionId(account.getTransaction().getId())
                .primaryCategoryId(account.getTertiaryCategory().getSecondaryCategory().getPrimaryCategory().getId())
                .primaryCategoryContent(
                        account.getTertiaryCategory().getSecondaryCategory().getPrimaryCategory().getContent())
                .secondaryCategoryId(account.getTertiaryCategory().getSecondaryCategory().getId())
                .secondaryCategoryContent(account.getTertiaryCategory().getSecondaryCategory().getContent())
                .tertiaryCategoryId(account.getTertiaryCategory().getId())
                .tertiaryCategoryContent(account.getTertiaryCategory().getContent())
                .isDefault(account.getTertiaryCategory().getIsDefault()).build();
    }

    public static AccountSummaryDto toAccountSummaryDto(Account account) {
        return AccountSummaryDto.builder()
                .typeName(account.getAccountType().getTypeName().name())
                .typeState(account.getAccountType().getTypeState().name())
                .amount(account.getAmount())
                .primaryCategoryContent(
                        account.getTertiaryCategory().getSecondaryCategory().getPrimaryCategory().getContent())
                .secondaryCategoryContent(account.getTertiaryCategory().getSecondaryCategory().getContent())
                .tertiaryCategoryContent(account.getTertiaryCategory().getContent()).build();
    }

    public static AccountDetailListDto toAccountDetailListDto(List<Account> accounts) {
        List<AccountDetailDto> accountDetailDtos = accounts.stream()
                .map(AccountConverter::toAccountDetailDto)
                .collect(Collectors.toList());

        return AccountDetailListDto.builder()
                .accounts(accountDetailDtos)
                .build();
    }

    public static AccountSummaryListDto toAccountSummaryListDto(List<Account> accounts) {
        List<AccountSummaryDto> accountSummaryDtos = accounts.stream()
                .map(AccountConverter::toAccountSummaryDto)
                .collect(Collectors.toList());

        return AccountSummaryListDto.builder()
                .accounts(accountSummaryDtos)
                .build();
    }

}
