package com.friends.easybud.member.service;


import com.friends.easybud.card.repository.CardRepository;
import com.friends.easybud.category.repository.TertiaryCategoryRepository;
import com.friends.easybud.member.domain.Member;
import com.friends.easybud.member.repository.MemberRepository;
import com.friends.easybud.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final TertiaryCategoryRepository tertiaryCategoryRepository;
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public boolean withdrawal(Member member) {
        tertiaryCategoryRepository.deleteAllByMember(member);
        cardRepository.deleteAllByMember(member);
        transactionRepository.deleteAllByMember(member);
        memberRepository.delete(member);
        return true;
    }
}
