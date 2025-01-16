package org.example.moviecollection.bll;

import org.example.moviecollection.be.Movie;
import org.example.moviecollection.bll.util.MovieSearcher;
import org.example.moviecollection.dal.IMovieDAO;
import org.example.moviecollection.dal.db.MovieDAODB;

import java.io.IOException;
import java.util.List;

public class MovieManager {

    private final IMovieDAO movieDAO = new MovieDAODB();
    private final MovieSearcher movieSearcher = new MovieSearcher();

    public List<Movie> getAllMovies() throws IOException{
        return movieDAO.getAllMovies();
    }

    public List<Movie> searchMovie(String query) throws IOException{
        List<Movie> allMovies = getAllMovies();
        List<Movie> searchResult = movieSearcher.search(allMovies, query);
        return searchResult;
    }

    public void addMovie(Movie movie) throws IOException{
        movieDAO.addMovie(movie);
    }

    public void deleteMovie(String name) throws IOException{
        movieDAO.deleteMovie(name);
    }

    public void updateMovie(Movie movie) throws IOException{
        movieDAO.updateMovie(movie);
    }
}
