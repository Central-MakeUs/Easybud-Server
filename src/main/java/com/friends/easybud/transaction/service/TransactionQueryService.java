package com.friends.easybud.transaction.service;

import com.friends.easybud.transaction.domain.Transaction;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionQueryService {

    Transaction getTransaction(Long transactionId);

    List<Transaction> getTransactionsByDate(LocalDateTime date);

    List<Transaction> getRecentTransactions();

}