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
        categoryDAO.deleteCategory(name);
    }
}
