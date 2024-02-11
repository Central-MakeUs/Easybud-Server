package com.friends.easybud.transaction.service;

import com.friends.easybud.card.domain.Card;
import com.friends.easybud.card.repository.CardRepository;
import com.friends.easybud.category.domain.TertiaryCategory;
import com.friends.easybud.category.repository.TertiaryCategoryRepository;
import com.friends.easybud.global.exception.GeneralException;
import com.friends.easybud.global.response.code.ErrorStatus;
import com.friends.easybud.member.domain.Member;
import com.friends.easybud.transaction.domain.Account;
import com.friends.easybud.transaction.domain.Transaction;
import com.friends.easybud.transaction.dto.TransactionRequest.AccountCreateDto;
import com.friends.easybud.transaction.dto.TransactionRequest.TransactionCreateDto;
import com.friends.easybud.transaction.repository.AccountRepository;
import com.friends.easybud.transaction.repository.TransactionRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TransactionCommandServiceImpl implements TransactionCommandService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final TertiaryCategoryRepository tertiaryCategoryRepository;


    @Override
    public Long createTransaction(Member member, TransactionCreateDto request) {
        checkForDuplicateTransaction(member);

        Transaction transaction = buildTransaction(request, member);
        transactionRepository.save(transaction);

        request.getAccounts().forEach(accountDto -> createAndSaveAccount(accountDto, transaction));

        return transaction.getId();
    }

    private Transaction buildTransaction(TransactionCreateDto request, Member member) {
        Transaction transaction = Transaction.builder()
                .date(request.getDate())
                .summary(request.getSummary())
                .member(member)
                .build();
        return transaction;
    }

    private void createAndSaveAccount(AccountCreateDto accountDto, Transaction transaction) {
        validateAccountDto(accountDto);

        Account account = null;
        if (accountDto.getCardId() != null) {
            Card card = cardRepository.findById(accountDto.getCardId())
                    .orElseThrow(() -> new GeneralException(ErrorStatus.CARD_NOT_FOUND));
            account = buildAccount(accountDto, card, null);
        } else if (accountDto.getTertiaryCategoryId() != null) {
            TertiaryCategory tertiaryCategory = tertiaryCategoryRepository.findById(accountDto.getTertiaryCategoryId())
                    .orElseThrow(() -> new GeneralException(ErrorStatus.TERTIARY_CATEGORY_NOT_FOUND));
            account = buildAccount(accountDto, null, tertiaryCategory);
        } else {
            account = buildAccount(accountDto, null, null);
        }

        account.addTransaction(transaction);
        accountRepository.save(account);
    }

    private Account buildAccount(AccountCreateDto accountDto, Card card, TertiaryCategory tertiaryCategory) {
        return Account.builder()
                .accountType(accountDto.getAccountType())
                .amount(accountDto.getAmount())
                .card(card)
                .tertiaryCategory(tertiaryCategory)
                .build();
    }

    private void validateAccountDto(AccountCreateDto accountDto) {
        boolean hasCardId = accountDto.getCardId() != null;
        boolean hasTertiaryCategoryId = accountDto.getTertiaryCategoryId() != null;

        if (hasCardId == hasTertiaryCategoryId && hasCardId == true) {
            throw new GeneralException(ErrorStatus.ACCOUNT_CREATION_RULE_VIOLATION);
        }
    }

    @Override
    public Boolean deleteTransaction(Member member, Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TRANSACTION_NOT_FOUND));

        checkTransactionOwnership(member, transaction);

        transactionRepository.delete(transaction);
        return Boolean.TRUE;
    }

    private void checkForDuplicateTransaction(Member member) {
        List<Transaction> transactions = transactionRepository.findLastTransactionByMember(member);
        Transaction lastTransaction = transactions.isEmpty() ? null : transactions.get(0);
        if (Duration.between(lastTransaction.getCreatedDate(), LocalDateTime.now()).getSeconds() < 30) {
            throw new GeneralException(ErrorStatus.DUPLICATE_TRANSACTION_CREATION);
        }
    }

    private void checkTransactionOwnership(Member member, Transaction transaction) {
        if (!transaction.getMember().equals(member)) {
            throw new GeneralException(ErrorStatus.UNAUTHORIZED_TRANSACTION_ACCESS);
        }
    }

}
