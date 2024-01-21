package com.friends.easybud.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class CategoryResponse {
    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "계정 카테고리 목록 조회 DTO")
    public static class AccountCategoryListDto {
        @Schema(description = "계정 카테고리 목록")
        private List<AccountCategoryDto> accountCategories;
    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "계정 카테고리 조회 DTO")
    public static class AccountCategoryDto {
        @Schema(description = "소분류 ID")
        private Long tertiaryCategoryId;

        @Schema(description = "대분류")
        private String primaryCategory;

        @Schema(description = "중분류")
        private String secondaryCategory;

        @Schema(description = "소분류")
        private String tertiaryCategory;
    }
}
