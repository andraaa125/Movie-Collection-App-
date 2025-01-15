package org.example.moviecollection.bll;

import org.example.moviecollection.be.Category;
import org.example.moviecollection.dal.ICategoryDAO;
import org.example.moviecollection.dal.db.CategoryDAODB;

import java.io.IOException;
import java.util.List;

public class CategoryManager {
    private final ICategoryDAO categoryDAO = new CategoryDAODB();

    public List<Category> getAllCategory() throws IOException {
        return categoryDAO.getAllCategory();
    }

    public void deleteCategory(String name) throws IOException {
        categoryDAO.deleteCategory(name);
    }

    public void addCategory(Category category) throws IOException {
        //categoryDAO.addCategory(category);
        try {
            categoryDAO.addCategory(category);
            System.out.println("Successfully added category: " + category.getName());
        } catch (Exception e) {
            System.err.println("Error in CategoryManager.addCategory: " + e.getMessage());
            throw new IOException("Failed to add category: " + category.getName(), e);
        }
    }

    public void updateCategory(Category category) throws IOException {
        categoryDAO.updateCategory(category);
    }


}
