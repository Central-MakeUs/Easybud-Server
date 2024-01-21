package com.friends.easybud.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class CategoryRequest {

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "소분류 생성 DTO")
    public static class TertiaryCategoryCreateDto {
        @Schema(description = "중분류")
        private String secondaryCategory;

        @Schema(description = "소분류")
        private String tertiaryCategory;
    }

}