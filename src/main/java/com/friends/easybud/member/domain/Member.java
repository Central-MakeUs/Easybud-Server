package com.friends.easybud.member.domain;

import com.friends.easybud.card.domain.Card;
import com.friends.easybud.category.domain.TertiaryCategory;
import com.friends.easybud.global.domain.BaseTimeEntity;
import com.friends.easybud.transaction.domain.Transaction;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Collections;
import java.util.Set;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
    private List<TertiaryCategory> tertiaryCategories = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Card> cards = new ArrayList<>();

    @Builder
    public Member(SocialProvider socialProvider, String email, String name, Role role) {
        this.socialProvider = socialProvider;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public Set<GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role.name()));
    }

}
