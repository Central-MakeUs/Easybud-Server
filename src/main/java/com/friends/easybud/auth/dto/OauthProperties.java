package com.friends.easybud.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "oauth")
public class OauthProperties {

    private OAuthSecret kakao;

    @Getter
    @Setter
    public static class OAuthSecret {
        private String baseUrl;
        private String appKey;
    }

    public String getKakaoBaseUrl() {
        return kakao.getBaseUrl();
    }

    public String getKakaoAppKey() {
        return kakao.getAppKey();
    }

}
