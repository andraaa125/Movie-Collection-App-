package org.example.moviecollection.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.moviecollection.be.CatMovie;
import org.example.moviecollection.be.Category;
import org.example.moviecollection.be.Movie;
import org.example.moviecollection.exceptions.MovieCollectionAppExceptions;
import org.example.moviecollection.gui.model.MovieModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AddEditMovieController implements Initializable{

    @FXML
    private Slider IMDBGradeSlider;
    @FXML
    private Label IMDBScore;
    @FXML
    private Slider PersonalGradeSlider;
    @FXML
    private Label PersonalScore;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtFilePath;
    @FXML
    private Button btnCancelMovie;
    @FXML
    private Button btnChoose;

    private MovieCollectionApplicationController movieCollectionApplicationController;
    private final MovieModel movieModel = new MovieModel();
    private Movie movieToEdit;
    private ObservableList<String> selectedCategories = FXCollections.observableArrayList();

    public void setParentController(MovieCollectionApplicationController movieCollectionApplicationController) {
        this.movieCollectionApplicationController = movieCollectionApplicationController;
    }

    public void initialize(URL location, ResourceBundle resources) {
        // Bind labels directly to the slider values with formatting
        IMDBScore.textProperty().bind(IMDBGradeSlider.valueProperty().asString("%.1f"));
        PersonalScore.textProperty().bind(PersonalGradeSlider.valueProperty().asString("%.1f"));

    }

    public void setMovieToEdit(Movie movieToEdit) throws MovieCollectionAppExceptions {
        this.movieToEdit = movieToEdit;

        txtName.setText(movieToEdit.getName());
        txtFilePath.setText(movieToEdit.getFilePath());
        IMDBGradeSlider.setValue(movieToEdit.getImdbRating());
        PersonalGradeSlider.setValue(movieToEdit.getPersonalRating());
    }

    public void onCancelMovieClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancelMovie.getScene().getWindow();
        stage.close();
    }

    public void onSaveMovieClick(ActionEvent actionEvent) throws IOException {
       try {
            if (txtName.getText().isEmpty() || txtFilePath.getText().isEmpty()) {
                movieCollectionApplicationController.showAlert("Validation Error", "All fields must be filled!");
                return;
            }
            String newMovieName = txtName.getText();
            String newMoviePath = txtFilePath.getText();
            Double newImdbRating = IMDBGradeSlider.getValue();
            Double newPersonalRating = PersonalGradeSlider.getValue();

            if (movieToEdit != null) {
                movieToEdit.setName(newMovieName);
                movieToEdit.setFilePath(newMoviePath);
                movieToEdit.setImdbRating(newImdbRating);
                movieToEdit.setPersonalRating(newPersonalRating);
                //movieToEdit.setCategory(category);

                movieModel.updateMovie(movieToEdit);
            }else {
                Movie newMovie = new Movie(newMovieName, newMoviePath, newImdbRating, newPersonalRating);
                movieModel.addMovie(newMovie);;

            }
            movieCollectionApplicationController.loadMoviesFromDatabase();
           Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
           stage.close();

       }catch (Exception e) {
           e.printStackTrace();
           movieCollectionApplicationController.showAlert("Error", "An error occurred while saving the movie: " + e.getMessage());
       }
    }

    public void onChooseClick(ActionEvent actionEvent) {// Open a file chooser dialog to select a song file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Please select a Movie File"); // Set the title of the file chooser window
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.mpeg4") );

        // Show the file chooser and store the selected file
        File selectedFile = fileChooser.showOpenDialog(btnChoose.getScene().getWindow());
        if (selectedFile != null) {
            // If a file is selected, set its path in the text field
            txtFilePath.setText(selectedFile.getAbsolutePath());
        }
    }
}
