package com.friends.easybud.transaction.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.friends.easybud.category.domain.PrimaryCategory;
import com.friends.easybud.category.domain.SecondaryCategory;
import com.friends.easybud.category.domain.TertiaryCategory;
import com.friends.easybud.member.domain.Member;
import com.friends.easybud.transaction.domain.Account;
import com.friends.easybud.transaction.domain.Transaction;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class AccountCustomRepositoryImplTest {

    @Autowired
    EntityManager em;

    @Autowired
    AccountCustomRepository accountCustomRepository;

    private Member member;
    private PrimaryCategory primaryCategory;
    private SecondaryCategory secondaryCategory1;
    private SecondaryCategory secondaryCategory2;
    private TertiaryCategory tertiaryCategory1;
    private TertiaryCategory tertiaryCategory2;
    private Transaction transaction;
    private Account account1;
    private Account account2;


    @BeforeEach
    void setUp() {
        member = Member.builder().build();
        em.persist(member);

        primaryCategory = PrimaryCategory.builder()
                .content("primary")
                .build();
        em.persist(primaryCategory);

        secondaryCategory1 = SecondaryCategory.builder()
                .content("secondary-1")
                .primaryCategory(primaryCategory)
                .build();
        em.persist(secondaryCategory1);

        secondaryCategory2 = SecondaryCategory.builder()
                .content("secondary-2")
                .primaryCategory(primaryCategory)
                .build();
        em.persist(secondaryCategory2);

        tertiaryCategory1 = TertiaryCategory.builder()
                .content("tertiary-1")
                .secondaryCategory(secondaryCategory1)
                .build();
        em.persist(tertiaryCategory1);

        tertiaryCategory2 = TertiaryCategory.builder()
                .content("tertiary-2")
                .secondaryCategory(secondaryCategory2)
                .build();
        em.persist(tertiaryCategory2);

        transaction = Transaction.builder()
                .member(member)
                .date(LocalDateTime.now())
                .build();
        em.persist(transaction);

        account1 = Account.builder()
                .tertiaryCategory(tertiaryCategory1)
                .amount(BigDecimal.valueOf(100)).build();
        account1.addTransaction(transaction);
        em.persist(account1);

        account2 = Account.builder()
                .tertiaryCategory(tertiaryCategory2)
                .amount(BigDecimal.valueOf(200)).build();
        account2.addTransaction(transaction);
        em.persist(account2);

        em.flush();
        em.clear();
    }

    @Test
    void testSumByPrimaryCategoryAndMember() {
        BigDecimal sum = accountCustomRepository.sumByPrimaryCategoryAndMember(primaryCategory.getContent(),
                member.getId()).orElse(BigDecimal.ZERO);
        assertThat(sum).isEqualByComparingTo(BigDecimal.valueOf(300.0));
    }

    @Test
    void testSumBySecondaryCategoryAndMember() {
        BigDecimal sum1 = accountCustomRepository.sumBySecondaryCategoryAndMember(secondaryCategory1.getContent(),
                member.getId()).orElse(BigDecimal.ZERO);
        assertThat(sum1).isEqualByComparingTo(BigDecimal.valueOf(100.0));

        BigDecimal sum2 = accountCustomRepository.sumBySecondaryCategoryAndMember(secondaryCategory2.getContent(),
                member.getId()).orElse(BigDecimal.ZERO);
        assertThat(sum2).isEqualByComparingTo(BigDecimal.valueOf(200.0));
    }

    @Test
    void testSumOfRevenueByMemberAndDateWithLike() {
        // given
        PrimaryCategory revenueCategory1 = PrimaryCategory.builder()
                .content("수익(일반)")
                .build();
        em.persist(revenueCategory1);

        PrimaryCategory revenueCategory2 = PrimaryCategory.builder()
                .content("수익(납세)")
                .build();
        em.persist(revenueCategory2);

        SecondaryCategory revenueSecondaryCategory1 = SecondaryCategory.builder()
                .primaryCategory(revenueCategory1)
                .build();
        em.persist(revenueSecondaryCategory1);

        SecondaryCategory revenueSecondaryCategory2 = SecondaryCategory.builder()
                .primaryCategory(revenueCategory2)
                .build();
        em.persist(revenueSecondaryCategory2);

        TertiaryCategory revenueTertiaryCategory1 = TertiaryCategory.builder()
                .secondaryCategory(revenueSecondaryCategory1)
                .build();
        em.persist(revenueTertiaryCategory1);

        TertiaryCategory revenueTertiaryCategory2 = TertiaryCategory.builder()
                .secondaryCategory(revenueSecondaryCategory2)
                .build();
        em.persist(revenueTertiaryCategory2);

        Account revenueAccount1 = Account.builder()
                .tertiaryCategory(revenueTertiaryCategory1)
                .amount(BigDecimal.valueOf(150)).build();
        revenueAccount1.addTransaction(transaction);
        em.persist(revenueAccount1);

        Account revenueAccount2 = Account.builder()
                .tertiaryCategory(revenueTertiaryCategory2)
                .amount(BigDecimal.valueOf(200)).build();
        revenueAccount2.addTransaction(transaction);
        em.persist(revenueAccount2);

        em.flush();
        em.clear();

        // when
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        BigDecimal sum = accountCustomRepository.sumOfRevenueByMemberAndDateWithLike(member.getId(), startDate, endDate)
                .orElse(BigDecimal.ZERO);

        // then
        assertThat(sum).isEqualByComparingTo(BigDecimal.valueOf(350.0));
    }

    @Test
    void testSumOfExpensesByMemberAndDate() {
        // given
        PrimaryCategory expenseCategory = PrimaryCategory.builder()
                .content("비용")
                .build();
        em.persist(expenseCategory);

        SecondaryCategory expenseSecondaryCategory = SecondaryCategory.builder()
                .primaryCategory(expenseCategory)
                .build();
        em.persist(expenseSecondaryCategory);

        TertiaryCategory expenseTertiaryCategory = TertiaryCategory.builder()
                .secondaryCategory(expenseSecondaryCategory)
                .build();
        em.persist(expenseTertiaryCategory);

        Account expenseAccount = Account.builder()
                .tertiaryCategory(expenseTertiaryCategory)
                .amount(BigDecimal.valueOf(250))
                .build();
        expenseAccount.addTransaction(transaction);
        em.persist(expenseAccount);

        em.flush();
        em.clear();

        // when
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        BigDecimal sum = accountCustomRepository.sumOfExpensesByMemberAndDate(member.getId(), startDate, endDate)
                .orElse(BigDecimal.ZERO);

        // then
        assertThat(sum).isEqualByComparingTo(BigDecimal.valueOf(250.0));
    }

    @Test
    void testSumByCardAndDateRange() {
    }
}