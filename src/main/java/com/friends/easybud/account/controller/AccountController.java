package com.friends.easybud.account.controller;

import com.friends.easybud.account.converter.AccountConverter;
import com.friends.easybud.account.dto.AccountRequest.AccountWithCardCreateDto;
import com.friends.easybud.account.dto.AccountRequest.AccountWithTertiaryCategoryCreateDto;
import com.friends.easybud.account.dto.AccountResponse.AccountDetailDto;
import com.friends.easybud.account.dto.AccountResponse.AccountDetailListDto;
import com.friends.easybud.account.dto.AccountResponse.AccountSummaryDto;
import com.friends.easybud.account.dto.AccountResponse.AccountSummaryListDto;
import com.friends.easybud.account.service.AccountCommandService;
import com.friends.easybud.account.service.AccountQueryService;
import com.friends.easybud.global.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/accounts")
@RestController
@Tag(name = "Account API", description = "계정 API")
public class AccountController {

    private final AccountCommandService accountCommandService;
    private final AccountQueryService accountQueryService;

    @Operation(summary = "계정 생성 (카드)", description = "카드 정보가 포함된 새로운 계정을 생성합니다.")
    @PostMapping("/with-card")
    public ResponseDto<Long> createAccountWithCard(@RequestBody AccountWithCardCreateDto request) {
        return ResponseDto.onSuccess(accountCommandService.createAccountWithCard(request));
    }

    @Operation(summary = "계정 생성 (소분류)", description = "소분류가 포함된 새로운 계정을 생성합니다.")
    @PostMapping("/with-tertiary-category")
    public ResponseDto<Long> createAccountWithTertiaryCategory(
            @RequestBody AccountWithTertiaryCategoryCreateDto request) {
        return ResponseDto.onSuccess(accountCommandService.createAccountWithTertiaryCategory(request));
    }

    @Operation(summary = "계정 삭제", description = "기존의 계정을 삭제합니다.")
    @Parameter(name = "accountId", description = "삭제할 계정의 ID")
    @DeleteMapping("/{accountId}")
    public ResponseDto<Boolean> deleteAccount(@PathVariable Long accountId) {
        return ResponseDto.onSuccess(accountCommandService.deleteAccount(accountId));
    }

    @Operation(summary = "계정 상세 목록 조회", description = "특정 거래의 계정 상세 목록을 조회합니다.")
    @GetMapping("/transactions/{transactionId}/accounts/details")
    public ResponseDto<AccountDetailListDto> getAccountDetails(@PathVariable Long transactionId) {
        return ResponseDto.onSuccess(
                AccountConverter.toAccountDetailListDto(accountQueryService.getAccounts(transactionId)));
    }

    @Operation(summary = "계정 요약 목록 조회", description = "특정 거래의 계정 요약 목록을 조회합니다.")
    @GetMapping("/transactions/{transactionId}/accounts/summaries")
    public ResponseDto<AccountSummaryListDto> getAccountSummaries(@PathVariable Long transactionId) {
        return ResponseDto.onSuccess(
                AccountConverter.toAccountSummaryListDto(accountQueryService.getAccounts(transactionId)));
    }

    @Operation(summary = "계정 상세 조회", description = "특정 거래의 계정 상세 정보를 조회합니다.")
    @GetMapping("/{accountId}/details")
    public ResponseDto<AccountDetailDto> getAccountDetail(@PathVariable Long accountId) {
        return ResponseDto.onSuccess(AccountConverter.toAccountDetailDto(accountQueryService.getAccount(accountId)));
    }

    @Operation(summary = "계정 요약 조회", description = "특정 거래의 계정 요약 정보를 조회합니다.")
    @GetMapping("/{accountId}/summary")
    public ResponseDto<AccountSummaryDto> getAccountSummary(@PathVariable Long accountId) {
        return ResponseDto.onSuccess(AccountConverter.toAccountSummaryDto(accountQueryService.getAccount(accountId)));
    }

}
