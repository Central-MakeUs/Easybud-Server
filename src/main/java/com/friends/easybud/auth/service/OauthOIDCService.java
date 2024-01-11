package com.friends.easybud.auth.service;

import com.friends.easybud.auth.dto.OIDCDecodePayload;
import com.friends.easybud.auth.dto.OIDCPublicKey;
import com.friends.easybud.auth.dto.OIDCPublicKeysResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OauthOIDCService {

    private final JwtOIDCProvider jwtOIDCProvider;

    private String getKidFromUnsignedIdToken(String token, String iss, String aud) {
        return jwtOIDCProvider.getKidFromUnsignedTokenHeader(token, iss, aud);
    }

    public OIDCDecodePayload getPayloadFromIdToken(
            String token, String iss, String aud, OIDCPublicKeysResponse oidcPublicKeysResponse) {
        String kid = getKidFromUnsignedIdToken(token, iss, aud);

        OIDCPublicKey oidcPublicKeyDto =
                oidcPublicKeysResponse.getKeys().stream()
                        .filter(o -> o.getKid().equals(kid))
                        .findFirst()
                        .orElseThrow();

        return jwtOIDCProvider.getOIDCTokenBody(
                token, oidcPublicKeyDto.getN(), oidcPublicKeyDto.getE());
    }

}
