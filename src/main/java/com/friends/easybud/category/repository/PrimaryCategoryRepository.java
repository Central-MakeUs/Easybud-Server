package com.friends.easybud.category.repository;

import com.friends.easybud.category.domain.PrimaryCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrimaryCategoryRepository extends JpaRepository<PrimaryCategory, Long> {
}
