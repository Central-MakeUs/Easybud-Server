package com.friends.easybud.account.controller;

import static com.friends.easybud.account.dto.AccountCategoryRequest.AccountCategoryCreateDto;

import com.friends.easybud.account.dto.AccountCategoryResponse;
import com.friends.easybud.account.dto.AccountCategoryResponse.AccountCategoryListDto;
import com.friends.easybud.global.response.ResponseDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/account-categories")
@RestController
public class AccountCategoryController {

    @PostMapping
    public ResponseDto<Long> createAccountCategory(@RequestBody AccountCategoryCreateDto request) {
        return ResponseDto.onSuccess(0L);
    }

    @DeleteMapping
    public ResponseDto<Boolean> deleteAccountCategory(@RequestParam String accountCategoryId) {
        return ResponseDto.onSuccess(Boolean.TRUE);
    }

    @GetMapping
    public ResponseDto<AccountCategoryResponse.AccountCategoryListDto> getAccountCategories(
            @RequestParam Long memberId) {
        return ResponseDto.onSuccess(AccountCategoryListDto.builder().build());
    }

}
