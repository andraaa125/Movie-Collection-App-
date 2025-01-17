package org.example.moviecollection.be;

public class CatMovie {
    private final int id;
    private int categoryId;
    private int movieId;
    private Movie movie;
    private Category category;
    private String movieName;
    
    public CatMovie(int id, int categoryId, int movieId, String movieName, Category category) {
        this.id = id;
        this.categoryId = categoryId;
        this.movieId = movieId;
        this.movieName = movieName;
        this.category = category;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return movieName;
    }
}
