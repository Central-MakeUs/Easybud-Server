package com.friends.easybud.transaction.repository;

import com.friends.easybud.transaction.domain.Account;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {


    @Query("SELECT SUM(a.amount) FROM Account a " +
            "JOIN a.transaction t " +
            "JOIN a.tertiaryCategory tc " +
            "JOIN tc.secondaryCategory sc " +
            "JOIN sc.primaryCategory pc " +
            "WHERE pc.content = :content AND t.member.id = :memberId")
    BigDecimal sumOfAccountsByPrimaryCategoryContentAndMemberId(@Param("content") String content,
                                                                @Param("memberId") Long memberId);

    @Query("SELECT SUM(a.amount) FROM Account a " +
            "JOIN a.transaction t " +
            "JOIN a.tertiaryCategory tc " +
            "JOIN tc.secondaryCategory sc " +
            "WHERE sc.content = :content AND t.member.id = :memberId")
    BigDecimal sumOfAccountsBySecondaryCategoryContentAndMemberId(@Param("content") String content,
                                                                  @Param("memberId") Long memberId);

    @Query("SELECT SUM(a.amount) FROM Account a " +
            "JOIN a.transaction t " +
            "JOIN a.tertiaryCategory tc " +
            "JOIN tc.secondaryCategory sc " +
            "JOIN sc.primaryCategory pc " +
            "WHERE pc.content LIKE '수익%' " +
            "AND t.member.id = :memberId " +
            "AND t.date BETWEEN :startDate AND :endDate")
    BigDecimal sumOfRevenueAccountsByMemberIdAndTransactionDateRangeWithLike(
            @Param("memberId") Long memberId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT SUM(a.amount) FROM Account a " +
            "JOIN a.transaction t " +
            "JOIN a.tertiaryCategory tc " +
            "JOIN tc.secondaryCategory sc " +
            "JOIN sc.primaryCategory pc " +
            "WHERE pc.content = '비용' " +
            "AND t.member.id = :memberId " +
            "AND t.date BETWEEN :startDate AND :endDate")
    BigDecimal sumOfExpenseAccountsByMemberIdAndTransactionDateRange(
            @Param("memberId") Long memberId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

}