package com.example.theaterproject.Models;

/**
 * Represents a user account in the theater project system.
 * Stores basic authentication information such as username and password.
 *
 * <p><b>Note:</b> In a real-world application, passwords should never be stored
 * in plain text. Proper hashing and security practices should be used.</p>
 */
public abstract class Account {
    private String aUserName;
    private String aPassword;

    /**
     * Creates a new Account with the specified username and password.
     *
     * @param pUserName the username associated with this account
     * @param pPassword the password associated with this account
     */
    public Account(String pUserName, String pPassword) {
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
}
