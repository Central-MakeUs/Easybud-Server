package com.friends.easybud.transaction.repository;

import com.friends.easybud.member.domain.Member;
import com.friends.easybud.transaction.domain.Transaction;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByMemberIdAndDateBetween(Long memberId, LocalDateTime startDateTime,
                                                   LocalDateTime endDateTime);

    List<Transaction> findTop3ByMemberIdOrderByDateDesc(Long memberId);

    void deleteAllByMember(Member member);

}
