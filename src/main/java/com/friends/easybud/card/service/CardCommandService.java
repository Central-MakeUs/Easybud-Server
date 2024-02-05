package com.friends.easybud.card.service;

import com.friends.easybud.card.dto.CardRequest.CardCreateDto;
import com.friends.easybud.card.dto.CardRequest.CardUpdateDto;
import com.friends.easybud.member.domain.Member;

public interface CardCommandService {

    Long createCard(Member member, CardCreateDto request);

    Long updateCard(Member member, Long cardId, CardUpdateDto request);

    Boolean deleteCard(Member member, Long cardId);

}
