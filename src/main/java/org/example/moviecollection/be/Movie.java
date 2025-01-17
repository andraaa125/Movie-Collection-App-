package org.example.moviecollection.be;

import java.time.LocalDate;

public class Movie {
    private int id;
    private String name;
    private double imdbRating;
    private double personalRating;
    private String filePath;
    private LocalDate lastView;
    private String category;

    public Movie(int id, String name, double imdbRating, double personalRating, String filePath, LocalDate lastView) {
        this.id = id;
        this.name = name;
        this.imdbRating = imdbRating;
        this.personalRating = personalRating;
        this.filePath = filePath;
        this.lastView = lastView;
        this.category = category;
    }
    public Movie(String name, String filePath, Double imdbRating, Double personalRating) {
        this.name = name;
        this.filePath = filePath; // Correct
        this.imdbRating = imdbRating;
        this.personalRating = personalRating;
    }

    public LocalDate getLastView() {
        return lastView;
    }

    public void setLastViewDate(LocalDate lastView) {
        this.lastView = lastView;
    }

    public void setLastView(LocalDate lastView) {
        this.lastView = lastView;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    @Override
    public String toString() {
        return name + " (" + category + ")";
    }

}
