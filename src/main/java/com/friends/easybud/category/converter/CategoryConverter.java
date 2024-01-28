package com.friends.easybud.category.converter;

import com.friends.easybud.category.domain.TertiaryCategory;
import com.friends.easybud.category.dto.CategoryResponse.AccountCategoryDto;
import com.friends.easybud.category.dto.CategoryResponse.AccountCategoryListDto;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryConverter {

    public static AccountCategoryDto toAccountCategoryDto(TertiaryCategory tertiaryCategory) {
        return AccountCategoryDto.builder()
                .primaryCategoryId(tertiaryCategory.getSecondaryCategory().getPrimaryCategory().getId())
                .primaryCategoryContent(tertiaryCategory.getSecondaryCategory().getPrimaryCategory().getContent())
                .secondaryCategoryId(tertiaryCategory.getSecondaryCategory().getId())
                .secondaryCategoryContent(tertiaryCategory.getSecondaryCategory().getContent())
                .tertiaryCategoryId(tertiaryCategory.getId())
                .tertiaryCategoryContent(tertiaryCategory.getContent())
                .isDefault(tertiaryCategory.getIsDefault())
                .build();
    }

    public static AccountCategoryListDto toAccountCategoryListDto(List<TertiaryCategory> tertiaryCategories) {
        List<AccountCategoryDto> accountCategoryDtos = tertiaryCategories.stream()
                .map(CategoryConverter::toAccountCategoryDto)
                .collect(Collectors.toList());

        return AccountCategoryListDto.builder()
                .accountCategories(accountCategoryDtos)
                .build();
    }

}
