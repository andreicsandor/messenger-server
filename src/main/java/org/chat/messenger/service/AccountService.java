package org.chat.messenger.service;

import org.chat.messenger.model.Account;
import org.chat.messenger.model.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    private final ConcurrentHashMap<String, Boolean> activeAccounts = new ConcurrentHashMap<>();

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

    public void addActiveUser(String username) {
        activeAccounts.put(username, true);
    }

    public void removeActiveUser(String username) {
        activeAccounts.remove(username);
    }

    public boolean isUserActive(String username) {
        return activeAccounts.containsKey(username);
    }

    public List<String> listActiveUsers() {
        List<String> activeUsers = new ArrayList<>(activeAccounts.keySet());
        return activeUsers;
    }
}
