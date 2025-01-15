package org.example.moviecollection.dal;

import org.example.moviecollection.be.Movie;

import java.io.IOException;
import java.util.List;

public interface IMovieDAO {
    List<Movie> getAllMovies() throws IOException;


    /*Movie add(Movie movie) throws MovieCollectionAppExceptions;
    void update(Movie movie) throws MovieCollectionAppExceptions;
    void update (Movie movie, Movie newMovie) throws MovieCollectionAppExceptions;*/

}


