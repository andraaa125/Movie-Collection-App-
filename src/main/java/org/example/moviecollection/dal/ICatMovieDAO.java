package org.example.moviecollection.dal;

import javafx.collections.ObservableList;
import org.example.moviecollection.be.CatMovie;
import org.example.moviecollection.be.Category;
import org.example.moviecollection.be.Movie;
import org.example.moviecollection.exceptions.MovieCollectionAppExceptions;

import java.io.IOException;
import java.util.List;

public interface ICatMovieDAO {
    List<CatMovie> getMoviesInCategory(int categoryId) throws MovieCollectionAppExceptions;
    List<CatMovie> getCategoriesPerMovie(int movieId) throws MovieCollectionAppExceptions;

    void addMovieToCategory(Category category, Movie movie) throws MovieCollectionAppExceptions;
    void removeMovieFromCategory(Category category, Movie movie) throws MovieCollectionAppExceptions;
}
