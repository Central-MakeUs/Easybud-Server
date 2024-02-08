package com.friends.easybud.auth.service;

import com.friends.easybud.auth.dto.IdTokenRequest;
import com.friends.easybud.auth.dto.OIDCDecodePayload;
import com.friends.easybud.auth.dto.OIDCPublicKeysResponse;
import com.friends.easybud.auth.dto.OauthProperties;
import com.friends.easybud.auth.dto.SocialLoginResponse;
import com.friends.easybud.auth.dto.SocialLoginType;
import com.friends.easybud.auth.feign.KakaoOauthClient;
import com.friends.easybud.jwt.JwtDto;
import com.friends.easybud.jwt.JwtProvider;
import com.friends.easybud.member.domain.Member;
import com.friends.easybud.member.domain.Role;
import com.friends.easybud.member.domain.SocialProvider;
import com.friends.easybud.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
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
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    public SocialLoginResponse socialLogin(SocialProvider provider, IdTokenRequest request) {
        OIDCDecodePayload oidcDecodePayload = getOIDCDecodePayload(provider, request.getIdToken());
        Optional<Member> existingMember = memberRepository.findByEmailAndSocialProvider(oidcDecodePayload.getEmail(),
                provider);

        SocialLoginType type;
        Member member;

        if (existingMember.isPresent()) {
            member = existingMember.get();
            type = SocialLoginType.LOGIN;
        } else {
            member = register(provider, oidcDecodePayload);
            type = SocialLoginType.REGISTER;
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(member, null, member.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtDto jwt = jwtProvider.generateToken(authentication);

        return toSocialLoginResponse(jwt, type);
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

    public SocialLoginResponse toSocialLoginResponse(JwtDto jwt, SocialLoginType type) {
        return SocialLoginResponse.builder()
                .type(type)
                .grantType(jwt.getGrantType())
                .accessToken(jwt.getAccessToken())
                .refreshToken(jwt.getRefreshToken()).build();
    }

}
