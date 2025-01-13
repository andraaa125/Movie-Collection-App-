package org.example.moviecollection.gui.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.example.moviecollection.MovieCollectionApplication;
import org.example.moviecollection.dal.db.DBConnection;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class MovieCollectionApplicationController implements Initializable {


    @FXML
    private Button btnAddCategory;
    @FXML
    private ListView<String> listViewCategories;

    DBConnection dbc = new DBConnection();

    public MovieCollectionApplicationController() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCategoriesFromDatabase();

    }


    public void onSearchBtnClick(ActionEvent actionEvent) {

    }

    public void onPlayBtnClick(ActionEvent actionEvent) {

    }

    public void onAddCategoryClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieCollectionApplication.class.getResource("CategoryEditor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AddEditCategoryController categoryController = fxmlLoader.getController();
        categoryController.setParentController(this);
        Stage stage = new Stage();
        stage.setTitle("Add/Edit Category");
        stage.setScene(scene);
        stage.show();
    }

    public void onEditCategoryClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieCollectionApplication.class.getResource("CategoryEditor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AddEditCategoryController categoryController = fxmlLoader.getController();
        categoryController.setParentController(this);
        Stage stage = new Stage();
        stage.setTitle("Add/Edit Category");
        stage.setScene(scene);
        stage.show();
    }

    public void onDeleteCategoryClick(ActionEvent actionEvent) {
        
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
            }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
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

    public void loadCategoriesFromDatabase() {
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
    }

    public void addCategoryToListView(String categoryName) {
        listViewCategories.getItems().add(categoryName);
    }
}