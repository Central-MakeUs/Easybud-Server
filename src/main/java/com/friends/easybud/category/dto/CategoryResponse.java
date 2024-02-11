package com.friends.easybud.category.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
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

        @ArraySchema(schema = @Schema(description = "계정 카테고리 목록", implementation = AccountCategoryDto.class))
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

        @Schema(description = "대분류 ID", example = "1")
        private Long primaryCategoryId;

        @Schema(description = "대분류", example = "자산")
        private String primaryCategoryContent;

        @Schema(description = "중분류 ID", example = "4")
        private Long secondaryCategoryId;

        @Schema(description = "중분류", example = "유가증권")
        private String secondaryCategoryContent;

        @Schema(description = "소분류 ID", example = "4")
        private Long tertiaryCategoryId;

        @Schema(description = "소분류", example = "주식")
        private String tertiaryCategoryContent;

        @Schema(description = "기본값 여부", example = "true")
        private Boolean isDefault;

    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "소분류 생성 응답 DTO")
    public static class TertiaryCategorySummaryDto {

        @Schema(description = "소분류 ID", example = "28")
        private Long tertiaryCategoryId;

        @Schema(description = "소분류 이름", example = "외화")
        private String tertiaryCategoryContent;

    }
}
