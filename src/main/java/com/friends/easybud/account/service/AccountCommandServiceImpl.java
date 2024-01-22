package com.friends.easybud.account.service;

import com.friends.easybud.account.domain.Account;
import com.friends.easybud.account.domain.AccountType;
import com.friends.easybud.account.dto.AccountRequest.AccountWithCardCreateDto;
import com.friends.easybud.account.dto.AccountRequest.AccountWithTertiaryCategoryCreateDto;
import com.friends.easybud.account.repository.AccountRepository;
import com.friends.easybud.card.domain.Card;
import com.friends.easybud.card.repository.CardRepository;
import com.friends.easybud.category.domain.TertiaryCategory;
import com.friends.easybud.category.repository.TertiaryCategoryRepository;
import com.friends.easybud.global.exception.GeneralException;
import com.friends.easybud.global.response.code.ErrorStatus;
import com.friends.easybud.transaction.domain.Transaction;
import com.friends.easybud.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AccountCommandServiceImpl implements AccountCommandService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TertiaryCategoryRepository tertiaryCategoryRepository;
    private final CardRepository cardRepository;

    @Override
    public Long createAccountWithCard(AccountWithCardCreateDto request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.TRANSACTION_NOT_FOUND));
        Card card = cardRepository.findById(request.getCardId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.CARD_NOT_FOUND));

        Account account = buildAccountWithCard(request, transaction, card);
        accountRepository.save(account);
        return account.getId();
    }

    private Account buildAccountWithCard(AccountWithCardCreateDto request, Transaction transaction, Card card) {
        return Account.builder()
                .accountType(AccountType.of(request.getTypeName(), request.getTypeState()))
                .amount(request.getAmount())
                .transaction(transaction)
                .card(card)
                .build();
    }

    @Override
    public Long createAccountWithTertiaryCategory(AccountWithTertiaryCategoryCreateDto request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.TRANSACTION_NOT_FOUND));
        TertiaryCategory tertiaryCategory = tertiaryCategoryRepository.findById(request.getTertiaryCategoryId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.TERTIARY_CATEGORY_NOT_FOUND));

        Account account = buildAccountWithTertiaryCategory(request, transaction, tertiaryCategory);
        accountRepository.save(account);
        return account.getId();
    }

    private Account buildAccountWithTertiaryCategory(AccountWithTertiaryCategoryCreateDto request,
                                                     Transaction transaction,
                                                     TertiaryCategory tertiaryCategory) {
        return Account.builder()
                .accountType(AccountType.of(request.getTypeName(), request.getTypeState()))
                .amount(request.getAmount())
                .transaction(transaction)
                .tertiaryCategory(tertiaryCategory)
                .build();
    }

    @Override
    public Boolean deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ACCOUNT_NOT_FOUND));
        accountRepository.delete(account);
        return Boolean.TRUE;
    }
}
