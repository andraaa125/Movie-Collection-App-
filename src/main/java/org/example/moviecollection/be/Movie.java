package org.example.moviecollection.be;

import javafx.collections.ObservableList;

import java.time.LocalDate;


public class Movie {
    private int id;
    private String name;
    private double imdbRating;
    private double personalRating;
    private String filePath;
    private LocalDate lastView;
    private ObservableList<String> categories;

    public Movie(int id, String name, double imdbRating, double personalRating, String filePath, LocalDate lastView) {
        this.id = id;
        this.name = name;
        this.imdbRating = imdbRating;
        this.personalRating = personalRating;
        this.filePath = filePath;
        this.lastView = lastView;
    }

    public Movie(String movieName, Double imdbRating, Double personalRating, String moviePath, ObservableList<String> selectedCategories) {
        this.name = movieName;
        this.imdbRating = imdbRating;
        this.personalRating = personalRating;
        this.filePath = moviePath;
        this.categories = selectedCategories;
    }

    public Movie(int id, String name, Double imdbRating, Double personalRating, String filePath, java.sql.Date lastViewDate) {
        this.name = name;
        this.imdbRating = imdbRating;
        this.personalRating = personalRating;
        this.filePath = filePath;
    }

    public LocalDate getLastView() {
        return lastView;
    }

    public void setLastViewDate(LocalDate lastView) {
        this.lastView = lastView;
    }

    public ObservableList<String> getCategories() {
        return categories;
    }

    public void setCategories(ObservableList<String> categories) {
        this.categories = categories;
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
        return id + "," + name + "," + imdbRating + "," + personalRating + "," + lastView;
    }
}
