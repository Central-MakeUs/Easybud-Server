package com.friends.easybud.card.service;

import com.friends.easybud.card.domain.Card;
import com.friends.easybud.card.dto.CardRequest;
import com.friends.easybud.card.dto.CardRequest.CardCreateDto;
import com.friends.easybud.card.dto.CardRequest.CardUpdateDto;
import com.friends.easybud.card.repository.CardRepository;
import com.friends.easybud.global.exception.GeneralException;
import com.friends.easybud.global.response.code.ErrorStatus;
import com.friends.easybud.member.domain.Member;
import com.friends.easybud.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CardCommandServiceImpl implements CardCommandService {

    private final MemberRepository memberRepository;    // TODO MemberQueryService 주입
    private final CardRepository cardRepository;

    @Override
    public Long createCard(CardCreateDto request) {
        Member member = memberRepository.findById(1L).get();    // TODO 로그인 된 사용자 정보 조회

        Card card = buildCard(request, member);
        cardRepository.save(card);

        return card.getId();
    }

    private Card buildCard(CardRequest.CardCreateDto request, Member member) {
        return Card.builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .paymentDate(request.getPaymentDate())
                .name(request.getName())
                .summary(request.getSummary())
                .member(member).build();
    }

    @Override
    public Long updateCard(Long cardId, CardUpdateDto request) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CARD_NOT_FOUND));

        card.update(request);

        return card.getId();
    }

    @Override
    public Boolean deleteCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CARD_NOT_FOUND));
        // TODO 연관된 Account 처리
        cardRepository.delete(card);
        return Boolean.TRUE;
    }

}
