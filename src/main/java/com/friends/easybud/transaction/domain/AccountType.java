package com.friends.easybud.transaction.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class AccountType {

    @Enumerated(value = EnumType.STRING)
    private AccountName typeName;

    @Enumerated(value = EnumType.STRING)
    private AccountState typeState;

    @Builder
    public AccountType(AccountName typeName, AccountState typeState) {
        this.typeName = typeName;
        this.typeState = typeState;
    }

}
