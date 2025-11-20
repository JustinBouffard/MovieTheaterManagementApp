package com.example.theaterproject.Models;

public class Movie {
    private String aGenre;
    private String aTitle;
    private String aDirector;
    private String aYear;
    private String aDescription;
    private String aRuntime;

    public Movie(String pGenre, String pTitle, String pDirector, String pYear, String pDescription, String pRuntime) {
        this.aGenre = pGenre;
        this.aTitle = pTitle;
        this.aDirector = pDirector;
        this.aYear = pYear;
        this.aDescription = pDescription;
        this.aRuntime = pRuntime;
    }

    public Movie(Movie pMovie) {
        this.aGenre = pMovie.aGenre;
        this.aTitle = pMovie.aTitle;
        this.aDirector = pMovie.aDirector;
        this.aYear = pMovie.aYear;
        this.aDescription = pMovie.aDescription;
        this.aRuntime = pMovie.aRuntime;
    }

    public String getTitle() {
        return this.aTitle;
    }

    @Override
    public String toString() {
        return this.aTitle + " " + this.aGenre + " " + this.aDirector + " " + this.aYear + " " + this.aDescription;
    }
}
