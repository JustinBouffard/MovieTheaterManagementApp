package com.example.theaterproject.Models;

public class Movie {
    private String aGenre;
    private String aTitle;
    private String aDirector;
    private String aYear;
    private String aDescription;

    public Movie(String pGenre, String pTitle, String pDirector, String pYear, String pDescription) {
        this.aGenre = pGenre;
        this.aTitle = pTitle;
        this.aDirector = pDirector;
        this.aYear = pYear;
        this.aDescription = pDescription;
    }

    public String getTitle() {
        return aTitle;
    }

    public String getGenre() {
        return aGenre;
    }

    public String getDescription() {
        return aDescription;
    }

    @Override
    public String toString() {
        return aTitle + " " + aGenre + " " + aDirector + " " + aYear + " " + aDescription;
    }
}
