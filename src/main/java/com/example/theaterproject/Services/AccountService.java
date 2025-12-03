package com.example.theaterproject.Services;

import com.example.theaterproject.Models.Account;
import com.example.theaterproject.Models.Client;
import com.example.theaterproject.Models.Manager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing user accounts in the theater system.
 *
 * <p>
 * This singleton service maintains a list of client accounts and a single manager account.
 * It provides methods for adding clients, retrieving clients, and authenticating users.
 * The service initializes with a default manager account.
 * </p>
 */
public class AccountService {

    // Singleton instance
    private static AccountService aInstance;

    private final ObservableList<Client> aClientList = FXCollections.observableArrayList();
    private final Manager aManager; // only one manager

    private AccountService() {
        // initialize manager account
        this.aManager = new Manager("Manager","Password");
    }

    /**
     * Returns the singleton instance of AccountService, creating it if necessary.
     *
     * @return the singleton AccountService instance
     */
    public static AccountService getInstance() {
        if (aInstance == null) {
            aInstance = new AccountService();
        }
        return aInstance;
    }

    /**
     * Adds a new client account to the system.
     *
     * <p>
     * Validates that the username is not already in use before adding.
     * </p>
     *
     * @param pClient the client to add; cannot be null
     * @throws IllegalArgumentException if a user with the same username already exists
     */
    public void addClient(Client pClient) {
        // check for duplicate before adding new client account
        for (Account account : aClientList) {
            if (account.getUserName().equals(pClient.getUserName())) {
                throw new IllegalArgumentException("User already exists");
            }
        }
        aClientList.add(pClient);
    }

    /**
     * Retrieves the manager account.
     *
     * @return the Manager account
     */
    public Manager getManager() {
        return aManager;
    }

    /**
     * Retrieves the observable list of all client accounts.
     *
     * @return an ObservableList containing all client accounts
     */
    public ObservableList<Client> getClients() {
        return aClientList;
    }

    /**
     * Replaces the current client collection with a new collection.
     *
     * <p>
     * If the provided list is null, the current client list is cleared.
     * Otherwise, the list is completely replaced with the provided clients.
     * </p>
     *
     * @param pClients the new collection of clients, or null to clear the list
     */
    public void setClients(List<Client> pClients) {
        // if list is null, clear current list
        if (pClients == null) {
            this.aClientList.clear();
        } else {
            // ObservableList.setAll replace the current list with provided list
            this.aClientList.setAll(pClients);
        }
    }

    /**
     * Authenticates a user based on username and password.
     *
     * <p>
     * Checks first if the credentials match the manager account, then checks
     * all client accounts. Returns the matching Account if found, or null if
     * authentication fails.
     * </p>
     *
     * @param pUsername the username of the account to authenticate
     * @param pPassword the password of the account to authenticate
     * @return the authenticated Account (either Manager or Client), or null if authentication fails
     */
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
