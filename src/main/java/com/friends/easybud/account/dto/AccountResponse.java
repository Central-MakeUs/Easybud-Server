package com.friends.easybud.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class AccountResponse {

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "계정 상세 목록 조회 DTO")
    public static class AccountDetailListDto {

        @Schema(description = "계정 상세 목록")
        private List<AccountDetailDto> accounts;

    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "계정 상세 목록 조회 DTO")
    public static class AccountSummaryListDto {

        @Schema(description = "계정 상세 목록")
        private List<AccountSummaryDto> accounts;

    }

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "계정 상세 조회 DTO")
    public static class AccountDetailDto {

        @Schema(description = "계정 ID")
        private Long accountId;

        @Schema(description = "계정 유형 이름")
        private String typeName;

        @Schema(description = "계정 유형 상태")
        private String typeState;

        @Schema(description = "금액")
        private BigDecimal amount;

        @Schema(description = "거래 ID")
        private Long transactionId;

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

    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "계정 요약 조회 DTO")
    public static class AccountSummaryDto {

        @Schema(description = "계정 ID")
        private Long accountId;

        @Schema(description = "계정 유형 이름")
        private String typeName;

        @Schema(description = "계정 유형 상태")
        private String typeState;

        @Schema(description = "금액")
        private BigDecimal amount;

        @Schema(description = "대분류")
        private String primaryCategoryContent;

        @Schema(description = "중분류")
        private String secondaryCategoryContent;

        @Schema(description = "소분류")
        private String tertiaryCategoryContent;

    }

}
