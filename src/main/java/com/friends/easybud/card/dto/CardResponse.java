package com.friends.easybud.card.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class CardResponse {

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "카드 목록 조회 DTO")
    public static class CardListDto {

        @ArraySchema(schema = @Schema(description = "카드 목록", implementation = CardDto.class))
        List<CardDto> cards;

    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "카드 조회 DTO")
    public static class CardDto {

        @Schema(description = "카드 ID", example = "1")
        private Long cardId;

        @Schema(description = "카드 사용 시작일 (말일은 -1)", example = "2")
        private int startDate;

        @Schema(description = "카드 사용 종료일 (말일은 -1)", example = "1")
        private int endDate;

        @Schema(description = "대금 지급일 (말일은 -1)", example = "15")
        private int paymentDate;

        @Schema(description = "카드명", example = "엄마카드")
        private String name;

    }

}
