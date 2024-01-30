package com.friends.easybud.category.repository;

import com.friends.easybud.category.domain.TertiaryCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TertiaryCategoryRepository extends JpaRepository<TertiaryCategory, Long> {

    List<TertiaryCategory> findByMemberIdOrIsDefaultTrue(Long memberId);

}