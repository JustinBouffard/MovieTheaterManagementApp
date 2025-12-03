package com.example.theaterproject.Models;

/**
 * Represents a manager user in the theater project system.
 *
 * <p>
 * This class extends {@link Account} and inherits its authentication-related
 * fields and behaviors. A Manager is essentially a specialized type of Account
 * with additional privileges for managing theater operations.
 * </p>
 */
public class Manager extends Account {
    /**
     * Creates a new Manager with the specified username and password.
     *
     * @param pUserName the username for the manager
     * @param pPassword the password for the manager
     */
    public Manager(String pUserName, String pPassword) {
        super(pUserName, pPassword);
    }

    /**
     * Copy constructor that creates a new Manager instance based on
     * another existing Manager.
     *
     * @param pManager the manager to copy from
     */
    public Manager(Manager pManager) {
        super(pManager);
    }
}