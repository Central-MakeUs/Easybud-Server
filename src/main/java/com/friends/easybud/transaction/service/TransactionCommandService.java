package com.friends.easybud.transaction.service;

import com.friends.easybud.member.domain.Member;
import com.friends.easybud.transaction.dto.TransactionRequest.TransactionCreateDto;

public interface TransactionCommandService {

    Long createTransaction(Member member, TransactionCreateDto request);

    Boolean deleteTransaction(Member member, Long transactionId);

}
