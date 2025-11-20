package com.example.theaterproject.Models;

public class Account {
    private String aUserName;
    private String aPassword;

    public Account(String pUserName, String pPassword) {
        aUserName = pUserName;
        aPassword = pPassword;
    }

    public String getUserName() {
        return aUserName;
    }

    public String getPassword() {
        return aPassword;
    }

    // These methods would contain more security if it were in a real-world scenario
    public void setUserName(String pUserName) {
        aUserName = pUserName;
    }

    public void setPassword(String pPassword) {
        aPassword = pPassword;
    }
}
