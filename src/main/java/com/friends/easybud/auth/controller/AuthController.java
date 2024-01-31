package com.friends.easybud.auth.controller;

import com.friends.easybud.auth.service.AuthService;
import com.friends.easybud.auth.service.KakaoOauthService;
import com.friends.easybud.global.response.ResponseDto;
import com.friends.easybud.jwt.dto.JwtToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
@Tag(name = "Auth API", description = "사용자 인증 API")
public class AuthController {

    private final KakaoOauthService kakaoOauthService;
    private final AuthService authService;


    @Operation(summary = "토큰 재발급", description = "Refresh Token, Access Token을 재발급합니다.")
    @PatchMapping("/reissue")
    public ResponseDto<JwtToken> reissue(@RequestBody String refreshToken) {
        return ResponseDto.onSuccess(authService.reissueToken(refreshToken));
    }

    @Operation(summary = "카카오 로그인", description = "OIDC 카카오 로그인을 진행하고, 토큰을 발급합니다.")
    @GetMapping("/login/kakao")
    public ResponseDto<JwtToken> kakaoLogin(@RequestParam String idToken) {
        return ResponseDto.onSuccess(kakaoOauthService.kakaoLogin(idToken));
    }

}
