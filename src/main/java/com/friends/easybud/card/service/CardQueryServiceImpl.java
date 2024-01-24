package com.friends.easybud.card.service;

import com.friends.easybud.card.domain.Card;
import com.friends.easybud.card.repository.CardRepository;
import com.friends.easybud.global.exception.GeneralException;
import com.friends.easybud.global.response.code.ErrorStatus;
import com.friends.easybud.member.domain.Member;
import com.friends.easybud.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CardQueryServiceImpl implements CardQueryService {

    private final MemberRepository memberRepository;    // TODO MemberQueryService 주입
    private final CardRepository cardRepository;

    @Override
    public Card getCard(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CARD_NOT_FOUND));
    }

    @Override
    public List<Card> getCards() {
        Member member = memberRepository.findById(1L).get();    // TODO 로그인 된 사용자 정보 조회
        return member.getCards();
    }

}
