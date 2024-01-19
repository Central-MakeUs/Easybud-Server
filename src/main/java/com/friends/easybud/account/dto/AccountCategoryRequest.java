package com.friends.easybud.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class AccountCategoryRequest {

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountCategoryCreateDto {
        private String primaryCategory;
        private String secondaryCategory;
        private String tertiaryCategory;
        private String quaternaryCategory;
    }
}
