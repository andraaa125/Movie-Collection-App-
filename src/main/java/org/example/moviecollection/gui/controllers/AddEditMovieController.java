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
    private ObservableList<String> selectedCategories = FXCollections.observableArrayList();

    public void setParentController(MovieCollectionApplicationController movieCollectionApplicationController) {
        this.movieCollectionApplicationController = movieCollectionApplicationController;
    }

    public void initialize(URL location, ResourceBundle resources) {
        displayCategoryChoice();
        // Bind labels directly to the slider values with formatting
        IMDBScore.textProperty().bind(IMDBGradeSlider.valueProperty().asString("%.1f"));
        PersonalScore.textProperty().bind(PersonalGradeSlider.valueProperty().asString("%.1f"));

    }

    public void displayCategoryChoice() {
        try {
            // Fetch categories from the model
            ObservableList<String> categories = movieModel.displayCategoryName(); // Fetch updated list
            comboBox.setItems(categories); // Update the ComboBox items

            if (!categories.isEmpty()) {
                comboBox.setValue(categories.get(0)); // Set first category as default
            }
        } catch (MovieCollectionAppExceptions e) {
            System.out.println("Error loading categories: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void displayCategoriesPerMovie(Movie selectedMovie) {
        if (selectedMovie == null) {
            System.out.println("No movie selected.");
            return; // If no movie is selected, exit the method early
        }

        // Fetch categories related to the selected movie (specific categories for this movie)
        ObservableList<CatMovie> catMovies = movieModel.getCategoriesPerMovie(selectedMovie.getId()); // Get categories for the selected movie
        System.out.println("Categories for selected movie (ID: " + selectedMovie.getId() + "): " + catMovies);

        // Clear the current categories in lstCategory
        lstCategory.getItems().clear();
        System.out.println("Cleared existing categories in ListView.");

        // Add only the categories related to the selected movie to lstCategory
        for (CatMovie catMovie : catMovies) {
            if (catMovie.getCategory() != null) { // Check if category is not null
                String categoryName = catMovie.getCategory().getName(); // Get the category name
                lstCategory.getItems().add(categoryName); // Add category name to the list
                System.out.println("Added category: " + categoryName);
            } else {
                System.out.println("CatMovie has null category. Skipping.");
            }
        }

        // Optionally, mark the categories that the movie belongs to (already selected categories)
        for (CatMovie catMovie : catMovies) {
            if (catMovie.getCategory() != null) { // Check if category is not null
                String categoryName = catMovie.getCategory().getName();
                int index = lstCategory.getItems().indexOf(categoryName); // Find the index of the category
                if (index != -1) {
                    lstCategory.getSelectionModel().select(index); // Select the category in the list
                    System.out.println("Selected category: " + categoryName);
                }
            }
        }
    }

    public void setMovieToEdit(Movie movieToEdit) throws MovieCollectionAppExceptions {
        this.movieToEdit = movieToEdit;
        txtName.setText(movieToEdit.getName());
        txtFilePath.setText(movieToEdit.getFilePath());

        ObservableList<String> movieCategories = movieToEdit.getCategories();
        if (movieCategories == null) {
            movieCategories = FXCollections.observableArrayList(); // Initialize as an empty list if null
        }
        selectedCategories.clear(); // Clear any existing categories
        selectedCategories.addAll(movieCategories); // Add the movie's categories to the selectedCategories list
        lstCategory.setItems(selectedCategories); // Update the ListView with the selected categories
        IMDBGradeSlider.setValue(movieToEdit.getImdbRating());
        PersonalGradeSlider.setValue(movieToEdit.getPersonalRating());
        displayCategoriesPerMovie(movieToEdit);
    }

    public void onCancelMovieClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancelMovie.getScene().getWindow();
        stage.close();
    }

    /*public void onSaveMovieClick(ActionEvent actionEvent) {
        String newMovieName = txtName.getText();
        String newMoviePath = txtFilePath.getText();
        Double newImdbRating = IMDBGradeSlider.getValue();
        Double newPersonalRating = PersonalGradeSlider.getValue();
        try {
            Movie newMovie = new Movie(newMovieName, newImdbRating, newPersonalRating, newMoviePath, selectedCategories);
            movieModel.addMovie(newMovie);
            for (String categoryName : selectedCategories) {
                // Retrieve the Category object based on the category name
                Category category = movieModel.getCategoryByName(categoryName);
                if (category != null) {
                    // Add the created movie to the retrieved category
                    movieModel.addMovieToCategory(category, newMovie);
                }
                System.out.println("New movie added with categories.");
            }
            movieCollectionApplicationController.loadCategoriesFromDatabase();
            movieCollectionApplicationController.loadMoviesFromDatabase();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

    public void onSaveMovieClick(ActionEvent actionEvent) throws IOException {
        try {
            if (txtName.getText().isEmpty() || txtFilePath.getText().isEmpty() || lstCategory.getItems().isEmpty()) {
                movieCollectionApplicationController.showAlert("Validation Error", "All fields must be filled!");
                return;
            }
            String newMovieName = txtName.getText();
            String newMoviePath = txtFilePath.getText();
            Double newImdbRating = IMDBGradeSlider.getValue();
            Double newPersonalRating = PersonalGradeSlider.getValue();
            ObservableList<String> selectedCategories = lstCategory.getItems();// Get the selected categories from the ListView

            System.out.println("New Movie Details: ");
            System.out.println("Name: " + newMovieName);
            System.out.println("Path: " + newMoviePath);
            System.out.println("IMDB Rating: " + newImdbRating);
            System.out.println("Personal Rating: " + newPersonalRating);
            System.out.println("Selected Categories: " + selectedCategories);

            if (movieToEdit != null) {
                movieToEdit.setName(newMovieName);
                movieToEdit.setFilePath(newMoviePath);
                movieToEdit.setImdbRating(newImdbRating);
                movieToEdit.setPersonalRating(newPersonalRating);
                movieToEdit.setCategories(selectedCategories);
                movieModel.updateMovie(movieToEdit);

                System.out.println("Movie updated with new categories.");

                // Remove old categories and add new ones
                for (String categoryName : selectedCategories) {
                    System.out.println("Checking category: " + categoryName);
                    Category category = movieModel.getCategoryByName(categoryName);
                    if (category != null) {
                        System.out.println("Adding movie to category: " + category.getName());
                        movieModel.addMovieToCategory(category, movieToEdit); // Add movie to new category
                        System.out.println("Category not found: " + categoryName);
                    }
                }
            } else {
                Movie newMovie = new Movie(newMovieName, newImdbRating, newPersonalRating, newMoviePath, selectedCategories);
                movieModel.addMovie(newMovie);
                System.out.println("New movie added with categories.");
                // Associate the movie with selected categories
                for (String categoryName : selectedCategories) {
                    System.out.println("Checking category: " + categoryName);
                    // Retrieve the Category object based on the category name
                    Category category = movieModel.getCategoryByName(categoryName);
                    if (category != null) {
                        System.out.println("Adding new movie to category: " + category.getName());
                        // Add the created movie to the retrieved category
                        movieModel.addMovieToCategory(category, newMovie);
                    }else {
                        System.out.println("Category not found: " + categoryName);
                } System.out.println("New movie added with categories.");}

                }
                movieCollectionApplicationController.loadCategoriesFromDatabase();
                movieCollectionApplicationController.loadMoviesFromDatabase();
            System.out.println("Categories and Movies reloaded from database.");
                //movieCollectionApplicationController.lstMovie.getItems().clear();
                //movieCollectionApplicationController.lstMovie.setItems(movieModel.getAllMovies());


        } catch (MovieCollectionAppExceptions e) {
            movieCollectionApplicationController.showAlert("Error", "An error occurred.");
            throw new RuntimeException(e);
        }finally {
            // Ensure the stage closes regardless of errors
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
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
            // Add the selected category to the selectedCategories ObservableList
            if (!selectedCategories.contains(selectedCategory)) {
                selectedCategories.add(selectedCategory);
                lstCategory.setItems(selectedCategories); // Refresh ListView with updated list
            } else {
                movieCollectionApplicationController.showAlert("Information", "This category already exists in the selected categories.");
            }
        } else {
            // Show an alert if no category is selected in the ComboBox
            movieCollectionApplicationController.showAlert("Warning","Please select a category from the dropdown list.");
        }
    }

    public void onRemoveClick(ActionEvent actionEvent) {
        String selectedCategory = (String) lstCategory.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            selectedCategories.remove(selectedCategory); // Remove from the ObservableList
            lstCategory.setItems(selectedCategories); // Refresh ListView
        } else {
            movieCollectionApplicationController.showAlert("Warning", "Please select a category to remove.");
        }
    }

}
