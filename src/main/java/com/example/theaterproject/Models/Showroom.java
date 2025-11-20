package com.example.theaterproject.Models;

public class Showroom {
    private String aName;
    private int aCapacity;

    public Showroom (String pName, int pCapacity) {
        this.aName = pName;
        this.aCapacity = pCapacity;
    }

    private String getShowroomName() {
        return this.aName;
    }

    private int getShowroomCapacity() {
        return this.aCapacity;
    }

    private void setShowroomName(String pName) {
        this.aName = pName;
    }

    private void setShowroomCapacity(int pCapacity) {
        this.aCapacity = pCapacity;
    }

}
