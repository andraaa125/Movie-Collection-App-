package org.example.moviecollection.dal;

import org.example.moviecollection.be.Movie;

import java.io.IOException;
import java.util.List;

public interface IMovieDAO {
    List<Movie> getAllMovies() throws IOException;
    void addMovie(Movie movie) throws IOException;

    void updateMovie(Movie movie) throws IOException;

    void updateLastView(int movieId);

    void deleteMovie(int movieId) throws IOException;
}


