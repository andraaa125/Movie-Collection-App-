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
    public Movie (String name, String filePath, String category) {
        this.name = name;
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public double getPersonalRating() {
        return personalRating;
    }

    public void setPersonalRating(double personalRating) {
        this.personalRating = personalRating;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getLastView() {
        return lastView;
    }

    public void setLastView(Date lastView) {
        this.lastView = lastView;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + imdbRating + "," + personalRating + "," + lastView;
    }
}
