package com.friends.easybud.auth.service;

import com.friends.easybud.auth.dto.KakaoClient;
import com.friends.easybud.auth.dto.OIDCDecodePayload;
import com.friends.easybud.auth.dto.OIDCPublicKeysResponse;
import com.friends.easybud.auth.dto.OauthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KakaoOauthService {

    private final KakaoClient kakaoClient;
    private final OauthOIDCService oauthOIDCService;
    private final OauthProperties oauthProperties;


    public OIDCDecodePayload getOIDCDecodePayload(String token) {
        OIDCPublicKeysResponse oidcPublicKeysResponse = kakaoClient.getKakaoOIDCOpenKeys();
        return oauthOIDCService.getPayloadFromIdToken(
                token,
                oauthProperties.getKakaoBaseUrl(),
                oauthProperties.getKakaoAppKey(),
                oidcPublicKeysResponse);
    }

}
