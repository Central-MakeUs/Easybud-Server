package com.friends.easybud.transaction.service;

import com.friends.easybud.member.domain.Member;
import com.friends.easybud.transaction.domain.Transaction;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionQueryService {

    Transaction getTransaction(Member member, Long transactionId);

    List<Transaction> getTransactionsBetweenDates(Member member, LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<Transaction> getRecentTransactions(Member member);

}