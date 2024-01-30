package com.friends.easybud.category.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.friends.easybud.category.domain.PrimaryCategory;
import com.friends.easybud.category.domain.SecondaryCategory;
import com.friends.easybud.category.domain.TertiaryCategory;
import com.friends.easybud.member.domain.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class TertiaryCategoryCustomRepositoryImplTest {

    @Autowired
    EntityManager em;

    @Autowired
    TertiaryCategoryCustomRepository tertiaryCategoryCustomRepository;


    @Test
    void testExistsWithConditions() {
        // given
        Member member = Member.builder().build();
        em.persist(member);

        Member member2 = Member.builder().build();
        em.persist(member2);

        PrimaryCategory primary = PrimaryCategory.builder()
                .content("primary").build();
        em.persist(primary);

        SecondaryCategory secondary = SecondaryCategory.builder()
                .content("secondary")
                .primaryCategory(primary).build();
        em.persist(secondary);

        TertiaryCategory tertiary = TertiaryCategory.builder()
                .content("tertiary")
                .member(member)
                .secondaryCategory(secondary)
                .isDefault(false)
                .build();
        em.persist(tertiary);

        TertiaryCategory defaultTertiary = TertiaryCategory.builder()
                .content("default tertiary")
                .member(member)
                .secondaryCategory(secondary)
                .isDefault(true)
                .build();
        em.persist(defaultTertiary);

        TertiaryCategory member2Tertiary = TertiaryCategory.builder()
                .content("member2 tertiary")
                .member(member2)
                .secondaryCategory(secondary)
                .isDefault(false)
                .build();
        em.persist(member2Tertiary);

        em.flush();
        em.clear();

        // when
        Boolean exists = tertiaryCategoryCustomRepository.existsWithConditions(
                member.getId(),
                "tertiary",
                secondary.getId());

        Boolean defaultExists = tertiaryCategoryCustomRepository.existsWithConditions(
                member.getId(),
                "default tertiary",
                secondary.getId());

        Boolean member2TertiaryExists = tertiaryCategoryCustomRepository.existsWithConditions(
                member.getId(),
                "member2 tertiary",
                secondary.getId());

        // then
        assertThat(exists).isTrue();
        assertThat(defaultExists).isTrue();
        assertThat(member2TertiaryExists).isFalse();

    }
}