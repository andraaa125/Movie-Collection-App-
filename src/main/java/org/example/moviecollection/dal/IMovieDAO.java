package org.example.moviecollection.dal;

import org.example.moviecollection.be.Movie;
import org.example.moviecollection.exceptions.MovieCollectionAppExceptions;

import java.util.List;

public interface IMovieDAO {
    List<Movie> getAllMovies() throws MovieCollectionAppExceptions;
    void addMovie(Movie movie) throws MovieCollectionAppExceptions;

    void updateMovie(Movie movie) throws MovieCollectionAppExceptions;

    void updateLastView(int movieId);

    void deleteMovie(int movieId) throws MovieCollectionAppExceptions;
}


