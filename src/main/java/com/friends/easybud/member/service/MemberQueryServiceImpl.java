package com.friends.easybud.member.service;

import com.friends.easybud.global.exception.GeneralException;
import com.friends.easybud.global.response.code.ErrorStatus;
import com.friends.easybud.member.domain.Member;
import com.friends.easybud.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;

    @Override
    public Member getMemberByUid(String uid) {
        return memberRepository.findByUid(uid)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
    }

}
