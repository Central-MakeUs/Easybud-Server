package com.friends.easybud.category.repository;

import com.friends.easybud.category.domain.TertiaryCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TertiaryCategoryRepository extends JpaRepository<TertiaryCategory, Long> {

    List<TertiaryCategory> findByMemberIdOrIsDefaultTrue(Long memberId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM TertiaryCategory c " +
            "WHERE (c.member.id = :memberId OR c.isDefault = true) " +
            "AND c.content = :content AND c.secondaryCategory.id = :secondaryCategoryId")
    Boolean existsByMemberIdOrIsDefaultAndContentAndSecondaryCategoryId(Long memberId,
                                                                        String content,
                                                                        Long secondaryCategoryId);

}
