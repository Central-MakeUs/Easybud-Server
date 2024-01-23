package com.friends.easybud.transaction.repository;

import com.friends.easybud.transaction.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
