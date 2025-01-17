package org.example.moviecollection.dal;

import org.example.moviecollection.be.CatMovie;

import java.io.IOException;
import java.util.List;

public interface ICatMovieDAO {
    List<CatMovie> getMoviesInCategory(int categoryId) throws IOException;
    List<CatMovie> getCategoriesPerMovie(int movieId) throws IOException;

    void addMovieToCategory(int categoryId, int movieId) throws IOException;
    void removeMovieFromCategory(int categoryId, int movieId) throws IOException;
}
