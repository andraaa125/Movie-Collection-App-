package org.example.moviecollection.dal;

import org.example.moviecollection.be.Category;
import org.example.moviecollection.exceptions.MovieCollectionAppExceptions;

import java.io.IOException;
import java.util.List;

public interface ICategoryDAO {
    List<Category> getAllCategory() throws MovieCollectionAppExceptions;
    void deleteCategory(String name) throws MovieCollectionAppExceptions;
    void addCategory(Category category) throws MovieCollectionAppExceptions;
    void updateCategory(Category category) throws MovieCollectionAppExceptions;

    boolean isCategoryExists(String categoryName) throws MovieCollectionAppExceptions;
}
