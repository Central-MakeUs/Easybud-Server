package com.friends.easybud.card.repository;

import com.friends.easybud.card.domain.Card;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByMemberId(Long memberId);

}