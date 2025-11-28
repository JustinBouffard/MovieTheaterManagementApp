package com.example.theaterproject.Models;

import java.util.Collections;
import java.util.List;

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

    private List<Screening> aScreenings;

    /**
     * Constructs a new {@code Showroom} with the specified name and capacity.
     *
     * @param pName     the name of the showroom
     * @param pCapacity the seating capacity of the showroom
     */
    public Showroom (String pName, int pCapacity) {
        if (pName == null || pName.isBlank()) {
            throw new IllegalArgumentException("Showroom name cannot be empty");
        }
        if (pCapacity <= 0) {
            throw new IllegalArgumentException("Showroom capacity cannot be 0 or below.");
        }
        this.aName = pName;
        this.aCapacity = pCapacity;
    }

    /**
     * Returns the name of the showroom.
     *
     * @return the showroom name
     */
    public String getShowroomName() {
        return this.aName;
    }

    /**
     * Returns the seating capacity of the showroom.
     *
     * @return the showroom capacity
     */
    public int getShowroomCapacity() {
        return this.aCapacity;
    }

    /**
     * Sets the name of the showroom.
     *
     * @param pName the new showroom name
     */
    public void setShowroomName(String pName) {
        this.aName = pName;
    }

    /**
     * Sets the seating capacity of the showroom.
     *
     * @param pCapacity the new seating capacity
     */
    public void setShowroomCapacity(int pCapacity) {
        this.aCapacity = pCapacity;
    }

    public List<Screening> getShowroomScreenings() {
        return Collections.unmodifiableList(this.aScreenings);
    }

    public void addScreening(Screening pScreening) {
        this.aScreenings.add(pScreening);
    }

    public void removeScreening(Screening pScreening) {
        this.aScreenings.remove(pScreening);
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
