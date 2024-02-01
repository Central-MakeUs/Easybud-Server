package com.friends.easybud.transaction.repository;

import com.friends.easybud.transaction.domain.AccountName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface AccountCustomRepository {

    Optional<BigDecimal> sumByPrimaryCategoryAndMember(String content, Long memberId);

    Optional<BigDecimal> sumBySecondaryCategoryAndMember(String content, Long memberId);

    Optional<BigDecimal> sumOfRevenueByMemberAndDateWithLike(Long memberId,
                                                             LocalDateTime startDate,
                                                             LocalDateTime endDate);

    Optional<BigDecimal> sumOfExpensesByMemberAndDate(Long memberId,
                                                      LocalDateTime startDate,
                                                      LocalDateTime endDate);

    Optional<BigDecimal> sumOfAccountsByTypeAndMember(AccountName typeName,
                                                      Long memberId,
                                                      LocalDateTime startDate,
                                                      LocalDateTime endDate);

    Optional<BigDecimal> sumByCardAndDateRange(Long cardId, LocalDateTime startDate, LocalDateTime endDate);

    boolean existsInitialNetAsset(Long memberId);

}
