package com.friends.easybud.auth.controller;

import com.friends.easybud.auth.dto.IdTokenRequest;
import com.friends.easybud.auth.dto.RefreshTokenRequest;
import com.friends.easybud.auth.dto.SocialLoginResponse;
import com.friends.easybud.auth.service.AuthService;
import com.friends.easybud.global.annotation.ApiErrorCodeExample;
import com.friends.easybud.global.annotation.AuthMember;
import com.friends.easybud.global.response.ResponseDto;
import com.friends.easybud.global.response.code.ErrorStatus;
import com.friends.easybud.jwt.JwtDto;
import com.friends.easybud.jwt.JwtProvider;
import com.friends.easybud.member.domain.Member;
import com.friends.easybud.member.domain.SocialProvider;
import com.friends.easybud.member.service.MemberCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
@ApiResponse(responseCode = "2000", description = "성공")
@Tag(name = "Auth API", description = "사용자 API")
public class AuthController {

    private final JwtProvider jwtProvider;
    private final AuthService authService;
    private final MemberCommandService memberCommandService;

    @ApiErrorCodeExample({
            ErrorStatus.TOKEN_INVALID,
            ErrorStatus.TOKEN_EXPIRED,
            ErrorStatus.TOKEN_UNSUPPORTED,
            ErrorStatus.TOKEN_CLAIMS_EMPTY,
            ErrorStatus.REFRESH_TOKEN_NOT_FOUND,
            ErrorStatus.MEMBER_NOT_FOUND,
            ErrorStatus._INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "토큰 재발급", description = "Refresh Token, Access Token을 재발급합니다.")
    @PostMapping("/reissue")
    public ResponseDto<JwtDto> reissue(@RequestBody RefreshTokenRequest request) {
        return ResponseDto.onSuccess(jwtProvider.reissueToken(request));
    }

    @ApiErrorCodeExample({
            ErrorStatus.TOKEN_INVALID,
            ErrorStatus.TOKEN_EXPIRED,
            ErrorStatus.OAUTH_PROVIDER_NOT_FOUND,
            ErrorStatus.TOKEN_UNSUPPORTED,
            ErrorStatus._INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "소셜 로그인", description = "소셜로그인을 진행하고 토큰을 발급합니다.")
    @PostMapping("/social-login")
    public ResponseDto<SocialLoginResponse> socialLogin(@RequestParam(name = "provider") SocialProvider provider,
                                                        @RequestBody IdTokenRequest request) {
        return ResponseDto.onSuccess(authService.socialLogin(provider, request));
    }

    @ApiErrorCodeExample({
            ErrorStatus._INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "로그아웃", description = "로그아웃을 진행합니다.")
    @PostMapping("/logout")
    public ResponseDto<Boolean> logout(@RequestBody RefreshTokenRequest request) {
        return ResponseDto.onSuccess(jwtProvider.logout(request.getRefreshToken()));
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
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 진행합니다.")
    @DeleteMapping("/withdrawal")
    public ResponseDto<Boolean> withdrawal(@AuthMember Member member) {
        return ResponseDto.onSuccess(memberCommandService.withdrawal(member));
    }

}
