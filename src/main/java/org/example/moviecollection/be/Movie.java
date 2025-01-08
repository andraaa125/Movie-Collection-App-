package org.example.moviecollection.be;

import java.util.Date;

public class Movie {
    private int id;
    private String name;
    private double imdbRating;
    private double personalRating;
    private String filePath;
    private Date lastView;


    //This constructor is for the database methods
    public Movie(int id, String name, double imdbRating, double personalRating, String filePath, Date lastView) {
        this.id = id;
        this.name = name;
        this.imdbRating = imdbRating;
        this.personalRating = personalRating;
        this.filePath = filePath;
        this.lastView = lastView;
    }

    //This constructor is for getting a movie from a folder
    public Movie (String name, String filePath) {
        this.name = name;
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public double getPersonalRating() {
        return personalRating;
    }

    public String getFilePath() {
        return filePath;
    }

    public Date getLastView() {
        return lastView;
    }
    
}
