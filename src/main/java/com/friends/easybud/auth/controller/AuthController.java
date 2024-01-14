package com.friends.easybud.auth.controller;

import com.friends.easybud.auth.dto.KakaoUserInfo;
import com.friends.easybud.auth.dto.OIDCDecodePayload;
import com.friends.easybud.auth.service.KakaoOauthService;
import com.friends.easybud.global.response.ResponseDto;
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

    @GetMapping("/validate")
    public ResponseDto<OIDCDecodePayload> getOIDCDecodePayload(@RequestParam String idToken) {
        return ResponseDto.onSuccess(kakaoOauthService.getOIDCDecodePayload(idToken));
    }

    @GetMapping("/userinfo")
    public ResponseDto<KakaoUserInfo> getKakaoUserInfo(@RequestParam String accessToken) {
        return ResponseDto.onSuccess(kakaoOauthService.getKakaoUserInfoClient(accessToken));
    }

}
