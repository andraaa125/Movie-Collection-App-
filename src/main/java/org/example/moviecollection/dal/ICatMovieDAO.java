package org.example.moviecollection.dal;

import org.example.moviecollection.be.CatMovie;
import org.example.moviecollection.be.Category;
import org.example.moviecollection.be.Movie;
import org.example.moviecollection.exceptions.MovieCollectionAppExceptions;

import java.util.List;

public interface ICatMovieDAO {
    List<CatMovie> getMoviesInCategory(int categoryId) throws MovieCollectionAppExceptions;
    List<CatMovie> getCategoriesPerMovie(int movieId) throws MovieCollectionAppExceptions;

    void addMovieToCategory(int categoryId, int movieId) throws MovieCollectionAppExceptions;
    void removeMovieFromCategory(int categoryId, int movieId) throws MovieCollectionAppExceptions;
}
