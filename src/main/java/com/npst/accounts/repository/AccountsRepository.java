package com.npst.accounts.repository;

import com.npst.accounts.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository  extends JpaRepository<Accounts, Integer> {
}
