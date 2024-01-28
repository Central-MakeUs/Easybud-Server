package com.friends.easybud.transaction.service;

import com.friends.easybud.transaction.dto.TransactionRequest.TransactionCreateDto;

public interface TransactionCommandService {

    Long createTransaction(TransactionCreateDto request);

    Boolean deleteTransaction(Long transactionId);

}
