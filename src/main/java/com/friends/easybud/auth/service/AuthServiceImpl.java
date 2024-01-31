package com.friends.easybud.auth.service;

import com.friends.easybud.auth.dto.IdTokenRequest;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthServiceImpl implements AuthService {

    private final OauthProperties oauthProperties;
    private final KakaoOauthClient kakaoOauthClient;
    private final OauthOIDCService oauthOIDCService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public JwtToken socialLogin(SocialProvider provider, IdTokenRequest request) {
        OIDCDecodePayload oidcDecodePayload = getOIDCDecodePayload(provider, request.getIdToken());
        Member member = memberRepository.findByEmailAndSocialProvider(oidcDecodePayload.getEmail(),
                        provider)
                .orElseGet(() -> register(provider, oidcDecodePayload));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                member,
                null,
                member.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    private Member register(SocialProvider provider, OIDCDecodePayload oidcDecodePayload) {
        Member member = Member.builder()
                .socialProvider(provider)
                .email(oidcDecodePayload.getEmail())
                .name(oidcDecodePayload.getNickname())
                .role(Role.USER)
                .build();
        return memberRepository.save(member);
    }

    public OIDCDecodePayload getOIDCDecodePayload(SocialProvider provider, String idToken) {
        OIDCPublicKeysResponse oidcPublicKeysResponse = kakaoOauthClient.getKakaoOIDCOpenKeys();
        return oauthOIDCService.getPayloadFromIdToken(
                idToken,
                oauthProperties.getBaseUrl(provider),
                oauthProperties.getAppKey(provider),
                oidcPublicKeysResponse);
    }

}
