package com.friends.easybud.category.domain;

import com.friends.easybud.global.domain.BaseTimeEntity;
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
public class SecondaryCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_category_id")
    private PrimaryCategory primaryCategory;

    @OneToMany(mappedBy = "secondaryCategory")
    private List<TertiaryCategory> tertiaryCategories = new ArrayList<>();

    @Builder
    public SecondaryCategory(String content, PrimaryCategory primaryCategory) {
        this.content = content;
        this.primaryCategory = primaryCategory;
    }
}
