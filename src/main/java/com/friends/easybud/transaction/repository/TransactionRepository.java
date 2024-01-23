package com.friends.easybud.transaction.repository;

import com.friends.easybud.transaction.domain.Transaction;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Transaction> findTop3ByOrderByDateDesc();

}
