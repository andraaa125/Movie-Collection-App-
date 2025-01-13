package org.example.moviecollection.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.moviecollection.be.Movie;
import org.example.moviecollection.dal.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class AddEditCategoryController {
    @FXML
    private TextField txtAddEditCategory;

    private MovieCollectionApplicationController movieCollectionApplicationController;
    DBConnection dbc = new DBConnection();
    private MovieCollectionApplicationController parentController;

    public AddEditCategoryController() throws SQLException {
    }

    public void setParentController(MovieCollectionApplicationController parentController) {
        this.movieCollectionApplicationController = parentController;
    }

    public void onCancelCategoryClick(ActionEvent actionEvent) {
        ((javafx.stage.Stage) txtAddEditCategory.getScene().getWindow()).close();
    }

    public void onSaveCategoryClick(ActionEvent actionEvent) {
        // Get the category name from the input field
        String categoryName = txtAddEditCategory.getText().trim();

        if (categoryName == null || categoryName.isEmpty()) {
            showAlert("Error", "Category name cannot be empty!");
            return;
        }

        // Sanitize category name (optional for safety)
        String sanitizedCategoryName = categoryName.replaceAll("[^a-zA-Z0-9_ ]", "_");

        // SQL query to insert the category name into the Category table
        String insertCategorySQL = "INSERT INTO Category (name) VALUES (?)";

        try (Connection con = dbc.getConnection();
             PreparedStatement stmt = con.prepareStatement(insertCategorySQL)) {

            // Set the category name parameter
            stmt.setString(1, sanitizedCategoryName);

            // Execute the query
            stmt.executeUpdate();

            // Notify the user of success
            showAlert("Success", "Category '" + categoryName + "' added successfully!");

            // Refresh the ListView in the main controller
            if (movieCollectionApplicationController != null) {
                movieCollectionApplicationController.loadCategoriesFromDatabase();
            }

            // Close the category creation window
            ((javafx.stage.Stage) txtAddEditCategory.getScene().getWindow()).close();

        } catch (SQLIntegrityConstraintViolationException e) {
            showAlert("Error", "Category '" + categoryName + "' already exists!");
        } catch (Exception e) {
            showAlert("Error", "Failed to add category: " + e.getMessage());
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null); // Optional: You can provide a header if needed
        alert.setContentText(message);
        alert.showAndWait();
    }
}
