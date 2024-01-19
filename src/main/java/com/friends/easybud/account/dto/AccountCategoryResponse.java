package com.friends.easybud.account.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class AccountCategoryResponse {

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountCategoryListDto {
        private List<AccountCategoryDto> accountCategories;
    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountCategoryDto {
        private String primaryCategory;
        private String secondaryCategory;
        private String tertiaryCategory;
        private String quaternaryCategory;
    }

}
