package com.example.theaterproject.Models;

public class Client extends Account {
    public Client(String pUserName, String pPassword) {
        super(pUserName, pPassword);
    }

    public Client(Client pClient) {
        super(pClient);
    }
}
