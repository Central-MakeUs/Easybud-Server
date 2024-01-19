package com.friends.easybud.account.controller;

import static com.friends.easybud.account.dto.AccountCategoryRequest.AccountCategoryCreateDto;

import com.friends.easybud.account.dto.AccountCategoryResponse;
import com.friends.easybud.account.dto.AccountCategoryResponse.AccountCategoryListDto;
import com.friends.easybud.global.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AccountCategory API", description = "계정 카테고리 API")
@RequestMapping("/v1/account-categories")
@RestController
public class AccountCategoryController {

    @Operation(summary = "계정 카테고리 생성", description = "새로운 계정 카테고리를 생성합니다.")
    @PostMapping
    public ResponseDto<Long> createAccountCategory(@RequestBody AccountCategoryCreateDto request) {
        return ResponseDto.onSuccess(0L);
    }

    @Operation(summary = "계정 카테고리 삭제", description = "기존의 계정 카테고리를 삭제합니다.")
    @Parameter(name = "accountCategoryId", description = "삭제할 계정 카테고리의 ID")
    @DeleteMapping
    public ResponseDto<Boolean> deleteAccountCategory(@RequestParam String accountCategoryId) {
        return ResponseDto.onSuccess(Boolean.TRUE);
    }

    @Operation(summary = "계정 카테고리 조회", description = "특정 회원의 계정 카테고리 목록을 조회합니다.")
    @Parameter(name = "memberId", description = "계정 카테고리 목록을 조회할 회원의 ID")
    @GetMapping
    public ResponseDto<AccountCategoryResponse.AccountCategoryListDto> getAccountCategories(
            @RequestParam Long memberId) {
        return ResponseDto.onSuccess(AccountCategoryListDto.builder().build());
    }

}
