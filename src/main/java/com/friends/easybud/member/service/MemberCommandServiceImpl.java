package com.friends.easybud.member.service;

import com.friends.easybud.auth.dto.KakaoUserInfo;
import com.friends.easybud.jwt.dto.JwtToken;
import com.friends.easybud.jwt.service.JwtTokenProvider;
import com.friends.easybud.member.domain.Member;
import com.friends.easybud.member.domain.Role;
import com.friends.easybud.member.domain.SocialProvider;
import com.friends.easybud.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtToken kakaoLogin(KakaoUserInfo kakaoUserInfo) {
        Member member = memberRepository.findByEmailAndSocialProvider(kakaoUserInfo.getEmail(), SocialProvider.KAKAO)
                .orElseGet(() -> registerKakaoUser(kakaoUserInfo));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                member,
                null,
                member.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

    private Member registerKakaoUser(KakaoUserInfo kakaoUserInfo) {
        Member member = Member.builder()
                .socialProvider(SocialProvider.KAKAO)
                .email(kakaoUserInfo.getEmail())
                .name(kakaoUserInfo.getNickname())
                .role(Role.USER)
                .build();
        return memberRepository.save(member);
    }

}
