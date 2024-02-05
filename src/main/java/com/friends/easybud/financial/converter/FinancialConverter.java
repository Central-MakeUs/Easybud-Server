package com.friends.easybud.financial.converter;

import com.friends.easybud.financial.dto.FinancialResponse.ProfitLossDto;
import com.friends.easybud.financial.dto.FinancialResponse.ProfitLossListDto;
import java.util.List;

public class FinancialConverter {

    public static ProfitLossListDto toProfitLossListDto(List<ProfitLossDto> profitLosses) {
        return ProfitLossListDto.builder()
                .profitLosses(profitLosses)
                .build();
    }

}
