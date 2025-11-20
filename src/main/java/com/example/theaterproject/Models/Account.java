package com.example.theaterproject.Models;

public class Account {
    private String aUserName;
    private String aPassword;

    public Account(String pUserName, String pPassword) {
        this.aUserName = pUserName;
        this.aPassword = pPassword;
    }

    public Account(Account pAccount) {
        this.aUserName = pAccount.aUserName;
        this.aPassword = pAccount.aPassword;
    }

    public String getUserName() {
        return this.aUserName;
    }

    public String getPassword() {
        return this.aPassword;
    }

    // These methods would contain more security if it were in a real-world scenario
    public void setUserName(String pUserName) {
        this.aUserName = pUserName;
    }

    public void setPassword(String pPassword) {
        this.aPassword = pPassword;
    }
}
