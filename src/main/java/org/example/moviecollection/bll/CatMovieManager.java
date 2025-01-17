package org.example.moviecollection.bll;

import org.example.moviecollection.be.CatMovie;
import org.example.moviecollection.dal.ICatMovieDAO;
import org.example.moviecollection.dal.db.CatMovieDAODB;

import java.io.IOException;
import java.util.List;

public class CatMovieManager {
    private final ICatMovieDAO catMovieDAO = new CatMovieDAODB();

    // Get all movies in a specific category
    public List<CatMovie> getMoviesInCategory(int categoryId) throws IOException {
        return catMovieDAO.getMoviesInCategory(categoryId);
    }

    public List<CatMovie> getCategoriesPerMovie(int movieId) throws IOException {
        return catMovieDAO.getCategoriesPerMovie(movieId);
    }

    // Add a movie to a category
    public void addMovieToCategory(int categoryId, int movieId) throws IOException {
        catMovieDAO.addMovieToCategory(categoryId, movieId);
    }

    // Remove a movie from a category
    public void removeMovieFromCategory(int categoryId, int movieId) throws IOException {
        catMovieDAO.removeMovieFromCategory(categoryId, movieId);
    }

}
