package com.friends.easybud.account.controller;

import com.friends.easybud.account.dto.AccountRequest.AccountWithCardCreateDto;
import com.friends.easybud.account.dto.AccountRequest.AccountWithTertiaryCategoryCreateDto;
import com.friends.easybud.account.service.AccountCommandService;
import com.friends.easybud.global.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/accounts")
@RestController
@Tag(name = "Account API", description = "계정 API")
public class AccountController {

    private final AccountCommandService accountCommandService;

    @Operation(summary = "계정 생성 (카드)", description = "카드 정보가 포함된 새로운 계정을 생성합니다.")
    @PostMapping
    public ResponseDto<Long> createAccountWithCard(@RequestBody AccountWithCardCreateDto request) {
        return ResponseDto.onSuccess(accountCommandService.createAccountWithCard(request));
    }

    @Operation(summary = "계정 생성 (소분류)", description = "소분류가 포함된 새로운 계정을 생성합니다.")
    @PostMapping
    public ResponseDto<Long> createAccountWithTertiaryCategory(
            @RequestBody AccountWithTertiaryCategoryCreateDto request) {
        return ResponseDto.onSuccess(accountCommandService.createAccountWithTertiaryCategory(request));
    }

    @Operation(summary = "계정 삭제", description = "기존의 계정을 삭제합니다.")
    @Parameter(name = "accountId", description = "삭제할 계정의 ID")
    @DeleteMapping
    public ResponseDto<Boolean> deleteAccount(@RequestParam Long accountId) {
        return ResponseDto.onSuccess(accountCommandService.deleteAccount(accountId));
    }

}
