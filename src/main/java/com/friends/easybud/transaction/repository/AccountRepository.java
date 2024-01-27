package com.friends.easybud.transaction.repository;

import com.friends.easybud.transaction.domain.Account;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
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
    Optional<BigDecimal> sumOfAccountsByPrimaryCategoryContentAndMemberId(@Param("content") String content,
                                                                          @Param("memberId") Long memberId);

    @Query("SELECT SUM(a.amount) FROM Account a " +
            "JOIN a.transaction t " +
            "JOIN a.tertiaryCategory tc " +
            "JOIN tc.secondaryCategory sc " +
            "WHERE sc.content = :content AND t.member.id = :memberId")
    Optional<BigDecimal> sumOfAccountsBySecondaryCategoryContentAndMemberId(@Param("content") String content,
                                                                            @Param("memberId") Long memberId);

    @Query("SELECT SUM(a.amount) FROM Account a " +
            "JOIN a.transaction t " +
            "JOIN a.tertiaryCategory tc " +
            "JOIN tc.secondaryCategory sc " +
            "JOIN sc.primaryCategory pc " +
            "WHERE pc.content LIKE '수익%' " +
            "AND t.member.id = :memberId " +
            "AND t.date BETWEEN :startDate AND :endDate")
    Optional<BigDecimal> sumOfRevenueAccountsByMemberIdAndTransactionDateRangeWithLike(
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
    Optional<BigDecimal> sumOfExpenseAccountsByMemberIdAndTransactionDateRange(
            @Param("memberId") Long memberId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT SUM(a.amount) FROM Account a " +
            "WHERE a.card.id = :cardId " +
            "AND a.transaction.date >= :startDate " +
            "AND a.transaction.date <= :endDate")
    Optional<BigDecimal> sumOfTransactionsByCardIdAndDateRange(
            @Param("cardId") Long cardId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

}