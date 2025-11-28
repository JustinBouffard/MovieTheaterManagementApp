package com.example.theaterproject.Models;

import java.util.ArrayList;
import java.util.List;

public class AccountService {

    // Singleton instance
    private static final AccountService aInstance = new AccountService();

    private final List<Client> aClientList = new ArrayList<>();
    private final Manager aManager; // only one manager

    private AccountService() {
        // initialize manager account
        this.aManager = new Manager("Manager","cinemaPassword");
    }

    public static AccountService getInstance() {
        return aInstance;
    }

    public void addClient(Client pClient) {
        // check for duplicate before adding new client account
        for (Account account : aClientList) {
            if (account.getUserName().equals(pClient.getUserName())) {
                throw new IllegalArgumentException("User already exists");
            }
        }
        aClientList.add(pClient);
    }

    public List<Client> getClients() {
        return aClientList;
    }

    // Login Validation
    public Account login(String pUsername, String pPassword) {

        // check if manager
        if (aManager != null &&
                aManager.getUserName().equals(pUsername) &&
                aManager.getPassword().equals(pPassword)) {
            return aManager;
        }

        // check which client
        for (Client client : aClientList) {
            if (client.getUserName().equals(pUsername) &&
                client.getPassword().equals(pPassword)) {
                return client;
            }
        }

        return null; // login failed or user not found

        /*
         * Can use this in login controller :
         *
         * if (user == null) {
         *     showError("Invalid credentials");
         * } else if (user instanceof Manager) {
         *     loadManagerMainView();
         * } else if (user instanceof Client) {
         *     loadClientMainView();
         * }
         */
    }
}
