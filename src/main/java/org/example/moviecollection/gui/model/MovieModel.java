package org.example.moviecollection.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.moviecollection.be.Category;
import org.example.moviecollection.bll.CategoryManager;

import java.io.IOException;
import java.util.List;

public class MovieModel {
    private final CategoryManager categoryManager = new CategoryManager();
    private final ObservableList<Category> categories = FXCollections.observableArrayList();

    public ObservableList<Category> getAllCategories() {
        try {
            List<Category> categoryList = categoryManager.getAllCategory();
            this.categories.setAll(categoryList);
        } catch (IOException e) {
            System.out.println("Error loading playlists: " + e.getMessage());
        }
        return categories;
    }
    public void deleteCategory(String categoryName) throws IOException {
        categoryManager.deleteCategory(categoryName);
    }

    public void addCategory(Category newCategory) throws IOException {
        //categoryManager.addCategory(newCategory);
        try {
            categoryManager.addCategory(newCategory);
            categories.add(newCategory); // Add the new category to the observable list
            System.out.println("Category added to MovieModel: " + newCategory.getName());
        } catch (Exception e) {
            System.err.println("Error in MovieModel.addCategory: " + e.getMessage());
            throw new IOException("Failed to add category in MovieModel.", e);
        }
    }

    public void updateCategory(Category category) throws IOException {
        categoryManager.updateCategory(category);
    }
}
