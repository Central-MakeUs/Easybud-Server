package com.friends.easybud.account.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

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

    public static AccountType of(String typeName, String typeState) {
        return AccountType.builder()
                .typeName(AccountName.valueOf(typeName))
                .typeState(AccountState.valueOf(typeState))
                .build();
    }

}
