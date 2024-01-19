package com.friends.easybud.member.domain;

import com.friends.easybud.account.domain.AccountCategory;
import com.friends.easybud.global.domain.BaseTimeEntity;
import com.friends.easybud.transaction.domain.Transaction;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SocialProvider socialProvider;

    private String email;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Transaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<AccountCategory> accountCategories = new ArrayList<>();

    @Builder
    public Member(String name, SocialProvider socialProvider, String email, Role role) {
        this.name = name;
        this.socialProvider = socialProvider;
        this.email = email;
        this.role = role;
    }

}
