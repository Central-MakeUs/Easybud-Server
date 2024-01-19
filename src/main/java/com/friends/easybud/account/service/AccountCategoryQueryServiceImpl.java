package com.friends.easybud.account.service;

import com.friends.easybud.account.domain.AccountCategory;
import com.friends.easybud.member.domain.Member;
import com.friends.easybud.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AccountCategoryQueryServiceImpl implements AccountCategoryQueryService {

    private final MemberRepository memberRepository;    // TODO MemberQueryService 주입

    @Override
    public List<AccountCategory> getAccountCategories() {
        Member member = memberRepository.findById(1L).get();    // TODO 로그인 된 사용자 정보 조회
        return member.getAccountCategories();
    }

}
