package com.example.theaterproject.Models;

public class Movie {
    private String aGenre;
    private String aTitle;
    private String aDirector;
    private String aYear;
    private String aDescription;

    public Movie(String aGenre, String aTitle, String aDirector, String aYear, String aDescription) {
        this.aGenre = aGenre;
        this.aTitle = aTitle;
        this.aDirector = aDirector;
        this.aYear = aYear;
        this.aDescription = aDescription;
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
