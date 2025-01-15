package org.example.moviecollection.gui.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.moviecollection.MovieCollectionApplication;
import org.example.moviecollection.be.Category;
import org.example.moviecollection.be.Movie;
import org.example.moviecollection.bll.FilterService;
import org.example.moviecollection.gui.model.MovieModel;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class MovieCollectionApplicationController implements Initializable {
    @FXML
    private TableColumn IdColumn;
    @FXML
    private TableView lstMovie;
    @FXML
    private TableColumn NameColumn;
    @FXML
    private TableColumn IMDbRatingColumn;
    @FXML
    private TableColumn MyRatingColumn;
    @FXML
    private TableColumn LastViewColumn;
    @FXML
    private ListView listViewCategories;

    private final MovieModel movieModel = new MovieModel();
    private final FilterService filterService = new FilterService();
    private boolean isFilterActive = false; // Track the current state of the button

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCategoriesFromDatabase();
        loadMoviesFromDatabase();
    }

    public void loadCategoriesFromDatabase() {
        listViewCategories.getItems().clear();
        listViewCategories.setItems(movieModel.getAllCategories());
    }

    public void loadMoviesFromDatabase() {
        lstMovie.getItems().clear();
        lstMovie.setItems(movieModel.getAllMovies());
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        IMDbRatingColumn.setCellValueFactory(new PropertyValueFactory<>("imdbRating"));
        MyRatingColumn.setCellValueFactory(new PropertyValueFactory<>("personalRating"));
        LastViewColumn.setCellValueFactory(new PropertyValueFactory<>("lastView"));
    }

    public void onAddCategoryClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieCollectionApplication.class.getResource("CategoryEditor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AddEditCategoryController categoryController = fxmlLoader.getController();
        categoryController.setParentController(this);
        Stage stage = new Stage();
        stage.setTitle("Add Category");
        stage.setScene(scene);
        stage.show();
    }

    public void onEditCategoryClick(ActionEvent actionEvent) throws IOException {
        Category selectedCategory = (Category) listViewCategories.getSelectionModel().getSelectedItem();
        if (selectedCategory == null) {
            showAlert("No Category Selected","Please select a category to edit");
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(MovieCollectionApplication.class.getResource("CategoryEditor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AddEditCategoryController categoryController = fxmlLoader.getController();
        categoryController.setParentController(this);
        categoryController.setCategoryToEdit(selectedCategory);
        Stage stage = new Stage();
        stage.setTitle("Edit Category");
        stage.setScene(scene);
        stage.show();
    }

    public void onDeleteCategoryClick(ActionEvent actionEvent) {
        Category selectedCategory = (Category) listViewCategories.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Delete Category");
            confirmationAlert.setHeaderText("Are you sure you want to delete this category?");
            confirmationAlert.setContentText("Category: " + selectedCategory.getName());

            var result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                try {
                    movieModel.deleteCategory(selectedCategory.getName());
                    listViewCategories.getItems().remove(selectedCategory);
                    listViewCategories.refresh();
                    showInfo("Success", "The category you chose was successfully deleted.");
                } catch (Exception e) {
                    showAlert("Error", "An error occurred while deleting the category: " + e.getMessage());
                }
            }
        } else {
            // Show a warning if no song is selected
            showAlert("No Category Was Selected", "Please select a category to delete.");
        }
    }

    public void onSearchBtnClick(ActionEvent actionEvent) {

    }

    public void onPlayBtnClick(ActionEvent actionEvent) {

    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Інформаційне повідомлення
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void onAddMovieClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieCollectionApplication.class.getResource("MovieEditor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AddEditMovieController movieController = fxmlLoader.getController();
        movieController.setParentController(this);
        Stage stage = new Stage();
        stage.setTitle("Add Movie");
        stage.setScene(scene);
        stage.show();
    }

    public void onEditMovieClick(ActionEvent actionEvent) throws IOException {
        Movie selectedMovie = (Movie) lstMovie.getSelectionModel().getSelectedItem();
        if (selectedMovie == null) {
            showAlert("No Movie Selected","Please select a movie to edit");
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(MovieCollectionApplication.class.getResource("MovieEditor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AddEditMovieController movieController = fxmlLoader.getController();
        movieController.setParentController(this);
        Stage stage = new Stage();
        stage.setTitle("Edit Movie");
        stage.setScene(scene);
        stage.show();
    }

    public void onDeleteMovieClick(ActionEvent actionEvent) {
        Movie selectedMovie = (Movie) lstMovie.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Delete Movie");
            confirmationAlert.setHeaderText("Are you sure you want to delete this movie?");
            confirmationAlert.setContentText("Movie: " + selectedMovie.getName());

            // Wait for the user's response
            var result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Delete the song from the database via the model
                    movieModel.deleteMovie(selectedMovie.getName());
                    lstMovie.getItems().remove(selectedMovie);
                    lstMovie.refresh();
                    // Inform the user about the successful deletion
                    showInfo("Success", "The song was successfully deleted.");
                } catch (Exception e) {
                    showAlert("Error", "An error occurred while deleting the song: " + e.getMessage());
                }
            }
        } else {
            showAlert("No Movie Selected","Please select a movie to delete");
        }
    }


}