package com.friends.easybud.auth.dto;

import com.friends.easybud.member.domain.SocialProvider;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public enum OAuthAttributes {
    KAKAO("kakao", attributes -> {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return new OAuthProfile(
                SocialProvider.KAKAO,
                (String) kakaoAccount.get("email"),
                (String) profile.get("nickname")
        );
    });

    // TODO Apple Login

    private final String registrationId;
    private final Function<Map<String, Object>, OAuthProfile> oAuthProfileFactory;

    public static OAuthProfile extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .oAuthProfileFactory.apply(attributes);
    }
}
