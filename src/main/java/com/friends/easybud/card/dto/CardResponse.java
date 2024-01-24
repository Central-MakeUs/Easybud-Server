package com.friends.easybud.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
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
        private Long cardId;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalDate paymentDate;
        private String name;
    }

}
