package com.friends.easybud.account.domain;


import com.friends.easybud.card.domain.Card;
import com.friends.easybud.category.domain.TertiaryCategory;
import com.friends.easybud.global.domain.BaseTimeEntity;
import com.friends.easybud.transaction.domain.Transaction;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private AccountType accountType;

    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tertiary_category_id")
    private TertiaryCategory tertiaryCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @Builder
    public Account(AccountType accountType, BigDecimal amount, TertiaryCategory tertiaryCategory, Card card) {
        this.accountType = accountType;
        this.amount = amount;
        this.tertiaryCategory = tertiaryCategory;
        this.card = card;
    }

    public void addTransaction(Transaction transaction) {
        this.transaction = transaction;
        if (!transaction.getAccounts().contains(this)) {
            transaction.getAccounts().add(this);
        }
    }

}
