package org.example.moviecollection.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.moviecollection.be.Movie;
import org.example.moviecollection.gui.model.MovieModel;

import java.io.IOException;

public class AddEditMovieController {
    @FXML
    private ComboBox comboBox;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtFilePath;
    @FXML
    private Button btnCancelMovie;

    private MovieCollectionApplicationController movieCollectionApplicationController;
    private final MovieModel movieModel = new MovieModel();

    public void setParentController(MovieCollectionApplicationController movieCollectionApplicationController) {
        this.movieCollectionApplicationController = movieCollectionApplicationController;
    }

    public void onCancelMovieClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancelMovie.getScene().getWindow();
        stage.close();
    }

    public void onSaveMovieClick(ActionEvent actionEvent) {
        try{
            if(txtName.getText().isEmpty() || txtFilePath.getText().isEmpty()){
                movieCollectionApplicationController.showAlert("Validation Error", "All fields must be filled!");
                return;
            }
            String movieName = txtName.getText();
            String moviePath = txtFilePath.getText();
            String category = (String) comboBox.getValue();
            Movie newMovie = new Movie(movieName,moviePath,category);
            movieModel.addMovie(newMovie);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setMovieToEdit(Movie selectedMovie){

    }

    public void onChooseClick(ActionEvent actionEvent) {
    }

    public void onLoadMoreClick(ActionEvent actionEvent) {
    }
}
