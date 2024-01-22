package com.friends.easybud.account.service;

import com.friends.easybud.account.dto.AccountRequest.AccountWithCardCreateDto;
import com.friends.easybud.account.dto.AccountRequest.AccountWithTertiaryCategoryCreateDto;

public interface AccountCommandService {

    Long createAccountWithCard(AccountWithCardCreateDto request);

    Long createAccountWithTertiaryCategory(AccountWithTertiaryCategoryCreateDto request);

    Boolean deleteAccount(Long accountId);

}
