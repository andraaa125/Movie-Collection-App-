package org.example.moviecollection.dal;

import javafx.collections.ObservableList;
import org.example.moviecollection.be.CatMovie;
import org.example.moviecollection.be.Category;
import org.example.moviecollection.be.Movie;

import java.io.IOException;
import java.util.List;

public interface ICatMovieDAO {
    List<CatMovie> getMoviesInCategory(int categoryId) throws IOException;
    List<CatMovie> getCategoriesPerMovie(int movieId) throws IOException;

    void addMovieToCategory(Category category, Movie movie) throws IOException;
    void removeMovieFromCategory(Category category, Movie movie) throws IOException;
}
