package com.example.theaterproject.Models;

/**
 * Represents a client user in the theater project system.
 *
 * <p>
 * This class extends {@link Account} and inherits its authentication-related
 * fields and behaviors. A Client is essentially a specialized type of Account.
 * </p>
 */
public class Client extends Account {

    private String aEmail;

    /**
     * Creates a new Client with the specified username, password, and email.
     *
     * @param pUserName the username for the client
     * @param pPassword the password for the client
     * @param pEmail    the email address for the client
     */
    public Client(String pUserName, String pPassword, String pEmail) {
        super(pUserName, pPassword);
        this.aEmail = pEmail;
    }

    /**
     * Copy constructor that creates a new Client instance based on
     * another existing Client.
     *
     * @param pClient the client to copy from
     */
    public Client(Client pClient) {
        super(pClient);
        this.aEmail = pClient.aEmail;
    }

    /**
     * Retrieves the email address associated with this client.
     *
     * @return the email address
     */
    public String getEmail() {
        return this.aEmail;
    }

    /**
     * Sets the email address for this client.
     *
     * @param pEmail the new email address
     */
    public void setEmail(String pEmail) {
        this.aEmail = pEmail;
    }
}
