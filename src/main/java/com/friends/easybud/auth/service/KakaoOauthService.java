package com.friends.easybud.auth.service;

import com.friends.easybud.auth.dto.KakaoUserInfo;
import com.friends.easybud.auth.dto.OIDCDecodePayload;
import com.friends.easybud.auth.dto.OIDCPublicKeysResponse;
import com.friends.easybud.auth.dto.OauthProperties;
import com.friends.easybud.auth.feign.KakaoOauthClient;
import com.friends.easybud.auth.feign.KakaoUserInfoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KakaoOauthService {

    private final KakaoOauthClient kakaoOauthClient;
    private final KakaoUserInfoClient kakaoUserInfoClient;
    private final OauthOIDCService oauthOIDCService;
    private final OauthProperties oauthProperties;


    public OIDCDecodePayload getOIDCDecodePayload(String idToken) {
        OIDCPublicKeysResponse oidcPublicKeysResponse = kakaoOauthClient.getKakaoOIDCOpenKeys();
        return oauthOIDCService.getPayloadFromIdToken(
                idToken,
                oauthProperties.getKakaoBaseUrl(),
                oauthProperties.getKakaoAppKey(),
                oidcPublicKeysResponse);
    }

    public KakaoUserInfo getKakaoUserInfoClient(String accessToken) {
        return kakaoUserInfoClient.getKakaoUserInfo(accessToken);
    }

}
