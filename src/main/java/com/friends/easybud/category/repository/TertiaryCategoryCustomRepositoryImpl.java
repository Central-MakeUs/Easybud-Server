package com.friends.easybud.category.repository;

import static com.friends.easybud.category.domain.QTertiaryCategory.tertiaryCategory;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TertiaryCategoryCustomRepositoryImpl implements TertiaryCategoryCustomRepository {

    private final JPAQueryFactory queryFactory;

    public TertiaryCategoryCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Boolean existsWithConditions(Long memberId,
                                        String content,
                                        Long secondaryCategoryId) {
        return queryFactory.select(tertiaryCategory.count())
                .from(tertiaryCategory)
                .where(tertiaryCategory.member.id.eq(memberId)
                        .or(tertiaryCategory.isDefault.isTrue()))
                .where(tertiaryCategory.content.eq(content))
                .where(tertiaryCategory.secondaryCategory.id.eq(secondaryCategoryId))
                .fetchOne() > 0;
    }

}
