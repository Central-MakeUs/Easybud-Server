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
        private String typeName;
        private String typeState;
        private BigDecimal amount;
        private Long transactionId;
        private Long primaryCategoryId;
        private String primaryCategoryContent;
        private Long secondaryCategoryId;
        private String secondaryCategoryContent;
        private Long tertiaryCategoryId;
        private String tertiaryCategoryContent;
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
        private String typeName;
        private String typeState;
        private BigDecimal amount;
        private String primaryCategoryContent;
        private String secondaryCategoryContent;
        private String tertiaryCategoryContent;
    }

}
