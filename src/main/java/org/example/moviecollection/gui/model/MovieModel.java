package org.example.moviecollection.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.moviecollection.be.Category;
import org.example.moviecollection.be.Movie;
import org.example.moviecollection.bll.CategoryManager;
import org.example.moviecollection.bll.MovieManager;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieModel {
    private final CategoryManager categoryManager = new CategoryManager();
    private final MovieManager movieManager = new MovieManager();
    private final ObservableList<Category> categories = FXCollections.observableArrayList();
    private final ObservableList<Movie> movies = FXCollections.observableArrayList();

    public ObservableList<Category> getAllCategories() {
        try {
            List<Category> categoryList = categoryManager.getAllCategory();
            categories.setAll(categoryList);
        } catch (IOException e) {
            System.out.println("Error loading playlists: " + e.getMessage());
        }
        return categories;
    }

    public void deleteCategory(String categoryName) throws IOException {
        categoryManager.deleteCategory(categoryName);
    }

    public void addCategory(Category newCategory) throws IOException {
        //categoryManager.addCategory(newCategory);
        try {
            categoryManager.addCategory(newCategory);
            categories.add(newCategory); // Add the new category to the observable list
            System.out.println("Category added to MovieModel: " + newCategory.getName());
        } catch (Exception e) {
            System.err.println("Error in MovieModel.addCategory: " + e.getMessage());
            throw new IOException("Failed to add category in MovieModel.", e);
        }
    }

    public void updateCategory(Category category) throws IOException {
        categoryManager.updateCategory(category);
    }

    public ObservableList<Movie> getAllMovies() {
        try {
            List<Movie> movieList = movieManager.getAllMovies();
            movies.setAll(movieList);
        } catch (IOException e) {
            System.out.println("Error loading movies: " + e.getMessage());
        }
        return movies;
    }

    public void addMovie(Movie newMovie) throws IOException {
        movieManager.addMovie(newMovie);
    }

    public void deleteMovie(String name) throws IOException {
        movieManager.deleteMovie(name);
    }
    public void updateMovie(Movie movie) throws IOException {
        movieManager.updateMovie(movie);
    }

    public ObservableList<String> displayCategoryName() throws IOException {
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


}
