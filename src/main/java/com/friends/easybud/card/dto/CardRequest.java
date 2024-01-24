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

        @Schema(description = "카드 사용 시작 날짜")
        private LocalDate startDate;

        @Schema(description = "카드 사용 종료 날짜")
        private LocalDate endDate;

        @Schema(description = "대금 지급일")
        private LocalDate paymentDate;

        @Schema(description = "카드명")
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

        @Schema(description = "카드 사용 시작 날짜")
        private LocalDate startDate;

        @Schema(description = "카드 사용 종료 날짜")
        private LocalDate endDate;

        @Schema(description = "대금 지급일")
        private LocalDate paymentDate;

        @Schema(description = "카드명")
        private String name;

    }

}
