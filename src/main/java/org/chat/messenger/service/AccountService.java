package org.chat.messenger.service;

import org.chat.messenger.model.Account;
import org.chat.messenger.model.Message;
import org.chat.messenger.model.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Account> listAccounts() {
        List<Account> accounts = accountRepository.findAll();
        if (accounts.isEmpty())
            return null;
        else
            return accounts;
    }
}

