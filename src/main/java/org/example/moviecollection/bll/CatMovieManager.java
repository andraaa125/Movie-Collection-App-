package org.example.moviecollection.bll;

import org.example.moviecollection.be.CatMovie;
import org.example.moviecollection.be.Category;
import org.example.moviecollection.be.Movie;
import org.example.moviecollection.dal.ICatMovieDAO;
import org.example.moviecollection.dal.db.CatMovieDAODB;
import org.example.moviecollection.exceptions.MovieCollectionAppExceptions;

import java.util.List;

public class CatMovieManager {
    private final ICatMovieDAO catMovieDAO = new CatMovieDAODB();

    // Get all movies in a specific category
    public List<CatMovie> getMoviesInCategory(int categoryId) throws MovieCollectionAppExceptions {
        return catMovieDAO.getMoviesInCategory(categoryId);
    }

    public List<CatMovie> getCategoriesPerMovie(int movieId) throws MovieCollectionAppExceptions {
        return catMovieDAO.getCategoriesPerMovie(movieId);
    }

    public void addMovieToCategory(int categoryId, int movieId) throws MovieCollectionAppExceptions {
        catMovieDAO.addMovieToCategory(categoryId, movieId);
    }

    public void removeMovieFromCategory(int categoryId, int movieId) throws MovieCollectionAppExceptions {
        catMovieDAO.removeMovieFromCategory(categoryId, movieId);
    }

}
