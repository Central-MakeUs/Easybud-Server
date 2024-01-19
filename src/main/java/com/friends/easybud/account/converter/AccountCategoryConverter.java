package com.friends.easybud.account.converter;

import static com.friends.easybud.account.dto.AccountCategoryResponse.AccountCategoryDto;

import com.friends.easybud.account.domain.AccountCategory;
import com.friends.easybud.account.dto.AccountCategoryResponse.AccountCategoryListDto;
import java.util.List;
import java.util.stream.Collectors;

public class AccountCategoryConverter {

    public static AccountCategoryDto toAccountCategoryDto(AccountCategory accountCategory) {
        return AccountCategoryDto.builder()
                .id(accountCategory.getId())
                .primaryCategory(accountCategory.getPrimaryCategory())
                .secondaryCategory(accountCategory.getSecondaryCategory())
                .tertiaryCategory(accountCategory.getTertiaryCategory())
                .quaternaryCategory(accountCategory.getQuaternaryCategory())
                .build();
    }

    public static AccountCategoryListDto toAccountCategoryListDto(List<AccountCategory> accountCategories) {
        List<AccountCategoryDto> accountCategoryDtos = accountCategories.stream()
                .map(AccountCategoryConverter::toAccountCategoryDto)
                .collect(Collectors.toList());

        return AccountCategoryListDto.builder()
                .accountCategories(accountCategoryDtos)
                .build();
    }

}
