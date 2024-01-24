package com.friends.easybud.card.domain;

import com.friends.easybud.card.dto.CardRequest.CardUpdateDto;
import com.friends.easybud.global.domain.BaseTimeEntity;
import com.friends.easybud.member.domain.Member;
import com.friends.easybud.transaction.domain.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Card extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int startDate;
    private int endDate;
    private int paymentDate;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "card")
    private List<Account> accounts = new ArrayList<>();

    @Builder
    public Card(int startDate, int endDate, int paymentDate, String name, Member member) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentDate = paymentDate;
        this.name = name;
        this.member = member;
    }

    public void update(CardUpdateDto updateDto) {
        this.startDate = updateDto.getStartDate();
        this.endDate = updateDto.getEndDate();
        this.paymentDate = updateDto.getPaymentDate();
        this.name = updateDto.getName();
    }

}