package com.friends.easybud.card.service;

import com.friends.easybud.card.domain.Card;
import java.util.List;

public interface CardQueryService {

    Card getCard(Long cardId);

    List<Card> getCards();

}
