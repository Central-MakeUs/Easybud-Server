package com.friends.easybud.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "계정 카테고리 생성 DTO")
    public static class AccountCategoryCreateDto {
        @Schema(description = "대분류")
        private String primaryCategory;

        @Schema(description = "중분류")
        private String secondaryCategory;

        @Schema(description = "소분류")
        private String tertiaryCategory;

        @Schema(description = "세분류")
        private String quaternaryCategory;
    }
}