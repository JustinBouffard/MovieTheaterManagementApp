package com.example.theaterproject.Services;

import com.example.theaterproject.Models.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountService {

    // Temporary in-memory storage (replace with DB later)
    private static final List<Account> accounts = new ArrayList<>();

    static {
        // Default admin account for testing
        accounts.add(new Account("manager", "cinemaPassword"));
        accounts.add(new Account("client", "password"));
    }

    public Account authenticate(String username, String password) {
        // EXAMPLE — replace with your real lookup (DB, list, file, etc.)
        for (Account acc : AccountRepository.getAllAccounts()) {
            if (acc.getUserName().equals(username) &&
                    acc.getPassword().equals(password)) {
                return acc;
            }
        }
        return null;
    }
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }
}

