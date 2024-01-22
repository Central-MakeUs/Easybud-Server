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

        @Schema(description = "대분류 ID")
        private Long primaryCategoryId;

        @Schema(description = "대분류")
        private String primaryCategoryContent;

        @Schema(description = "중분류 ID")
        private Long secondaryCategoryId;

        @Schema(description = "중분류")
        private String secondaryCategoryContent;

        @Schema(description = "소분류 ID")
        private Long tertiaryCategoryId;

        @Schema(description = "소분류")
        private String tertiaryCategoryContent;

        @Schema(description = "기본값 여부")
        private Boolean isDefault;

    }
}
