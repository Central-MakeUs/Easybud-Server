package com.friends.easybud.card.service;

import com.friends.easybud.card.domain.Card;
import com.friends.easybud.member.domain.Member;
import java.util.List;

public interface CardQueryService {

    Card getCard(Member member, Long cardId);

    List<Card> getCards(Member member);

}
