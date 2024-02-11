package com.friends.easybud.category.converter;

import com.friends.easybud.category.domain.SecondaryCategory;
import com.friends.easybud.category.domain.TertiaryCategory;
import com.friends.easybud.category.dto.CategoryResponse.AccountCategoryDto;
import com.friends.easybud.category.dto.CategoryResponse.AccountCategoryListDto;
import com.friends.easybud.category.dto.CategoryResponse.TertiaryCategorySummaryDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryConverter {

    public static AccountCategoryDto toAccountCategoryDto(SecondaryCategory secondaryCategory,
                                                          TertiaryCategory tertiaryCategory) {
        AccountCategoryDto.AccountCategoryDtoBuilder builder = AccountCategoryDto.builder()
                .primaryCategoryId(secondaryCategory.getPrimaryCategory().getId())
                .primaryCategoryContent(secondaryCategory.getPrimaryCategory().getContent())
                .secondaryCategoryId(secondaryCategory.getId())
                .secondaryCategoryContent(secondaryCategory.getContent());

        if (tertiaryCategory != null) {
            builder.tertiaryCategoryId(tertiaryCategory.getId())
                    .tertiaryCategoryContent(tertiaryCategory.getContent())
                    .isDefault(tertiaryCategory.getIsDefault());
        }

        return builder.build();
    }

    public static AccountCategoryListDto toAccountCategoryListDto(List<SecondaryCategory> secondaryCategories,
                                                                  List<TertiaryCategory> tertiaryCategories) {
        List<AccountCategoryDto> accountCategoryDtos = new ArrayList<>();

        for (SecondaryCategory secondaryCategory : secondaryCategories) {
            List<TertiaryCategory> relatedTertiaryCategories = tertiaryCategories.stream()
                    .filter(tc -> tc.getSecondaryCategory().equals(secondaryCategory))
                    .collect(Collectors.toList());

            if (relatedTertiaryCategories.isEmpty()) {
                accountCategoryDtos.add(toAccountCategoryDto(secondaryCategory, null));
            } else {
                relatedTertiaryCategories.forEach(tertiaryCategory ->
                        accountCategoryDtos.add(toAccountCategoryDto(secondaryCategory, tertiaryCategory)));
            }
        }

        return AccountCategoryListDto.builder()
                .accountCategories(accountCategoryDtos)
                .build();
    }

    public static TertiaryCategorySummaryDto toTertiaryCategorySummaryDto(TertiaryCategory tertiaryCategory) {
        return TertiaryCategorySummaryDto.builder()
                .tertiaryCategoryId(tertiaryCategory.getId())
                .tertiaryCategoryContent(tertiaryCategory.getContent())
                .build();
    }

}
