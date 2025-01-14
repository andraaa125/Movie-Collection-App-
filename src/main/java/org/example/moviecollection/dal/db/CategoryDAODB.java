package org.example.moviecollection.dal.db;

import org.example.moviecollection.be.Category;
import org.example.moviecollection.dal.ICategoryDAO;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAODB implements ICategoryDAO {
    private DBConnection con = new DBConnection();


    @Override
    public List<Category> getAllCategory() throws IOException{
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
            throw new IOException("Couldn't get all categories from SQL database",e);
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
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting song and its dependencies: " + e.getMessage(), e);
        }
    }
}



