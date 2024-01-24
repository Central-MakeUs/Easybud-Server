package com.friends.easybud.card.service;

import com.friends.easybud.card.dto.CardRequest.CardCreateDto;
import com.friends.easybud.card.dto.CardRequest.CardUpdateDto;

public interface CardCommandService {

    Long createCard(CardCreateDto request);

    Long updateCard(Long cardId, CardUpdateDto request);

    Boolean deleteCard(Long cardId);

}
