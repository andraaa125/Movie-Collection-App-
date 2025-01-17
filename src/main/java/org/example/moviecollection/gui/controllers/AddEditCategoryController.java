package org.example.moviecollection.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.moviecollection.be.Category;
import org.example.moviecollection.gui.model.MovieModel;


public class AddEditCategoryController {
    @FXML
    private TextField txtAddEditCategory;

    private MovieCollectionApplicationController movieCollectionApplicationController;
    private MovieModel movieModel = new MovieModel();
    private Category categoryToEdit;

    public void setParentController(MovieCollectionApplicationController parentController) {
        this.movieCollectionApplicationController = parentController;
    }

    public void setCategoryToEdit(Category categoryToEdit) {
        this.categoryToEdit = categoryToEdit;
        txtAddEditCategory.setText(categoryToEdit.getName());
    }

    public void onCancelCategoryClick(ActionEvent actionEvent) {
        ((javafx.stage.Stage) txtAddEditCategory.getScene().getWindow()).close();
    }

    public void onSaveCategoryClick(ActionEvent actionEvent) {
        try {
            String newCategoryName = txtAddEditCategory.getText().trim();
            if (newCategoryName.isEmpty()) {
                movieCollectionApplicationController.showAlert("Error", "Category name cannot be empty!");
                return;
            }
            // Check if the category already exists
            if (movieModel.isCategoryExists(newCategoryName)) {
                movieCollectionApplicationController.showAlert("Error", "Category already exists!");
                return;
            }

            if (categoryToEdit != null) {
                categoryToEdit.setName(newCategoryName);
                movieModel.updateCategory(categoryToEdit);
                System.out.println("Category updated: " + newCategoryName);
            } else {
                Category newCategory = new Category(0, newCategoryName);
                movieModel.addCategory(newCategory);
                System.out.println("New category added: " + newCategoryName);
            }

            movieCollectionApplicationController.loadCategoriesFromDatabase();
            ((javafx.stage.Stage) txtAddEditCategory.getScene().getWindow()).close();

        } catch (Exception e) {
            movieCollectionApplicationController.showAlert("Error", "An error occurred: " + e.getMessage());
            System.err.println("Error during category save: " + e.getMessage());
            e.printStackTrace();
        }
        }

    }

