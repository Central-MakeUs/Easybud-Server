package com.friends.easybud.transaction.service;

import com.friends.easybud.transaction.domain.Transaction;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionQueryService {

    Transaction getTransaction(Long transactionId);

    List<Transaction> getTransactionsBetweenDates(LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<Transaction> getRecentTransactions();

}