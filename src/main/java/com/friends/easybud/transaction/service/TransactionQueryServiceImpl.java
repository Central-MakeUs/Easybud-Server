package com.friends.easybud.transaction.service;

import com.friends.easybud.global.exception.GeneralException;
import com.friends.easybud.global.response.code.ErrorStatus;
import com.friends.easybud.member.domain.Member;
import com.friends.easybud.transaction.domain.Transaction;
import com.friends.easybud.transaction.repository.TransactionRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TransactionQueryServiceImpl implements TransactionQueryService {

    private final TransactionRepository transactionRepository;

    @Override
    public Transaction getTransaction(Member member, Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TRANSACTION_NOT_FOUND));
    }

    @Override
    public List<Transaction> getTransactionsBetweenDates(Member member, LocalDateTime startDateTime,
                                                         LocalDateTime endDateTime) {
        return transactionRepository.findByMemberIdAndDateBetween(member.getId(), startDateTime, endDateTime);
    }

    @Override
    public List<Transaction> getRecentTransactions(Member member) {
        return transactionRepository.findTop3ByMemberIdOrderByDateDesc(member.getId());
    }

}
