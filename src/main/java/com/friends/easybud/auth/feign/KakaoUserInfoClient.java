package com.friends.easybud.auth.feign;

import com.friends.easybud.auth.dto.KakaoUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoUserInfoClient", url = "https://kapi.kakao.com")
public interface KakaoUserInfoClient {

    @GetMapping("/v1/oidc/userinfo")
    KakaoUserInfo getKakaoUserInfo(@RequestParam String accessToken);

}
