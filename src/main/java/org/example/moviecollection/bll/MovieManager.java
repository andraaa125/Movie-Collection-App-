package org.example.moviecollection.bll;

import org.example.moviecollection.be.CatMovie;
import org.example.moviecollection.be.Movie;
import org.example.moviecollection.bll.util.MovieSearcher;
import org.example.moviecollection.bll.util.MoviesToDelete;
import org.example.moviecollection.dal.IMovieDAO;
import org.example.moviecollection.dal.db.CatMovieDAODB;
import org.example.moviecollection.dal.db.MovieDAODB;
import org.example.moviecollection.exceptions.MovieCollectionAppExceptions;

import java.util.List;

public class MovieManager {

    private final IMovieDAO movieDAO = new MovieDAODB();
    private final MovieSearcher movieSearcher = new MovieSearcher();
    private final MoviesToDelete moviesToDelete = new MoviesToDelete();

    public List<Movie> getAllMovies() throws MovieCollectionAppExceptions {
        return movieDAO.getAllMovies();
    }

    public List<Movie> searchMovie(String query) throws MovieCollectionAppExceptions{
        List<Movie> allMovies = getAllMovies();
        return movieSearcher.search(allMovies, query);
    }


    public List<Movie> moviesToDelete() throws MovieCollectionAppExceptions{
        List<Movie> allMovies = getAllMovies();
        return moviesToDelete.filterMoviesToDelete(allMovies);
    }

    public void addMovie(Movie movie) throws MovieCollectionAppExceptions{
        movieDAO.addMovie(movie);
    }

    public void deleteMovie(int movieId) throws MovieCollectionAppExceptions{
        movieDAO.deleteMovie(movieId);
    }

    public void updateMovie(Movie movie) throws MovieCollectionAppExceptions{
        movieDAO.updateMovie(movie);
    }

    public void updateLastView(int movieId) throws MovieCollectionAppExceptions{
        movieDAO.updateLastView(movieId);
    }

}
