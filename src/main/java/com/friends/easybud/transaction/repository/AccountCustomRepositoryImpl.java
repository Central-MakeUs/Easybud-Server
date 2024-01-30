package com.friends.easybud.transaction.repository;

import static com.friends.easybud.category.domain.QPrimaryCategory.primaryCategory;
import static com.friends.easybud.category.domain.QSecondaryCategory.secondaryCategory;
import static com.friends.easybud.category.domain.QTertiaryCategory.tertiaryCategory;
import static com.friends.easybud.transaction.domain.QAccount.account;
import static com.friends.easybud.transaction.domain.QTransaction.transaction;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class AccountCustomRepositoryImpl implements AccountCustomRepository {

    private final JPAQueryFactory queryFactory;

    public AccountCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<BigDecimal> sumByPrimaryCategoryAndMember(String content, Long memberId) {
        return Optional.ofNullable(queryFactory.select(account.amount.sum())
                .from(account)
                .join(account.transaction, transaction)
                .join(account.tertiaryCategory, tertiaryCategory)
                .join(tertiaryCategory.secondaryCategory, secondaryCategory)
                .join(secondaryCategory.primaryCategory, primaryCategory)
                .where(primaryCategory.content.eq(content),
                        transaction.member.id.eq(memberId))
                .fetchOne());
    }

    @Override
    public Optional<BigDecimal> sumBySecondaryCategoryAndMember(String content, Long memberId) {
        return Optional.ofNullable(queryFactory.select(account.amount.sum())
                .from(account)
                .join(account.transaction, transaction)
                .join(account.tertiaryCategory, tertiaryCategory)
                .join(tertiaryCategory.secondaryCategory, secondaryCategory)
                .where(secondaryCategory.content.eq(content),
                        transaction.member.id.eq(memberId))
                .fetchOne());
    }

    @Override
    public Optional<BigDecimal> sumOfRevenueByMemberAndDateWithLike(Long memberId,
                                                                    LocalDateTime startDate,
                                                                    LocalDateTime endDate) {
        return Optional.ofNullable(queryFactory.select(account.amount.sum())
                .from(account)
                .join(account.transaction, transaction)
                .join(account.tertiaryCategory, tertiaryCategory)
                .join(tertiaryCategory.secondaryCategory, secondaryCategory)
                .join(secondaryCategory.primaryCategory, primaryCategory)
                .where(primaryCategory.content.like("수익%"),
                        transaction.member.id.eq(memberId),
                        transaction.date.between(startDate, endDate))
                .fetchOne());
    }

    @Override
    public Optional<BigDecimal> sumOfExpensesByMemberAndDate(Long memberId,
                                                             LocalDateTime startDate,
                                                             LocalDateTime endDate) {
        return Optional.ofNullable(queryFactory.select(account.amount.sum())
                .from(account)
                .join(account.transaction, transaction)
                .join(account.tertiaryCategory, tertiaryCategory)
                .join(tertiaryCategory.secondaryCategory, secondaryCategory)
                .join(secondaryCategory.primaryCategory, primaryCategory)
                .where(primaryCategory.content.eq("비용"),
                        transaction.member.id.eq(memberId),
                        transaction.date.between(startDate, endDate))
                .fetchOne());
    }

    @Override
    public Optional<BigDecimal> sumByCardAndDateRange(Long cardId, LocalDateTime startDate, LocalDateTime endDate) {

        return Optional.ofNullable(queryFactory.select(account.amount.sum())
                .from(account)
                .join(account.transaction, transaction)
                .where(account.card.id.eq(cardId),
                        transaction.date.between(startDate, endDate))
                .fetchOne());
    }
}
