package org.example.moviecollection.dal.db;

import org.example.moviecollection.be.CatMovie;
import org.example.moviecollection.be.Category;
import org.example.moviecollection.be.Movie;
import org.example.moviecollection.dal.ICatMovieDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatMovieDAODB implements ICatMovieDAO {
    private DBConnection con = new DBConnection();

    @Override
    public List<CatMovie> getMoviesInCategory(int categoryId) throws IOException {
        List<CatMovie> catMovies = new ArrayList<>();
        try {
            Connection c = con.getConnection();
            String sql = "SELECT cm.id AS catMovieId, cm.category_Id AS categoryId, cm.movie_Id AS movieId, m.name AS movieName, c.name AS categoryName " +
                    "FROM CatMovie cm " +
                    "JOIN Movie m ON cm.movie_Id = m.id " +
                    "JOIN Category c ON cm.category_Id = c.id " +
                    "WHERE cm.category_Id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("catMovieId");
                int catId = rs.getInt("categoryId");
                int movieId = rs.getInt("movieId");
                String movieName = rs.getString("movieName");
                String categoryName = rs.getString("categoryName");
                Category category = new Category(categoryId, categoryName);
                catMovies.add(new CatMovie(id, categoryId, movieId, movieName, category)); // Adjust CatMovie constructor if necessary
            }
        } catch (SQLException e) {
            throw new IOException("SQL Error fetching movies for category: " + e.getMessage());
        }
        return catMovies;
    }

    @Override
    public List<CatMovie> getCategoriesPerMovie(int movieId) throws IOException {
        List<CatMovie> catMovies = new ArrayList<>();
        try {
            Connection c = con.getConnection();
            String sql = "SELECT cm.id AS catMovieId, cm.category_Id AS categoryId, cm.movie_Id AS movieId, m.name AS movieName, c.name AS categoryName " +
                    "FROM CatMovie cm " +
                    "JOIN Movie m ON cm.movie_Id = m.id " +
                    "JOIN Category c ON cm.category_Id = c.id " +
                    "WHERE cm.movie_Id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, movieId);  // Set the movieId parameter in the query
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("catMovieId");
                int categoryId = rs.getInt("categoryId");
                int movieIdFromDb = rs.getInt("movieId");  // This should be the same as the input movieId
                String movieName = rs.getString("movieName");
                String categoryName = rs.getString("categoryName");
                Category category = new Category(categoryId, categoryName);
                // Add the category information to the CatMovie list
                catMovies.add(new CatMovie(id, categoryId, movieIdFromDb, movieName, category)); // Adjust CatMovie constructor if necessary
            }
        } catch (SQLException e) {
            throw new IOException("SQL Error fetching categories for movie: " + e.getMessage());
        }
        return catMovies;
    }

    @Override
    public void addMovieToCategory(Category category, Movie movie) throws IOException {
        try {
            Connection c = con.getConnection();
            String sql = "INSERT INTO CatMovie (CategoryId, MovieId) VALUES (?, ?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, category.getId());
            ps.setInt(2, movie.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IOException("Error adding movie to category", e);
        }
    }

    @Override
    public void removeMovieFromCategory(Category category, Movie movie) throws IOException {
        try {
            Connection c = con.getConnection();
            String sql = "DELETE FROM CatMovie WHERE CategoryId = ? AND MovieId = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, category.getId());
            ps.setInt(2,  movie.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IOException("Error removing movie from category", e);
        }
    }

}
