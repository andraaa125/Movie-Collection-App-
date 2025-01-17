package org.example.moviecollection.bll;

import javafx.collections.ObservableList;
import org.example.moviecollection.be.Category;
import org.example.moviecollection.dal.ICategoryDAO;
import org.example.moviecollection.dal.db.CategoryDAODB;
import org.example.moviecollection.exceptions.MovieCollectionAppExceptions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CategoryManager {
    private final ICategoryDAO categoryDAO = new CategoryDAODB();

    public List<Category> getAllCategory() throws MovieCollectionAppExceptions {
        return categoryDAO.getAllCategory();
    }

    public void deleteCategory(String name) throws MovieCollectionAppExceptions {
        categoryDAO.deleteCategory(name);
    }

    public void addCategory(Category category) throws MovieCollectionAppExceptions {
        //categoryDAO.addCategory(category);
        try {
            categoryDAO.addCategory(category);
            System.out.println("Successfully added category: " + category.getName());
        } catch (Exception e) {
            System.err.println("Error in CategoryManager.addCategory: " + e.getMessage());
            throw new MovieCollectionAppExceptions("Failed to add category: " + category.getName(), (SQLException) e);
        }
    }

    public void updateCategory(Category category) throws MovieCollectionAppExceptions {
        categoryDAO.updateCategory(category);
    }

    public boolean isCategoryExists(String categoryName) throws MovieCollectionAppExceptions {
        return categoryDAO.isCategoryExists(categoryName);
    }

}
