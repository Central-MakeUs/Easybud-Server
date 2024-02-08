package com.friends.easybud.category.controller;

import static com.friends.easybud.category.dto.CategoryResponse.AccountCategoryListDto;

import com.friends.easybud.category.converter.CategoryConverter;
import com.friends.easybud.category.dto.CategoryRequest.TertiaryCategoryCreateDto;
import com.friends.easybud.category.service.CategoryCommandService;
import com.friends.easybud.category.service.CategoryQueryService;
import com.friends.easybud.global.annotation.ApiErrorCodeExample;
import com.friends.easybud.global.annotation.AuthMember;
import com.friends.easybud.global.response.ResponseDto;
import com.friends.easybud.global.response.code.ErrorStatus;
import com.friends.easybud.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/api/categories")
@RestController
@ApiResponses({
        @ApiResponse(responseCode = "2000", description = "성공"),
        @ApiResponse(responseCode = "4150", description = "존재하지 않는 계정 대분류입니다."),
        @ApiResponse(responseCode = "4151", description = "존재하지 않는 계정 중분류입니다."),
        @ApiResponse(responseCode = "4152", description = "존재하지 않는 계정 소분류입니다."),
        @ApiResponse(responseCode = "4153", description = "기본값은 삭제할 수 없습니다."),
        @ApiResponse(responseCode = "4154", description = "이미 존재하는 계정 소분류입니다."),
        @ApiResponse(responseCode = "4155", description = "존재하지 않는 카드입니다."),
        @ApiResponse(responseCode = "5000", description = "접근 권한이 없는 소분류입니다.")
})
@Tag(name = "Category API", description = "계정 카테고리 API")
public class CategoryController {

    private final CategoryCommandService categoryCommandService;
    private final CategoryQueryService categoryQueryService;

    @ApiErrorCodeExample({
            ErrorStatus.MEMBER_NOT_FOUND,
            ErrorStatus.TOKEN_INVALID,
            ErrorStatus.TOKEN_EXPIRED,
            ErrorStatus.TOKEN_UNSUPPORTED,
            ErrorStatus.TOKEN_CLAIMS_EMPTY,
            ErrorStatus.AUTHENTICATION_REQUIRED,
            ErrorStatus.SECONDARY_CATEGORY_NOT_FOUND,
            ErrorStatus.TERTIARY_CATEGORY_ALREADY_EXISTS,
            ErrorStatus._INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "계정 소분류 생성", description = "새로운 소분류를 생성합니다.")
    @PostMapping("/tertiary")
    public ResponseDto<Long> createTertiaryCategory(@AuthMember Member member,
                                                    @RequestBody TertiaryCategoryCreateDto request) {
        return ResponseDto.onSuccess(categoryCommandService.createTertiaryCategory(member, request));
    }

    @ApiErrorCodeExample({
            ErrorStatus.MEMBER_NOT_FOUND,
            ErrorStatus.TOKEN_INVALID,
            ErrorStatus.TOKEN_EXPIRED,
            ErrorStatus.TOKEN_UNSUPPORTED,
            ErrorStatus.TOKEN_CLAIMS_EMPTY,
            ErrorStatus.AUTHENTICATION_REQUIRED,
            ErrorStatus.TERTIARY_CATEGORY_NOT_FOUND,
            ErrorStatus.CANNOT_DELETE_DEFAULT_CATEGORY,
            ErrorStatus.UNAUTHORIZED_TERTIARY_CATEGORY_ACCESS,
            ErrorStatus._INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "계정 소분류 삭제", description = "기존의 소분류를 삭제합니다.")
    @Parameter(name = "tertiaryCategoryId", description = "삭제할 소분류의 ID")
    @DeleteMapping("/tertiary/{tertiaryCategoryId}")
    public ResponseDto<Boolean> deleteTertiaryCategory(@AuthMember Member member,
                                                       @PathVariable Long tertiaryCategoryId) {
        return ResponseDto.onSuccess(categoryCommandService.deleteTertiaryCategory(member, tertiaryCategoryId));
    }

    @ApiErrorCodeExample({
            ErrorStatus.MEMBER_NOT_FOUND,
            ErrorStatus.TOKEN_INVALID,
            ErrorStatus.TOKEN_EXPIRED,
            ErrorStatus.TOKEN_UNSUPPORTED,
            ErrorStatus.TOKEN_CLAIMS_EMPTY,
            ErrorStatus.AUTHENTICATION_REQUIRED,
            ErrorStatus._INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "계정 카테고리 목록 조회", description = "로그인 된 회원의 계정 카테고리 목록을 조회합니다.")
    @GetMapping
    public ResponseDto<AccountCategoryListDto> getAccountCategories(@AuthMember Member member) {
        return ResponseDto.onSuccess(
                CategoryConverter.toAccountCategoryListDto(categoryQueryService.getTertiaryCategories(member)));
    }

}
