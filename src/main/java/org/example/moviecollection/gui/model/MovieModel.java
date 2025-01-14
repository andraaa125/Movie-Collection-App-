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
    public void deleteCategory(String categoryName) {
        categoryManager.deleteCategory(categoryName);
    }

    public void updateCategory(Category category) throws IOException {
        categoryManager.updateCategory(category);
    }


}
