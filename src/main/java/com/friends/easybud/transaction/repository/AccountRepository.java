package com.friends.easybud.transaction.repository;

import com.friends.easybud.transaction.domain.Account;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {


    @Query("SELECT SUM(a.amount) FROM Account a " +
            "JOIN a.tertiaryCategory tc " +
            "JOIN tc.secondaryCategory sc " +
            "JOIN sc.primaryCategory pc " +
            "WHERE pc.content = :content AND tc.member.id = :memberId")
    BigDecimal sumOfAccountsByPrimaryCategoryContentAndMemberId(@Param("content") String content,
                                                                @Param("memberId") Long memberId);

    @Query("SELECT SUM(a.amount) FROM Account a " +
            "JOIN a.tertiaryCategory tc " +
            "JOIN tc.secondaryCategory sc " +
            "WHERE sc.content = :content AND tc.member.id = :memberId")
    BigDecimal sumOfAccountsBySecondaryCategoryContentAndMemberId(@Param("content") String content,
                                                                  @Param("memberId") Long memberId);
}