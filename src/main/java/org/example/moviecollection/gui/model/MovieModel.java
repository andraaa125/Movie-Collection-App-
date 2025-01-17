package org.example.moviecollection.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.moviecollection.be.CatMovie;
import org.example.moviecollection.be.Category;
import org.example.moviecollection.be.Movie;
import org.example.moviecollection.bll.CatMovieManager;
import org.example.moviecollection.bll.CategoryManager;
import org.example.moviecollection.bll.MovieManager;
import org.example.moviecollection.exceptions.MovieCollectionAppExceptions;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieModel {
    private final CategoryManager categoryManager = new CategoryManager();
    private final MovieManager movieManager = new MovieManager();
    private final CatMovieManager catMovieManager = new CatMovieManager();
    private final ObservableList<Category> categories = FXCollections.observableArrayList();
    private final ObservableList<Movie> movies = FXCollections.observableArrayList();
    private final ObservableList<Movie> searchedMovies = FXCollections.observableArrayList();
    private final ObservableList<Movie> shouldDeleteMovies = FXCollections.observableArrayList();
    private final ObservableList<CatMovie> moviesInCategory = FXCollections.observableArrayList();
    private final ObservableList<CatMovie> categoriesPerMovie = FXCollections.observableArrayList();

    public ObservableList<Category> getAllCategories() {
        try {
            List<Category> categoryList = categoryManager.getAllCategory();
            categories.setAll(categoryList);
        } catch (MovieCollectionAppExceptions e) {
            System.out.println("Error loading playlists: " + e.getMessage());
        }
        return categories;
    }

    public Category getCategoryByName(String categoryName) {
        for (Category category : categories) {
            if (category.getName().equals(categoryName)) {
                return category;
            }
        }
        return null;
    }


    public void deleteCategory(String categoryName) throws MovieCollectionAppExceptions {
        categoryManager.deleteCategory(categoryName);
    }

    public void addCategory(Category newCategory) throws MovieCollectionAppExceptions {
        //categoryManager.addCategory(newCategory);
        try {
            categoryManager.addCategory(newCategory);
            categories.add(newCategory); // Add the new category to the observable list
            System.out.println("Category added to MovieModel: " + newCategory.getName());
        } catch (Exception e) {
            System.err.println("Error in MovieModel.addCategory: " + e.getMessage());
            throw new MovieCollectionAppExceptions("Failed to add category in MovieModel.", (SQLException) e);
        }
    }

    public void updateCategory(Category category) throws MovieCollectionAppExceptions {
        categoryManager.updateCategory(category);
    }

    public ObservableList<Movie> getAllMovies() {
        try {
            List<Movie> movieList = movieManager.getAllMovies();
            movies.setAll(movieList);
        } catch (MovieCollectionAppExceptions e) {
            System.out.println("Error loading movies: " + e.getMessage());
        }
        return movies;
    }

    public void addMovie(Movie newMovie) throws MovieCollectionAppExceptions {
        movieManager.addMovie(newMovie);
    }

    public void deleteMovie(int movieId) throws MovieCollectionAppExceptions {
        movieManager.deleteMovie(movieId);
    }

    public void updateMovie(Movie movieToEdit) throws MovieCollectionAppExceptions {
        movieManager.updateMovie(movieToEdit);
    }

    public ObservableList<String> displayCategoryName() throws MovieCollectionAppExceptions {
        Set<String> categorySet = new HashSet<>(); // Use a Set to automatically remove duplicates
        List<Category> categories = categoryManager.getAllCategory();
        for (Category category : categories) {
            String categoryName = category.getName();
            if (categoryName != null && !categoryName.isEmpty()) {
                categorySet.add(categoryName);
            }
        }
        // Convert the Set to an ObservableList
        return FXCollections.observableArrayList(categorySet);
    }

    // Get observableList of searched movies.
    public ObservableList<Movie> getSearchedMovies(String query) {
        try {
            List<Movie> searchResult = movieManager.searchMovie(query);
            searchedMovies.setAll(searchResult); // Update observable list with filtered songs
        } catch (MovieCollectionAppExceptions e) {
            System.out.println("Error searching movies: " + e.getMessage());
        }
        return searchedMovies;
    }

    // Get observableList of movies to delete.
    public ObservableList<Movie> getFilterMoviesToDelete() {
        try {
            List<Movie> filterResult = movieManager.moviesToDelete(); // Call the moviesToDelete method
            shouldDeleteMovies.setAll(filterResult); // Update observable list with filtered movies
        } catch (MovieCollectionAppExceptions e) {
            System.out.println("Error filtering movies to delete: " + e.getMessage());
        }
        return shouldDeleteMovies;
    }

    public boolean isCategoryExists(String categoryName) throws MovieCollectionAppExceptions {
        return categoryManager.isCategoryExists(categoryName); // Assuming `categoryManager` is an instance of `CategoryManager`
    }

    public void updateLastView(int movieId) throws MovieCollectionAppExceptions {
        movieManager.updateLastView(movieId);
    }

    // Get ObservableList of movies in a category
    public ObservableList<CatMovie> getMoviesInCategory(int categoryId) {
        try {
            List<CatMovie> allMoviesInCategory = catMovieManager.getMoviesInCategory(categoryId);
            moviesInCategory.setAll(allMoviesInCategory);
        } catch (MovieCollectionAppExceptions e) {
            System.out.println("Error loading movies in category: " + e.getMessage());
        }
        return moviesInCategory;
    }

    // Method to get all categories related to a specific movie
    public ObservableList<CatMovie> getCategoriesPerMovie(int movieId) {
        try {
            List<CatMovie> allCategoriesPerMovie = catMovieManager.getCategoriesPerMovie(movieId);
            categoriesPerMovie.setAll(allCategoriesPerMovie); // Populate the ObservableList with categories
        } catch (MovieCollectionAppExceptions e) {
            System.out.println("Error loading categories for movie: " + e.getMessage());
            e.printStackTrace();
        }
        return categoriesPerMovie;
    }


    public void removeMovieFromCategory(Category category, Movie movie) throws MovieCollectionAppExceptions {
        catMovieManager.removeMovieFromCategory(category, movie);

    }
    public void addMovieToCategory(Category category, Movie movie) throws MovieCollectionAppExceptions {
        catMovieManager.addMovieToCategory(category, movie);

    }


}
