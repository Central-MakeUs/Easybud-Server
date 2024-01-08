package com.friends.easybud.auth.dto;

import com.friends.easybud.member.domain.Member;
import com.friends.easybud.member.domain.Role;
import com.friends.easybud.member.domain.SocialProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class OAuthProfile {

    private final SocialProvider socialProvider;
    private final String email;
    private final String name;

    public Member toMember() {
        return Member.builder()
                .socialProvider(socialProvider)
                .email(email)
                .name(name)
                .role(Role.USER)
                .build();
    }
}
