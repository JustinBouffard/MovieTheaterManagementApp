package com.example.theaterproject.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a showroom in a theater, containing a name and seating capacity.
 * This class provides basic data storage and accessor methods for showroom
 * attributes.
 */
public class Showroom {
    /**
     * The name of the showroom.
     */
    private String aName;

    /**
     * The seating capacity of the showroom.
     */
    private int aCapacity;
    /**
     * The list of screenings scheduled in this showroom.
     */
    private ObservableList<Screening> aScreenings;

    /**
     * Constructs a new {@code Showroom} with the specified name and capacity.
     *
     * @param pName     the name of the showroom
     * @param pCapacity the seating capacity of the showroom
     */
    /**
     * Constructs a new {@code Showroom} with the specified name, capacity, and screenings.
     *
     * @param pName        the name of the showroom; must not be blank
     * @param pCapacity    the seating capacity; must be greater than zero
     * @param pScreenings  the initial list of screenings for this showroom; must not be {@code null}
     */
    public Showroom(String pName, int pCapacity, ObservableList<Screening> pScreenings) {
        if (pName == null || pName.isBlank()) {
            throw new IllegalArgumentException("Showroom name cannot be empty");
        }
        if (pCapacity <= 0) {
            throw new IllegalArgumentException("Showroom capacity cannot be 0 or below.");
        }
        this.aName = pName;
        this.aCapacity = pCapacity;
        this.aScreenings = pScreenings;
    }

    /**
     * Default constructor creating an empty showroom with zero capacity and no screenings.
     */
    public Showroom(){
        this.aName = "";
        this.aCapacity = 0;
        this.aScreenings = FXCollections.observableArrayList();
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

    /**
     * Retrieves the list of screenings for this showroom.
     *
     * @return an ObservableList of screenings in this showroom
     */
    public ObservableList<Screening> getShowroomScreenings() {
        return this.aScreenings;
    }

    /**
     * Sets the list of screenings for this showroom.
     *
     * @param pScreenings the new list of screenings
     */
    public void setShowroomScreenings(ObservableList<Screening> pScreenings) {
        this.aScreenings = pScreenings;
    }

    /**
     * Adds a screening to this showroom's list of screenings.
     *
     * @param pScreening the screening to add
     */
    public void addScreening(Screening pScreening) {
        this.aScreenings.add(pScreening);
    }

    /**
     * Removes a screening from this showroom's list of screenings.
     *
     * @param pScreening the screening to remove
     */
    public void removeScreening(Screening pScreening) {
        this.aScreenings.remove(pScreening);
    }

    /**
     * Removes all screenings currently scheduled in this showroom.
     */
    public void resetScreenings() {
        this.aScreenings.clear();
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
