package org.example.moviecollection.dal.db;

import org.example.moviecollection.be.CatMovie;
import org.example.moviecollection.be.Category;
import org.example.moviecollection.dal.ICatMovieDAO;
import org.example.moviecollection.exceptions.MovieCollectionAppExceptions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatMovieDAODB implements ICatMovieDAO {
    private DBConnection con = new DBConnection();

    @Override
    public List<CatMovie> getMoviesInCategory(int categoryId) throws MovieCollectionAppExceptions {
        List<CatMovie> catMovies = new ArrayList<>();
        try {
            Connection c = con.getConnection();
            String sql = "SELECT cm.id AS catMovieId, cm.category_id AS categoryId, cm.movie_id AS movieId, m.name AS movieName, c.name AS categoryName " +
                    "FROM CatMovie cm " +
                    "JOIN Movie m ON cm.movie_id = m.id " +
                    "JOIN Category c ON cm.category_id = c.id " +
                    "WHERE cm.category_id = ?";
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
            throw new MovieCollectionAppExceptions("SQL Error fetching movies for category: " + e.getMessage());
        }
        return catMovies;
    }

    @Override
    public List<CatMovie> getCategoriesPerMovie(int movieId) throws MovieCollectionAppExceptions {
        List<CatMovie> catMovies = new ArrayList<>();
        try {
            Connection c = con.getConnection();
            String sql = "SELECT cm.id AS catMovieId, cm.category_id AS categoryId, cm.movie_id AS movieId, m.name AS movieName, c.name AS categoryName " +
                    "FROM CatMovie cm " +
                    "JOIN Movie m ON cm.movie_id = m.id " +
                    "JOIN Category c ON cm.category_id = c.id " +
                    "WHERE cm.movie_id = ?";
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
            throw new MovieCollectionAppExceptions("SQL Error fetching categories for movie: " + e.getMessage());
        }
        return catMovies;
    }

    @Override
    public void addMovieToCategory(int categoryId, int movieId) throws MovieCollectionAppExceptions {
        try {
            Connection c = con.getConnection();
            String sql = "INSERT INTO CatMovie (category_id, movie_id) VALUES (?, ?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, categoryId);
            ps.setInt(2, movieId);
            ps.executeUpdate();
        } catch (SQLException e) {
            // Print stack trace for any SQL exceptions
            e.printStackTrace();
            // Rethrow custom exception with the original SQLException
            throw new MovieCollectionAppExceptions("Error adding movie to category", e);
        }
    }


/*    @Override
    public void addMovieToCategory(Category category, Movie movie) throws MovieCollectionAppExceptions {
        System.out.println("addMovieToCategory called with Category ID: " + category.getId() + ", Movie ID: " + movie.getId());
        try {
            Connection c = con.getConnection();
            String sql = "INSERT INTO CatMovie (category_id, movie_id) VALUES (?, ?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, category.getId());
            ps.setInt(2, movie.getId());
            System.out.println("Executing SQL: " + ps.toString());

            // Execute the SQL command
            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            throw new MovieCollectionAppExceptions("Error adding movie to category", e);
        }
    }*/

    /*@Override
    public void removeMovieFromCategory(Category category, Movie movie) throws MovieCollectionAppExceptions {
        try {
            Connection c = con.getConnection();
            String sql = "DELETE FROM CatMovie WHERE category_id = ? AND movie_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, category.getId());
            ps.setInt(2,  movie.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new MovieCollectionAppExceptions("Error removing movie from category", e);
        }
    }*/
    @Override
    public void removeMovieFromCategory(int categoryId, int movieId) throws MovieCollectionAppExceptions {
        try {
            Connection c = con.getConnection();
            String sql = "DELETE FROM CatMovie WHERE category_id = ? AND movie_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, categoryId);
            ps.setInt(2,  movieId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new MovieCollectionAppExceptions("Error removing movie from category", e);
        }
    }

}
