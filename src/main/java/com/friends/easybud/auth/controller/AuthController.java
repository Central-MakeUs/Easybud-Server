package com.friends.easybud.auth.controller;

import com.friends.easybud.auth.dto.IdTokenRequest;
import com.friends.easybud.auth.dto.RefreshTokenRequest;
import com.friends.easybud.auth.service.AuthService;
import com.friends.easybud.global.response.ResponseDto;
import com.friends.easybud.jwt.JwtDto;
import com.friends.easybud.jwt.JwtProvider;
import com.friends.easybud.member.domain.Member;
import com.friends.easybud.member.domain.SocialProvider;
import com.friends.easybud.member.service.MemberCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
@Tag(name = "Auth API", description = "사용자 API")
public class AuthController {

    private final JwtProvider jwtProvider;
    private final AuthService authService;
    private final MemberCommandService memberCommandService;

    @Operation(summary = "토큰 재발급", description = "Refresh Token, Access Token을 재발급합니다.")
    @PatchMapping("/reissue")
    public ResponseDto<JwtDto> reissue(@RequestBody RefreshTokenRequest request) {
        return ResponseDto.onSuccess(jwtProvider.reissueToken(request));
    }

    @Operation(summary = "소셜 로그인", description = "소셜로그인을 진행하고 토큰을 발급합니다.")
    @PostMapping("/social-login")
    public ResponseDto<JwtDto> socialLogin(@RequestParam(name = "provider") SocialProvider provider,
                                           @RequestBody IdTokenRequest request) {
        return ResponseDto.onSuccess(authService.socialLogin(provider, request));
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 진행합니다.")
    @PostMapping("/logout")
    public ResponseDto<Boolean> logout(@RequestBody RefreshTokenRequest request) {
        return ResponseDto.onSuccess(jwtProvider.logout(request.getRefreshToken()));
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 진행합니다.")
    @PostMapping("/withdrawal")
    public ResponseDto<Boolean> withdrawal(@RequestBody Member member) {
        return ResponseDto.onSuccess(memberCommandService.withdrawal(member));
    }

}
