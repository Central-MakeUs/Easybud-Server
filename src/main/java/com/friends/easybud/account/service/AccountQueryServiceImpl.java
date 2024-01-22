package com.friends.easybud.account.service;

import com.friends.easybud.account.domain.Account;
import com.friends.easybud.account.repository.AccountRepository;
import com.friends.easybud.global.exception.GeneralException;
import com.friends.easybud.global.response.code.ErrorStatus;
import com.friends.easybud.transaction.domain.Transaction;
import com.friends.easybud.transaction.repository.TransactionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AccountQueryServiceImpl implements AccountQueryService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<Account> getAccounts(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TRANSACTION_NOT_FOUND));
        return transaction.getAccounts();
    }

    @Override
    public Account getAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ACCOUNT_NOT_FOUND));
        return account;
    }
}
