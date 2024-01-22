package com.friends.easybud.account.service;

import com.friends.easybud.account.domain.Account;
import java.util.List;

public interface AccountQueryService {

    List<Account> getAccounts(Long transactionId);

    Account getAccount(Long accountId);

}
