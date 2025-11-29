package com.example.theaterproject.Models;

/**
 * Represents a user account in the theater project system.
 * Stores basic authentication information such as username and password.
 *
 * <p><b>Note:</b> In a real-world application, passwords should never be stored
 * in plain text. Proper hashing and security practices should be used.</p>
 */
public class Account {
    private String aUserName;
    private String aPassword;

    /**
     * Creates a new Account with the specified username and password.
     *
     * @param pUserName the username associated with this account
     * @param pPassword the password associated with this account
     */
    public Account(String pUserName, String pPassword) {
        validateUsername(pUserName);
        validatePassword(pPassword);

        this.aUserName = pUserName;
        this.aPassword = pPassword;
    }

    /**
     * Copy constructor that creates a new Account instance
     * based on the values of another Account.
     *
     * @param pAccount the account to copy
     */
    public Account(Account pAccount) {
        this.aUserName = pAccount.aUserName;
        this.aPassword = pAccount.aPassword;
    }

    /**
     * Returns the username associated with this account.
     *
     * @return the username
     */
    public String getUserName() {
        return this.aUserName;
    }

    /**
     * Returns the password associated with this account.
     *
     * <p><b>Warning:</b> In production software, exposing a password
     * directly through a getter is not secure.</p>
     *
     * @return the password
     */
    public String getPassword() {
        return this.aPassword;
    }

    /**
     * Updates the username for this account.
     *
     * @param pUserName the new username
     */
    public void setUserName(String pUserName) {
        this.aUserName = pUserName;
    }

    /**
     * Updates the password for this account.
     *
     * <p><b>Note:</b> In secure applications, passwords should be hashed before storage.</p>
     *
     * @param pPassword the new password
     */
    public void setPassword(String pPassword) {
        this.aPassword = pPassword;
    }

    /**
     * Validates that the provided username meets the required criteria.
     *
     * <p>The username must:</p>
     * <ul>
     *   <li>Not be null or blank</li>
     *   <li>Contain only letters (A-Z, a-z), numbers (0-9), and underscores (_)</li>
     * </ul>
     *
     * @param pUsername the username to validate
     * @throws IllegalArgumentException if the username is null, blank, or contains invalid characters
     */
    private void validateUsername(String pUsername) {
        if (pUsername == null || pUsername.isBlank())
            throw new IllegalArgumentException("Username cannot be blank.");
        if (!pUsername.matches("[A-Za-z0-9_]+"))
            throw new IllegalArgumentException("Username can only be composed of letters, numbers and underscores");
    }

    /**
     * Validates that the provided password meets the required criteria.
     *
     * <p>The password must not be null or blank.</p>
     *
     * @param pPassword the password to validate
     * @throws IllegalArgumentException if the password is null or blank
     */
    private void validatePassword(String pPassword) {
        if (pPassword == null || pPassword.isBlank())
            throw new IllegalArgumentException("Password cannot be empty");
    }
}
