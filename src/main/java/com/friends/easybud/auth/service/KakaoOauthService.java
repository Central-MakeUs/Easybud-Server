package com.friends.easybud.auth.service;

import com.friends.easybud.auth.dto.OIDCDecodePayload;
import com.friends.easybud.auth.dto.OIDCPublicKeysResponse;
import com.friends.easybud.auth.dto.OauthProperties;
import com.friends.easybud.auth.feign.KakaoOauthClient;
import com.friends.easybud.jwt.dto.JwtToken;
import com.friends.easybud.jwt.service.JwtTokenProvider;
import com.friends.easybud.member.domain.Member;
import com.friends.easybud.member.domain.Role;
import com.friends.easybud.member.domain.SocialProvider;
import com.friends.easybud.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoOauthService {

    private final OauthProperties oauthProperties;
    private final KakaoOauthClient kakaoOauthClient;
    private final OauthOIDCService oauthOIDCService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;


    public JwtToken kakaoLogin(String idToken) {
        OIDCDecodePayload oidcDecodePayload = getOIDCDecodePayload(idToken);
        Member member = memberRepository.findByEmailAndSocialProvider(oidcDecodePayload.getEmail(),
                        SocialProvider.KAKAO)
                .orElseGet(() -> registerKakaoUser(oidcDecodePayload));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                member,
                null,
                member.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    private Member registerKakaoUser(OIDCDecodePayload oidcDecodePayload) {
        log.info("name = {}", oidcDecodePayload.getNickname());
        Member member = Member.builder()
                .socialProvider(SocialProvider.KAKAO)
                .email(oidcDecodePayload.getEmail())
                .name(oidcDecodePayload.getNickname())
                .role(Role.USER)
                .build();
        return memberRepository.save(member);
    }

    public OIDCDecodePayload getOIDCDecodePayload(String idToken) {
        OIDCPublicKeysResponse oidcPublicKeysResponse = kakaoOauthClient.getKakaoOIDCOpenKeys();
        return oauthOIDCService.getPayloadFromIdToken(
                idToken,
                oauthProperties.getKakaoBaseUrl(),
                oauthProperties.getKakaoAppKey(),
                oidcPublicKeysResponse);
    }

}
