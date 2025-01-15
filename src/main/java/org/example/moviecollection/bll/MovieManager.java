package org.example.moviecollection.bll;

import org.example.moviecollection.be.Movie;
import org.example.moviecollection.dal.IMovieDAO;
import org.example.moviecollection.dal.db.MovieDAODB;

import java.io.IOException;
import java.util.List;

public class MovieManager {

    private final IMovieDAO movieDAO = new MovieDAODB();

    public List<Movie> getAllMovies() throws IOException{
        return movieDAO.getAllMovies();
    }

}
