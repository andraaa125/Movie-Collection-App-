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

    public void deleteCategory(String name) {
        try {
            categoryDAO.deleteCategory(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCategory(Category category) {
        try {
            categoryDAO.updateCategory(category);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
