package org.example.moviecollection.gui.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.example.moviecollection.MovieCollectionApplication;
import org.example.moviecollection.be.Category;
import org.example.moviecollection.bll.FilterService;
import org.example.moviecollection.gui.model.MovieModel;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class MovieCollectionApplicationController implements Initializable {

    @FXML
    private Button btnAddCategory;
    @FXML
    private ListView listViewCategories;

    private final MovieModel movieModel = new MovieModel();
    private final FilterService filterService = new FilterService();
    private boolean isFilterActive = false; // Track the current state of the button

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCategoriesFromDatabase();

    }
    public void loadCategoriesFromDatabase() {
        listViewCategories.getItems().clear();
        listViewCategories.setItems(movieModel.getAllCategories());
    }


    /*public void loadCategoriesFromDatabase() {
        String selectCategorySQL = "SELECT name FROM Category";

        try (Connection con = dbc.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(selectCategorySQL)) {

            // Debug: Ensure ListView is initialized
            if (listViewCategories == null) {
                System.err.println("ListView is null. Check FXML binding!");
                return;
            }

            // Clear the ListView
            listViewCategories.getItems().clear();
            System.out.println("ListView cleared.");

            // Fetch categories
            while (rs.next()) {
                String categoryName = rs.getString("name");
                System.out.println("Fetched category: " + categoryName);
                Platform.runLater(() -> listViewCategories.getItems().add(categoryName));
            }

        } catch (SQLException e) {
            System.err.println("SQL error occurred:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error occurred:");
            e.printStackTrace();
        }
    }*/

    /*public void onSearchBtnClick(ActionEvent actionEvent) {
        if (isFilterActive) {
            // Clear filter
            lstViewSongs.setItems(FXCollections.observableArrayList(songsModel.getAllSongs()));
            fieldFilterSearch.clear();
            btnFilter.setText("Filter");
            isFilterActive = false;
        } else {
            // Apply filter
            String filterQuery = fieldFilterSearch.getText().trim().toLowerCase();
            List<Songs> filteredSongs = filterService.filterSongs(
                    songsModel.getAllSongs(), // Get all songs
                    filterQuery // Pass query to the filter method
            );
            System.out.println("Filtered Songs Count: " + filteredSongs.size());
            lstViewSongs.setItems(FXCollections.observableArrayList(filteredSongs));
            btnFilter.setText("Clear");
            isFilterActive = true;
        }
    }*/


    public void onAddCategoryClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieCollectionApplication.class.getResource("CategoryEditor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AddEditCategoryController categoryController = fxmlLoader.getController();
        categoryController.setParentController(this);
        categoryController.setCategoryModel(movieModel);
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
        categoryController.setCategoryModel(movieModel);
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
        /*
            // Get the selected category from the ListView
            String selectedCategory = listViewCategories.getSelectionModel().getSelectedItem();

            if (selectedCategory != null) {
                try {
                    //  Retrieve the database connection
                    Connection con = dbc.getConnection(); // Fetch actual Connection object from dbc

                    //  Prepare the SQL query
                    String deleteSQL = "DELETE FROM Category WHERE name = ?";
                    PreparedStatement pstmt = con.prepareStatement(deleteSQL);
                    pstmt.setString(1, selectedCategory);

                    //  Execute the query
                    int affectedRows = pstmt.executeUpdate();
                    pstmt.close();

                    //  Check if deletion was successful
                    if (affectedRows > 0) {

                        listViewCategories.getItems().remove(selectedCategory);
                        showAlert("Success", "Category '" + selectedCategory + "' has been deleted.");
                    } else {
                        showAlert("Error", "Could not delete category.");
                    }
                } catch (SQLException e) {
                    showAlert("Database Error", "Error deleting category: " + e.getMessage());
                }
            } else {
                showAlert("Warning", "Please select a category to delete.");
            }*/
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
        stage.setTitle("Add/Edit Movie");
        stage.setScene(scene);
        stage.show();
    }

    public void onEditMovieClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieCollectionApplication.class.getResource("MovieEditor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AddEditMovieController movieController = fxmlLoader.getController();
        movieController.setParentController(this);
        Stage stage = new Stage();
        stage.setTitle("Add/Edit Movie");
        stage.setScene(scene);
        stage.show();
    }

    public void onDeleteMovieClick(ActionEvent actionEvent) {
    }



    public void addCategoryToListView(String categoryName) {
        listViewCategories.getItems().add(categoryName);
    }
}