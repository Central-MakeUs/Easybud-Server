package com.friends.easybud.account.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class AccountType {

    @Enumerated(value = EnumType.STRING)
    private AccountName typeName;

    @Enumerated(value = EnumType.STRING)
    private AccountState typeStatus;

}
