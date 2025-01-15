package org.example.moviecollection.gui.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.moviecollection.be.Category;
import org.example.moviecollection.be.Movie;
import org.example.moviecollection.gui.model.MovieModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddEditMovieController implements Initializable{
    @FXML
    private ListView lstCategory;
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

    public void setParentController(MovieCollectionApplicationController movieCollectionApplicationController) {
        this.movieCollectionApplicationController = movieCollectionApplicationController;
    }

    public void initialize(URL location, ResourceBundle resources) {
        displayCategoryName();
        // Bind labels directly to the slider values with formatting
        IMDBScore.textProperty().bind(IMDBGradeSlider.valueProperty().asString("%.1f"));
        PersonalScore.textProperty().bind(PersonalGradeSlider.valueProperty().asString("%.1f"));
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
        try{
            if(txtName.getText().isEmpty() || txtFilePath.getText().isEmpty() || lstCategory.getItems().isEmpty()){
                movieCollectionApplicationController.showAlert("Validation Error", "All fields must be filled!");
                return;
            }
            String movieName = txtName.getText();
            String moviePath = txtFilePath.getText();
            Double imdbRating = IMDBGradeSlider.getValue();
            Double personalRating = PersonalGradeSlider.getValue();
            ObservableList<String> selectedCategories = lstCategory.getItems();// Get the selected categories from the ListView

            if(movieToEdit != null){
                movieToEdit.setName(movieName);
                movieToEdit.setFilePath(moviePath);
                movieToEdit.setImdbRating(imdbRating);
                movieToEdit.setPersonalRating(personalRating);
                movieModel.updateMovie(movieToEdit);
            }else{
                Movie newMovie = new Movie(movieName, imdbRating, personalRating, moviePath, selectedCategories);
                movieModel.addMovie(newMovie);
            }
            movieCollectionApplicationController.loadMoviesFromDatabase();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }
        catch (Exception e) {
            movieCollectionApplicationController.showAlert("Error", "An error occurred while saving the movie: " + e.getMessage());
        }
    }

    public void setMovieToEdit(Movie selectedMovie){

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

    public void onLoadMoreClick(ActionEvent actionEvent) {
        // Show an input dialog for the new category
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Category");
        dialog.setHeaderText("Add a New Category");
        dialog.setContentText("Enter category name:");

        // Get the user input
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(category -> {
            // Check if the category already exists
            if (comboBox.getItems().contains(category)) {
                movieCollectionApplicationController.showAlert("Fail","Category already exists!");
            } else {
                // Add new category to the ComboBox
                comboBox.getItems().add(category);
                movieCollectionApplicationController.showInfo("Success","Added category: " + category);
            }
        });
    }

    public void onAddCategoryClick(ActionEvent actionEvent) {
        String selectedCategory = comboBox.getSelectionModel().getSelectedItem();// Get the selected category from the ComboBox
        if (selectedCategory != null) {
            // Get the items of the ListView
            ObservableList<String> currentCategories = lstCategory.getItems();
            // Check if the category already exists in the ListView
            if (!currentCategories.contains(selectedCategory)) {
                // Add the selected category to the ListView
                currentCategories.add(selectedCategory);
            } else {
                // Show an alert if the category already exists
                movieCollectionApplicationController.showAlert("Information","This category already exists in the selected categories");
            }
        } else {
            // Show an alert if no category is selected in the ComboBox
            movieCollectionApplicationController.showAlert("Warning","Please select a category from the dropdown list.");
        }
    }

    public void onRemoveClick(ActionEvent actionEvent) {
        // Get the selected category from the ListView
        String selectedCategory = (String) lstCategory.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            // Remove the selected category from the ListView
            lstCategory.getItems().remove(selectedCategory);
        } else {
            // Show an alert if no category is selected in the ListView
            movieCollectionApplicationController.showAlert("Warning","Please select a category to remove.");
        }
    }

}
