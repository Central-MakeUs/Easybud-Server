package com.friends.easybud.card.converter;

import com.friends.easybud.card.domain.Card;
import com.friends.easybud.card.dto.CardResponse.CardDto;
import com.friends.easybud.card.dto.CardResponse.CardListDto;
import java.util.List;
import java.util.stream.Collectors;

public class CardConverter {

    public static CardListDto toCardListDto(List<Card> cards) {
        List<CardDto> cardDtoList = cards.stream().map(CardConverter::toCardDto)
                .collect(Collectors.toList());
        return CardListDto.builder()
                .cards(cardDtoList)
                .build();
    }

    public static CardDto toCardDto(Card card) {
        return CardDto.builder()
                .cardId(card.getId())
                .startDate(card.getStartDate())
                .endDate(card.getEndDate())
                .paymentDate(card.getPaymentDate())
                .name(card.getName())
                .summary(card.getSummary())
                .build();
    }

}
