package org.chat.messenger.service;

import org.chat.messenger.model.Account;
import org.chat.messenger.model.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account authenticate(String name, String password) {
        Account account = accountRepository.findByUsername(name);
        if (account != null && account.getPassword().equals(password)) {
            return account;
        } else {
            return null;
        }
    }
}

