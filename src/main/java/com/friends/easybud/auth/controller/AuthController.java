package com.friends.easybud.auth.controller;

import com.friends.easybud.auth.dto.IdTokenRequest;
import com.friends.easybud.auth.dto.RefreshTokenRequest;
import com.friends.easybud.auth.service.AuthService;
import com.friends.easybud.global.response.ResponseDto;
import com.friends.easybud.jwt.dto.JwtToken;
import com.friends.easybud.jwt.service.JwtTokenProvider;
import com.friends.easybud.member.domain.SocialProvider;
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
@Tag(name = "Auth API", description = "사용자 인증 API")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    @Operation(summary = "토큰 재발급", description = "Refresh Token, Access Token을 재발급합니다.")
    @PatchMapping("/reissue")
    public ResponseDto<JwtToken> reissue(@RequestBody RefreshTokenRequest request) {
        return ResponseDto.onSuccess(jwtTokenProvider.reissueToken(request));
    }

    @Operation(summary = "소셜 로그인", description = "소셜로그인을 진행하고 토큰을 발급합니다.")
    @PostMapping("/social-login")
    public ResponseDto<JwtToken> socialLogin(@RequestParam(name = "provider") SocialProvider provider,
                                             @RequestBody IdTokenRequest request) {
        return ResponseDto.onSuccess(authService.socialLogin(provider, request));
    }

}
