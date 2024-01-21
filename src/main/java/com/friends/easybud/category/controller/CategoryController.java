package com.friends.easybud.category.controller;

import static com.friends.easybud.category.dto.CategoryResponse.AccountCategoryListDto;

import com.friends.easybud.category.dto.CategoryRequest.TertiaryCategoryCreateDto;
import com.friends.easybud.global.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/categories")
@RestController
@Tag(name = "Category API", description = "계정 카테고리 API")
public class CategoryController {

    @Operation(summary = "계정 소분류 생성", description = "새로운 소분류를 생성합니다.")
    @PostMapping
    public ResponseDto<Long> createTertiaryCategory(@RequestBody TertiaryCategoryCreateDto request) {
        return ResponseDto.onSuccess(1L);
    }

    @Operation(summary = "계정 소분류 삭제", description = "기존의 소분류를 삭제합니다.")
    @Parameter(name = "tertiaryCategoryId", description = "삭제할 소분류의 ID")
    @DeleteMapping
    public ResponseDto<Boolean> deleteTertiaryCategory(@RequestParam Long tertiaryCategoryId) {
        return ResponseDto.onSuccess(Boolean.TRUE);
    }

    @Operation(summary = "계정 카테고리 조회", description = "로그인 된 회원의 계정 카테고리 목록을 조회합니다.")
    @GetMapping
    public ResponseDto<AccountCategoryListDto> getAccountCategories() {
        return ResponseDto.onSuccess(null);
    }

}
