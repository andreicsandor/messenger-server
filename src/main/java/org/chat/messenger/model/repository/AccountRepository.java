package org.chat.messenger.model.repository;

import org.chat.messenger.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a ORDER BY a.lastName, a.firstName")
    List<Account> findAll();

    Account findByUsername(String username);
}
