package com.friends.easybud.transaction.repository;

import static com.friends.easybud.category.domain.QPrimaryCategory.primaryCategory;
import static com.friends.easybud.category.domain.QSecondaryCategory.secondaryCategory;
import static com.friends.easybud.category.domain.QTertiaryCategory.tertiaryCategory;
import static com.friends.easybud.transaction.domain.QAccount.account;
import static com.friends.easybud.transaction.domain.QTransaction.transaction;

import com.friends.easybud.financial.dto.AccountInfo;
import com.friends.easybud.transaction.domain.AccountName;
import com.friends.easybud.transaction.domain.AccountState;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    public Optional<BigDecimal> sumOfAccountsByTypeAndMember(AccountName typeName,
                                                             Long memberId,
                                                             LocalDateTime startDate,
                                                             LocalDateTime endDate) {
        return Optional.ofNullable(queryFactory.select(account.amount.sum())
                .from(account)
                .join(account.transaction, transaction)
                .where(account.accountType.typeName.eq(typeName),
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

    @Override
    public boolean existsInitialNetAsset(Long memberId) {
        Integer exists = queryFactory.selectOne()
                .from(account)
                .join(account.transaction, transaction)
                .join(account.tertiaryCategory, tertiaryCategory)
                .join(tertiaryCategory.secondaryCategory, secondaryCategory)
                .where(secondaryCategory.content.eq("기초순자산"),
                        transaction.member.id.eq(memberId))
                .fetchFirst();

        return exists != null;
    }

    @Override
    public List<AccountInfo> getAccountInfosByAccountNameAndMember(AccountName accountName, Long memberId) {
        List<Tuple> results = queryFactory.select(account.amount, account.accountType.typeName,
                        account.accountType.typeState)
                .from(account)
                .join(account.transaction, transaction)
                .where(account.accountType.typeName.eq(accountName))
                .fetch();

        return results.stream().map(tuple -> {
            BigDecimal amount = tuple.get(account.amount);

            AccountState accountState = tuple.get(account.accountType.typeState);
            boolean isDecrease = accountState == AccountState.DECREASE;

            return new AccountInfo(amount, isDecrease);
        }).collect(Collectors.toList());
    }

}
