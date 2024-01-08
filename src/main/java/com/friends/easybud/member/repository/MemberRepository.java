package com.friends.easybud.member.repository;

import com.friends.easybud.member.domain.Member;
import com.friends.easybud.member.domain.SocialProvider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailAndSocialProvider(String email, SocialProvider socialProvider);
}
