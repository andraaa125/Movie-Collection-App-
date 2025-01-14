package org.example.moviecollection.dal;

import org.example.moviecollection.be.Category;

import java.io.IOException;
import java.util.List;

public interface ICategoryDAO {
    List<Category> getAllCategory() throws IOException;
    void deleteCategory(String name);
}
