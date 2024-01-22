package com.friends.easybud.account.converter;

import com.friends.easybud.account.domain.Account;
import com.friends.easybud.account.dto.AccountResponse.AccountDetailDto;
import com.friends.easybud.account.dto.AccountResponse.AccountSummaryDto;

public class AccountConverter {

    public static AccountDetailDto toAccountDetailDto(Account account) {
        return AccountDetailDto.builder()
                .accountId(account.getId())
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
                .accountId(account.getId())
                .typeName(account.getAccountType().getTypeName().name())
                .typeState(account.getAccountType().getTypeState().name())
                .amount(account.getAmount())
                .primaryCategoryContent(
                        account.getTertiaryCategory().getSecondaryCategory().getPrimaryCategory().getContent())
                .secondaryCategoryContent(account.getTertiaryCategory().getSecondaryCategory().getContent())
                .tertiaryCategoryContent(account.getTertiaryCategory().getContent()).build();
    }

}
