package org.example.moviecollection.be;

public class CatMovie {
    private final int id;
    private int categoryID;
    private int movieID;
    private Movie movie;
    private Category category;
    
    public CatMovie(int id, int categoryID, int movieID) {
        this.id = id;
        this.categoryID = categoryID;
        this.movieID = movieID;
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

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CatMovie{" +
                "id=" + id +
                ", categoryId=" + categoryID +
                ", movieId=" + movieID +
                '}';
    }
}
