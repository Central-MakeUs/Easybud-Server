package com.friends.easybud.auth.service;

import com.friends.easybud.auth.dto.OAuthAttributes;
import com.friends.easybud.auth.dto.OAuthProfile;
import com.friends.easybud.member.domain.Member;
import com.friends.easybud.member.repository.MemberRepository;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        OAuthProfile oAuthProfile = extractOAuthProfile(userRequest, oAuth2User);
        Member member = saveOAuthProfile(oAuthProfile);
        return createOAuth2User(member, oAuth2User.getAttributes(), getUserNameAttributeName(userRequest));
    }

    private OAuthProfile extractOAuthProfile(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        return OAuthAttributes.extract(registrationId, attributes);
    }

    private Member saveOAuthProfile(OAuthProfile userProfile) {
        return memberRepository.findByEmailAndSocialProvider(userProfile.getEmail(), userProfile.getSocialProvider())
                .orElseGet(() -> memberRepository.save(userProfile.toMember()));
    }

    private DefaultOAuth2User createOAuth2User(Member member, Map<String, Object> attributes,
                                               String userNameAttributeName) {
        return new DefaultOAuth2User(
                Collections.singletonList(new SimpleGrantedAuthority(member.getRole().getKey())),
                attributes,
                userNameAttributeName
        );
    }

    private String getUserNameAttributeName(OAuth2UserRequest userRequest) {
        return userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();
    }

}