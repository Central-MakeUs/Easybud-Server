package com.friends.easybud.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class CardRequest {

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "카드 생성 DTO")
    public static class CardCreateDto {

        @Schema(description = "카드 사용 시작일 (말일은 -1)", example = "2")
        private int startDate;

        @Schema(description = "카드 사용 종료일 (말일은 -1)", example = "1")
        private int endDate;

        @Schema(description = "대금 지급일 (말일은 -1)", example = "15")
        private int paymentDate;

        @Schema(description = "카드명", example = "엄마카드")
        private String name;

        @Schema(description = "적요", example = "해외 사용 시 2% 캐시백")
        private String summary;

    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "카드 수정 DTO")
    public static class CardUpdateDto {

        @Schema(description = "카드 사용 시작일 (말일은 -1)", example = "15")
        private int startDate;

        @Schema(description = "카드 사용 종료일 (말일은 -1)", example = "14")
        private int endDate;

        @Schema(description = "대금 지급일 (말일은 -1)", example = "1")
        private int paymentDate;

        @Schema(description = "카드명", example = "엄마카드")
        private String name;

        @Schema(description = "적요", example = "해외 사용 시 2% 캐시백")
        private String summary;

    }

}
