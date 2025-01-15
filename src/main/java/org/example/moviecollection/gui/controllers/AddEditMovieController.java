package org.example.moviecollection.gui.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.moviecollection.be.Movie;
import org.example.moviecollection.gui.model.MovieModel;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class AddEditMovieController implements Initializable{
    public ListView lstCategory;
    public Slider IMDBGradeSlider;
    public Label IMDBScore;
    public Slider PersonalGradeSlider;
    public Label PersonalScore;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtFilePath;
    @FXML
    private Button btnCancelMovie;

    private MovieCollectionApplicationController movieCollectionApplicationController;
    private final MovieModel movieModel = new MovieModel();
    private Movie movieToEdit;

    public void setParentController(MovieCollectionApplicationController movieCollectionApplicationController) {
        this.movieCollectionApplicationController = movieCollectionApplicationController;
    }

    public void initialize(URL location, ResourceBundle resources) {
        displayCategoryName();
    }

    public void displayCategoryName() {
        try {
            // Fetch categories from the model
            ObservableList<String> categories = movieModel.displayCategoryName(); // Fetch updated list
            comboBox.setItems(categories); // Update the ComboBox items

            if (!categories.isEmpty()) {
                comboBox.setValue(categories.get(0)); // Set first category as default
            }
        } catch (IOException e) {
            System.out.println("Error loading categories: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void onCancelMovieClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancelMovie.getScene().getWindow();
        stage.close();
    }

    public void onSaveMovieClick(ActionEvent actionEvent) {
        /*try{
            if(txtName.getText().isEmpty() || txtFilePath.getText().isEmpty()){
                movieCollectionApplicationController.showAlert("Validation Error", "All fields must be filled!");
                return;
            }

            String movieName = txtName.getText();
            Double imdbRating = Double.parseDouble(txtFilePath.getText());
            Double personalRating = Double.parseDouble(comboBox.getValue());
            String moviePath = txtFilePath.getText();


            Movie newMovie = new Movie(movieName, imdbRating, personalRating, moviePath);
            movieModel.addMovie(newMovie);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

    }

    public void setMovieToEdit(Movie selectedMovie){

    }

    public void onChooseClick(ActionEvent actionEvent) {
    }

    public void onLoadMoreClick(ActionEvent actionEvent) {
    }

    public void onRemoveClick(ActionEvent actionEvent) {
    }
}
