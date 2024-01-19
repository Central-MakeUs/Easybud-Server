package com.friends.easybud.account.repository;

import com.friends.easybud.account.domain.AccountCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountCategoryRepository extends JpaRepository<AccountCategory, Long> {
}
