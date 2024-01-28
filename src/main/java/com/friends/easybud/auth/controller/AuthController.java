package com.friends.easybud.auth.controller;

import com.friends.easybud.auth.dto.KakaoUserInfo;
import com.friends.easybud.auth.dto.OIDCDecodePayload;
import com.friends.easybud.auth.service.KakaoOauthService;
import com.friends.easybud.global.response.ResponseDto;
import com.friends.easybud.jwt.dto.JwtToken;
import com.friends.easybud.member.service.MemberCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final KakaoOauthService kakaoOauthService;
    private final MemberCommandService memberCommandService;

    @GetMapping("/kakao/validate")
    public ResponseDto<OIDCDecodePayload> getOIDCDecodePayload(@RequestParam String idToken) {
        return ResponseDto.onSuccess(kakaoOauthService.getOIDCDecodePayload(idToken));
    }

    @GetMapping("/kakao/login")
    public ResponseDto<JwtToken> getKakaoUserInfo(@RequestParam String accessToken) {
        KakaoUserInfo kakaoUserInfo = kakaoOauthService.getKakaoUserInfoClient(accessToken);
        return ResponseDto.onSuccess(memberCommandService.kakaoLogin(kakaoUserInfo));
    }

}
