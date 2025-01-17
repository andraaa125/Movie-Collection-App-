package org.example.moviecollection.dal.db;

import org.example.moviecollection.be.Category;
import org.example.moviecollection.dal.ICategoryDAO;
import org.example.moviecollection.exceptions.MovieCollectionAppExceptions;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAODB implements ICategoryDAO {
    private DBConnection con = new DBConnection();

    @Override
    public List<Category> getAllCategory() throws MovieCollectionAppExceptions {
        List<Category> categories = new ArrayList<>();
        try {
            Connection c = con.getConnection();
            String sql = "SELECT * FROM Category";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){ // while there are rows
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Category category = new Category(id, name);
                categories.add(category);
            }
        } catch (SQLException e) {
            //System.err.println("SQL Error: " + e.getMessage());
            throw new MovieCollectionAppExceptions("Couldn't get all categories from SQL database",e);
        }
        return categories;
    }

    @Override
    public void deleteCategory(String name) {
        String deleteFromCategory = "DELETE FROM Category WHERE name = ?";
        try {
            Connection c = con.getConnection();
            PreparedStatement stmt = c.prepareStatement(deleteFromCategory);
            stmt.setString(1,name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting song and its dependencies: " + e.getMessage(), e);
        }
    }

    @Override
    public void addCategory(Category category) {
        String addCategory = "INSERT INTO Category (name) VALUES (?)";
        try {
            Connection c = con.getConnection();
            PreparedStatement ps = c.prepareStatement(addCategory, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,category.getName());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert category; no rows affected.");
            }
            System.out.println("Category added: " + category.getName());

        } catch (SQLException e) {
            System.err.println("SQL Error during category insertion: " + e.getMessage());
            throw new RuntimeException("Failed to add category: " + category.getName(), e);
        }
    }

    @Override
    public void updateCategory(Category category) {
        String updateCategory = "UPDATE Category SET name = ? WHERE id = ?";
        try {
            Connection c = con.getConnection();
            PreparedStatement ps = c.prepareStatement(updateCategory);
            ps.setString(1,category.getName());
            ps.setInt(2,category.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isCategoryExists(String categoryName) throws MovieCollectionAppExceptions {
        try (Connection connection = con.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM Category WHERE name = ?")) {
            ps.setString(1, categoryName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // If count > 0, the category exists
            }
        } catch (SQLException e) {
            throw new MovieCollectionAppExceptions("Error checking if category exists: " + e.getMessage(), e);
        }
        return false;
    }

}





