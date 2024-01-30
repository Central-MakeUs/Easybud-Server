package com.friends.easybud.category.repository;

import com.friends.easybud.category.domain.QSecondaryCategory;
import com.friends.easybud.category.domain.QTertiaryCategory;
import com.friends.easybud.member.domain.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TertiaryCategoryCustomRepositoryImpl implements TertiaryCategoryCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final QTertiaryCategory tertiaryCategory = QTertiaryCategory.tertiaryCategory;
    private final QSecondaryCategory secondaryCategory = QSecondaryCategory.secondaryCategory;
    private final QMember member = QMember.member;

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
