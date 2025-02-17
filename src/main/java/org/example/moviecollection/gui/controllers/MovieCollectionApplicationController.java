package org.example.moviecollection.gui.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.example.moviecollection.MovieCollectionApplication;
import org.example.moviecollection.be.CatMovie;
import org.example.moviecollection.be.Category;
import org.example.moviecollection.be.Movie;
import org.example.moviecollection.exceptions.MovieCollectionAppExceptions;
import org.example.moviecollection.gui.model.MovieModel;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class MovieCollectionApplicationController implements Initializable {
    @FXML
    private ComboBox<String> cbSortOptions;
    @FXML
    private Button btnReset;
    @FXML
    private TextField txtQuery;
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
    @FXML
    private ListView lstCatMovie;

    private Movie movieToEdit;
    private ObservableList<Movie> movies = FXCollections.observableArrayList();
    private static final MovieModel movieModel = new MovieModel();
    private boolean isFilterActive = false; // Track the current state of the button

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCategoriesFromDatabase();
        loadMoviesFromDatabase();

        // Initialize sorting options if using ComboBox
        cbSortOptions.setItems(FXCollections.observableArrayList(
                "Sort by Name",
                "Sort by IMDB Rating",
                "Sort by My Rating",
                "Sort by Last View Date"
        ));
        cbSortOptions.setOnAction(this::onSortOptionsSelected);

        btnReset.setDisable(true);
        try {
            handleCategorySelection();
        } catch (MovieCollectionAppExceptions e) {
            throw new RuntimeException(e);
        }
        Platform.runLater(this::checkMoviesToDelete);
        try {
            handleMovieSelection();
        } catch (MovieCollectionAppExceptions e) {
            throw new RuntimeException(e);
        }

    }


    public void handleCategorySelection() throws MovieCollectionAppExceptions {
        listViewCategories.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Get the selected category
            Category selectedCategory = (Category) listViewCategories.getSelectionModel().getSelectedItem();

            if (selectedCategory == null) {
                lstCatMovie.getItems().clear();  // Clear the list if no category is selected
                return;
            }
            int categoryId = selectedCategory.getId();

            // Get the list of CatMovies associated with the selected category
            List<CatMovie> catMovies = movieModel.getMoviesInCategory(categoryId);

            // Create a new list to hold Movie objects
            List<Movie> movies = new ArrayList<>();

            // Loop through each CatMovie and find the corresponding Movie
            for (CatMovie catMovie : catMovies) {
                // Find the Movie object by matching MovieId
                for (Movie movie : movieModel.getAllMovies()) {
                    if (movie.getId() == catMovie.getMovieId()) {
                        movies.add(movie);  // Add the matching movie to the list
                        break;  // Exit the inner loop as we found the movie
                    }
                }
            }
            lstCatMovie.getItems().setAll(catMovies);
        });
    }

    public void handleMovieSelection() throws MovieCollectionAppExceptions {
        lstMovie.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Movie selectedMovie = (Movie) newValue;
                System.out.println("Selected movie ID: " + selectedMovie.getId());
            } else {
                System.out.println("No movie selected.");
            }
        });
    }

    private void checkMoviesToDelete() {
        // Get the list of movies to warn about
        List<Movie> moviesToWarn = movieModel.getFilterMoviesToDelete();
        if (!moviesToWarn.isEmpty()) {
            // Build the warning message
            StringBuilder warningMessage = new StringBuilder();
            warningMessage.append("You have movies that should be reviewed, as they have a personal rating below 6 and have not been viewed in over 2 years.\n\n");
            for (Movie movie : moviesToWarn) {
                warningMessage.append(movie.getName()).append("\n");
            }

            // Show the warning dialog
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning: Movies to Review");
            alert.setHeaderText("Attention: Movies need review");
            alert.setContentText(warningMessage.toString());
            alert.showAndWait();
        }
    }

    public void onPlayBtnClick(ActionEvent actionEvent) {
        // Get the selected movie
        Movie selectedMovie = (Movie) lstMovie.getSelectionModel().getSelectedItem();

        if (selectedMovie != null) {
            String filePath = selectedMovie.getFilePath();

            // Check if the file path is valid
            if (filePath != null && !filePath.isEmpty()) {
                File movieFile = new File(filePath);
                if (movieFile.exists()) {
                    try {
                        // Open the file with the system's default application
                        Desktop.getDesktop().open(movieFile);

                        // Update the last view in the database
                        movieModel.updateLastView(selectedMovie.getId());
                        loadMoviesFromDatabase(); // Refresh the table to show updated data

                    } catch (MovieCollectionAppExceptions e) {
                        showAlert("Error opening movie", "Unable to play the movie file.");
                        e.printStackTrace();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    showAlert("File not found", "The movie file does not exist at the specified path.");
                }
            } else {
                showAlert("Invalid file path", "The selected movie does not have a valid file path.");
            }
        } else {
            showAlert("No movie selected", "Please select a movie to play.");
        }

    }

    public void loadCategoriesFromDatabase() {
        listViewCategories.getItems().clear();
        listViewCategories.setItems(movieModel.getAllCategories());
    }

    public void loadMoviesFromDatabase() {
        lstMovie.getItems().clear();
        lstMovie.setItems(movieModel.getAllMovies());
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        IMDbRatingColumn.setCellValueFactory(new PropertyValueFactory<>("imdbRating"));
        MyRatingColumn.setCellValueFactory(new PropertyValueFactory<>("personalRating"));
        LastViewColumn.setCellValueFactory(new PropertyValueFactory<>("lastView"));
    }

    public void onAddCategoryClick(ActionEvent actionEvent) throws MovieCollectionAppExceptions, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieCollectionApplication.class.getResource("CategoryEditor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AddEditCategoryController categoryController = fxmlLoader.getController();
        categoryController.setParentController(this);
        Stage stage = new Stage();
        stage.setTitle("Add Category");
        stage.setScene(scene);
        stage.show();
    }

    public void onEditCategoryClick(ActionEvent actionEvent) throws MovieCollectionAppExceptions, IOException {
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

    public void onAddMovieClick(ActionEvent actionEvent) throws MovieCollectionAppExceptions, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieCollectionApplication.class.getResource("MovieEditor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AddEditMovieController movieController = fxmlLoader.getController();
        movieController.setParentController(this);
        Stage stage = new Stage();
        stage.setTitle("Add Movie");
        stage.setScene(scene);
        stage.show();
        movieToEdit = null;
    }

    public void onEditMovieClick(ActionEvent actionEvent) throws MovieCollectionAppExceptions, IOException {
        Movie selectedMovie = (Movie) lstMovie.getSelectionModel().getSelectedItem();
        if (selectedMovie == null) {
            showAlert("No Movie Selected","Please select a movie to edit");
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(MovieCollectionApplication.class.getResource("MovieEditor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AddEditMovieController movieController = fxmlLoader.getController();
        movieController.setParentController(this);
        movieController.setMovieToEdit(selectedMovie);
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
                    movieModel.deleteMovie(selectedMovie.getId());
                    lstMovie.getItems().remove(selectedMovie);
                    lstMovie.refresh();
                    // Inform the user about the successful deletion
                    showInfo("Success", "The movie was successfully deleted.");
                } catch (Exception e) {
                    showAlert("Error", "An error occurred while deleting the movie: " + e.getMessage());
                }
            }
        } else {
            showAlert("No Movie Selected","Please select a movie to delete");
        }
    }

    public void onSearchBtnClick(ActionEvent actionEvent) {
        String query = txtQuery.getText().trim().toLowerCase(); // Get and trim the query text
        // If query is empty, show a warning
        if (query.isEmpty()) {
            showAlert("Error", "Please input what you want to search!");
            return;
        }
        try {
            System.out.println("Searched movies in TableView: " + lstMovie.getItems()); // Log the items in the TableView
            lstMovie.setItems(movieModel.getSearchedMovies(query));  // Update TableView with filtered songs
            lstMovie.refresh();
            btnReset.setText("Reset");
            btnReset.setDisable(false);
        }catch (Exception e) {
            showAlert("Error", "An error occurred while searching the movie: .");
        }
    }


    public void onResetClick(ActionEvent actionEvent) {
        if (!btnReset.isDisable()) {
            btnReset.setText("Search");
            btnReset.setDisable(true);  // Disable the Clear button
            txtQuery.clear();
            lstMovie.refresh();
            lstMovie.setItems(movieModel.getAllMovies());
        }
    }

    public void onSortOptionsSelected(ActionEvent actionEvent) {
        String selectedOption = cbSortOptions.getValue();
        ObservableList<Movie> movies = lstMovie.getItems();

        switch (selectedOption) {
            case "Sort by Name":
                FXCollections.sort(movies, Comparator.comparing(Movie::getName, String.CASE_INSENSITIVE_ORDER));
                break;

            case "Sort by IMDB Rating":
                FXCollections.sort(movies, Comparator.comparingDouble(Movie::getImdbRating).reversed()); // Descending order
                break;

            case "Sort by My Rating":
                FXCollections.sort(movies, Comparator.comparingDouble(Movie::getPersonalRating).reversed()); // Descending order
                break;

            case "Sort by Last View Date":
                FXCollections.sort(movies, Comparator.comparing(Movie::getLastView, Comparator.nullsLast(Comparator.naturalOrder())).reversed());
                break;

            default:
                break;
        }
    }

    public void onAddMovieToCatClick(ActionEvent actionEvent) {
        Category selectedCategory = (Category) listViewCategories.getSelectionModel().getSelectedItem();
        Movie selectedMovie = (Movie) lstMovie.getSelectionModel().getSelectedItem();
        if (selectedCategory != null && selectedMovie != null) {
            try {
                movieModel.addMovieToCategory(selectedCategory.getId(),selectedMovie.getId());
                refreshCategoryTable();
                refreshMoviesInCategory(selectedCategory);
                // Re-select the previously selected playlist
                listViewCategories.getSelectionModel().select(selectedCategory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (MovieCollectionAppExceptions e) {
                throw new RuntimeException(e);
            }
        } else {
            showAlert("Error", "Please select both category and movie.");
        }

    }

    private void refreshCategoryTable() throws IOException {
        List<Category> categoryList = movieModel.getAllCategories(); // Fetch updated playlists
        listViewCategories.setItems(FXCollections.observableArrayList(categoryList)); // Update the table view
    }

    private void refreshMoviesInCategory(Category category) throws IOException {
        List<CatMovie> sop = movieModel.getMoviesInCategory(category.getId());
        lstCatMovie.setItems(FXCollections.observableArrayList(sop));
    }

    public void onRemoveMovieFromCatClick(ActionEvent actionEvent) {
        CatMovie selectedCatMovie = (CatMovie) lstCatMovie.getSelectionModel().getSelectedItem();
        Category selectedCategory= (Category) listViewCategories.getSelectionModel().getSelectedItem();
        if (selectedCatMovie != null && selectedCategory != null) {
            try {
                movieModel.removeMovieFromCategory(selectedCatMovie.getCategoryId(),selectedCatMovie.getMovieId());
                // Refresh both tables
                refreshCategoryTable();
                refreshMoviesInCategory(selectedCategory);
                System.out.println("Updated songsOnPlaylist table for playlist ID: " + selectedCategory.getId());
            } catch (IOException e) {
                showAlert("Error", "Failed to remove song from playlist.");
                e.printStackTrace();
            } catch (MovieCollectionAppExceptions e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("No song or playlist selected.");
            showAlert("Error", "No song selected.");
        }
    }
    }
