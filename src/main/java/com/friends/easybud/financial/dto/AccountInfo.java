package com.friends.easybud.financial.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountInfo {

    private BigDecimal amount;
    private boolean isDecrease;

}

