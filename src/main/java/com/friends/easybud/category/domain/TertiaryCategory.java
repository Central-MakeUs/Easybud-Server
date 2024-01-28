package com.friends.easybud.category.domain;

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
public class TertiaryCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private Boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secondary_category_id")
    private SecondaryCategory secondaryCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "tertiaryCategory")
    private List<Account> accounts = new ArrayList<>();

    @Builder
    public TertiaryCategory(String content, Boolean isDefault, SecondaryCategory secondaryCategory, Member member) {
        this.content = content;
        this.isDefault = isDefault;
        this.secondaryCategory = secondaryCategory;
        this.member = member;
    }
}