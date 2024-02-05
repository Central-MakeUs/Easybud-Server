package com.friends.easybud.card.service;

import com.friends.easybud.card.domain.Card;
import com.friends.easybud.card.dto.CardRequest;
import com.friends.easybud.card.dto.CardRequest.CardCreateDto;
import com.friends.easybud.card.dto.CardRequest.CardUpdateDto;
import com.friends.easybud.card.repository.CardRepository;
import com.friends.easybud.global.exception.GeneralException;
import com.friends.easybud.global.response.code.ErrorStatus;
import com.friends.easybud.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CardCommandServiceImpl implements CardCommandService {

    private final CardRepository cardRepository;

    @Override
    public Long createCard(Member member, CardCreateDto request) {
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
    public Long updateCard(Member member, Long cardId, CardUpdateDto request) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CARD_NOT_FOUND));

        checkCardOwnership(member, card);

        card.update(request);

        return card.getId();
    }

    @Override
    public Boolean deleteCard(Member member, Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CARD_NOT_FOUND));

        checkCardOwnership(member, card);

        // TODO 연관된 Account 처리
        cardRepository.delete(card);
        return Boolean.TRUE;
    }

    private void checkCardOwnership(Member member, Card card) {
        if (!card.getMember().equals(member)) {
            throw new GeneralException(ErrorStatus.UNAUTHORIZED_CARD_ACCESS);
        }
    }

}
