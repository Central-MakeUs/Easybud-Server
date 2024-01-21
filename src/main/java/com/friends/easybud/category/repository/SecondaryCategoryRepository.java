package com.friends.easybud.category.repository;

import com.friends.easybud.category.domain.SecondaryCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondaryCategoryRepository extends JpaRepository<SecondaryCategory, Long> {

    Optional<SecondaryCategory> findByContent(String name);

}
