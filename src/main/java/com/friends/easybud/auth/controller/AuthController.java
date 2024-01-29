package com.friends.easybud.auth.controller;

import com.friends.easybud.auth.dto.KakaoUserInfo;
import com.friends.easybud.auth.dto.OIDCDecodePayload;
import com.friends.easybud.auth.service.KakaoOauthService;
import com.friends.easybud.global.response.ResponseDto;
import com.friends.easybud.jwt.dto.JwtToken;
import com.friends.easybud.member.service.MemberCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
@Tag(name = "Auth API", description = "사용자 인증 API")
public class AuthController {

    private final KakaoOauthService kakaoOauthService;
    private final MemberCommandService memberCommandService;

    @Operation(summary = "토큰 재발급", description = "Refresh Token, Access Token을 재발급합니다.")
    @PatchMapping("/reissue")
    public ResponseDto<JwtToken> reissue(HttpServletRequest request, HttpServletResponse response) {
        return ResponseDto.onSuccess(null);
    }

    @Operation(summary = "카카오 ID 토큰 검증", description = "카카오 ID 토큰의 유효성을 검증합니다.")
    @GetMapping("/kakao/validate")
    public ResponseDto<OIDCDecodePayload> getOIDCDecodePayload(@RequestParam String idToken) {
        return ResponseDto.onSuccess(kakaoOauthService.getOIDCDecodePayload(idToken));
    }

    @Operation(summary = "카카오 로그인", description = "카카오 로그인 또는 회원가입을 진행합니다.")
    @GetMapping("/kakao/login")
    public ResponseDto<JwtToken> kakaoLogin(@RequestParam String accessToken) {
        KakaoUserInfo kakaoUserInfo = kakaoOauthService.getKakaoUserInfoClient(accessToken);
        return ResponseDto.onSuccess(memberCommandService.kakaoLogin(kakaoUserInfo));
    }

}
