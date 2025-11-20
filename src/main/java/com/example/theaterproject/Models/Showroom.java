package com.example.theaterproject.Models;

/**
 * Represents a showroom in a theater, containing a name and seating capacity.
 * This class provides basic data storage and accessor methods for showroom
 * attributes.
 */
public class Showroom {
    /** The name of the showroom. */
    private String aName;

    /** The seating capacity of the showroom. */
    private int aCapacity;

    /**
     * Constructs a new {@code Showroom} with the specified name and capacity.
     *
     * @param pName     the name of the showroom
     * @param pCapacity the seating capacity of the showroom
     */
    public Showroom (String pName, int pCapacity) {
        this.aName = pName;
        this.aCapacity = pCapacity;
    }

    /**
     * Returns the name of the showroom.
     *
     * @return the showroom name
     */
    private String getShowroomName() {
        return this.aName;
    }

    /**
     * Returns the seating capacity of the showroom.
     *
     * @return the showroom capacity
     */
    private int getShowroomCapacity() {
        return this.aCapacity;
    }

    /**
     * Sets the name of the showroom.
     *
     * @param pName the new showroom name
     */
    private void setShowroomName(String pName) {
        this.aName = pName;
    }

    /**
     * Sets the seating capacity of the showroom.
     *
     * @param pCapacity the new seating capacity
     */
    private void setShowroomCapacity(int pCapacity) {
        this.aCapacity = pCapacity;
    }

    /**
     * Returns a string representation of the showroom, including its name and capacity.
     *
     * @return a formatted string describing the showroom
     */
    @Override
    public String toString() {
        return "Showroom: " + this.aName + "Capacity: " + this.aCapacity;
    }
}
