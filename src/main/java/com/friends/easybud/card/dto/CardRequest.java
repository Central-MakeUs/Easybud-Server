package com.friends.easybud.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
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
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalDate paymentDate;
        private String name;
    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "카드 수정 DTO")
    public static class CardUpdateDto {
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalDate paymentDate;
        private String name;
    }

}
