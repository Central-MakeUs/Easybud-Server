package com.friends.easybud.auth.dto;

import com.friends.easybud.global.exception.GeneralException;
import com.friends.easybud.global.response.code.ErrorStatus;
import com.friends.easybud.member.domain.SocialProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "oauth")
public class OauthProperties {

    private OAuthSecret kakao;
    private OAuthSecret apple;

    @Getter
    @Setter
    public static class OAuthSecret {
        private String baseUrl;
        private String appKey;
    }

    public String getBaseUrl(SocialProvider provider) {
        switch (provider) {
            case KAKAO:
                return getOAuthSecret(kakao).getBaseUrl();
            case APPLE:
                return getOAuthSecret(apple).getBaseUrl();
            default:
                throw new GeneralException(ErrorStatus.OAUTH_PROVIDER_NOT_FOUND);
        }
    }

    public String getAppKey(SocialProvider provider) {
        switch (provider) {
            case KAKAO:
                return getOAuthSecret(kakao).getAppKey();
            case APPLE:
                return getOAuthSecret(apple).getAppKey();
            default:
                throw new GeneralException(ErrorStatus.OAUTH_PROVIDER_NOT_FOUND);
        }
    }

    private OAuthSecret getOAuthSecret(OAuthSecret secret) {
        if (secret == null || secret.getBaseUrl() == null || secret.getAppKey() == null) {
            throw new GeneralException(ErrorStatus.OAUTH_PROVIDER_NOT_FOUND);
        }
        return secret;
    }

}
