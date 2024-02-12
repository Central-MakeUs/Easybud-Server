package com.friends.easybud.category.converter;

import com.friends.easybud.card.domain.Card;
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
                                                                  List<TertiaryCategory> tertiaryCategories,
                                                                  List<Card> cards) {
        List<AccountCategoryDto> accountCategoryDtos = new ArrayList<>();

        for (SecondaryCategory secondaryCategory : secondaryCategories) {
            if (secondaryCategory.getId() == 8) {
                processCardsForCategory(cards, secondaryCategory, accountCategoryDtos);
            } else {
                processTertiaryCategoriesForSecondaryCategory(secondaryCategory, tertiaryCategories,
                        accountCategoryDtos);
            }
        }

        return AccountCategoryListDto.builder()
                .accountCategories(accountCategoryDtos)
                .build();
    }

    private static void processCardsForCategory(List<Card> cards, SecondaryCategory secondaryCategory,
                                                List<AccountCategoryDto> accountCategoryDtos) {
        cards.forEach(card -> accountCategoryDtos.add(createAccountCategoryDtoFromCard(secondaryCategory, card)));
    }

    private static AccountCategoryDto createAccountCategoryDtoFromCard(SecondaryCategory secondaryCategory, Card card) {
        return AccountCategoryDto.builder()
                .primaryCategoryId(secondaryCategory.getPrimaryCategory().getId())
                .primaryCategoryContent(secondaryCategory.getPrimaryCategory().getContent())
                .secondaryCategoryId(secondaryCategory.getId())
                .secondaryCategoryContent(secondaryCategory.getContent())
                .tertiaryCategoryId(card.getId())
                .tertiaryCategoryContent(card.getName())
                .build();
    }

    private static void processTertiaryCategoriesForSecondaryCategory(SecondaryCategory secondaryCategory,
                                                                      List<TertiaryCategory> tertiaryCategories,
                                                                      List<AccountCategoryDto> accountCategoryDtos) {
        List<TertiaryCategory> relatedTertiaryCategories = filterTertiaryCategoriesForSecondaryCategory(
                secondaryCategory, tertiaryCategories);

        if (relatedTertiaryCategories.isEmpty()) {
            accountCategoryDtos.add(toAccountCategoryDto(secondaryCategory, null));
        } else {
            relatedTertiaryCategories.forEach(tertiaryCategory -> accountCategoryDtos.add(
                    toAccountCategoryDto(secondaryCategory, tertiaryCategory)));
        }
    }

    private static List<TertiaryCategory> filterTertiaryCategoriesForSecondaryCategory(
            SecondaryCategory secondaryCategory, List<TertiaryCategory> tertiaryCategories) {
        return tertiaryCategories.stream()
                .filter(tc -> tc.getSecondaryCategory().equals(secondaryCategory))
                .collect(Collectors.toList());
    }

    public static TertiaryCategorySummaryDto toTertiaryCategorySummaryDto(TertiaryCategory tertiaryCategory) {
        return TertiaryCategorySummaryDto.builder()
                .tertiaryCategoryId(tertiaryCategory.getId())
                .tertiaryCategoryContent(tertiaryCategory.getContent())
                .build();
    }
}
