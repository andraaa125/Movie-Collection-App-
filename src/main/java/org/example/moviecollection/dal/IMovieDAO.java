package org.example.moviecollection.dal;

import org.example.moviecollection.be.Movie;
import org.example.moviecollection.exceptions.MovieCollectionAppExceptions;
import java.sql.SQLException;
import java.util.List;

public interface IMovieDAO {

    List<Movie> getAllMovies() throws MovieCollectionAppExceptions;
    Movie add(Movie movie) throws MovieCollectionAppExceptions;
    void update(Movie movie) throws MovieCollectionAppExceptions;
    void update (Movie movie, Movie newMovie) throws MovieCollectionAppExceptions;
}


