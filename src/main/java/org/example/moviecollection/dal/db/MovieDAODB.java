package org.example.moviecollection.dal.db;

import org.example.moviecollection.be.Movie;
import org.example.moviecollection.dal.IMovieDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class MovieDAODB implements IMovieDAO {
    private DBConnection con = new DBConnection();

    @Override
    public List<Movie> getAllMovies() throws IOException {
        List<Movie> allMovies = new ArrayList<>();
        try {
            Connection c = con.getConnection();
            String sql = "SELECT * FROM movie";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double imdbRating = rs.getDouble("imdb_rating");
                double personalRating = rs.getDouble("personal_rating");
                String filePath = rs.getString("file_path");
                Date lastViewDate = rs.getDate("last_viewed");
                Movie movie = new Movie(name, filePath);
                allMovies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException("Error fetching movies from database: " + e.getMessage(), e);
        }
        return allMovies;
    }

    @Override
    public void addMovie(Movie movie) throws IOException{
        String sql = "INSERT INTO Movie (name, imdb_Rating, personal_rating, file_path, last_viewed) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = con.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, movie.getName());
            ps.setDouble(2, movie.getImdbRating());
            ps.setDouble(3, movie.getPersonalRating());
            ps.setString(4, movie.getFilePath());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IOException("Error adding movie to database: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteMovie(String name) throws IOException{
        String deleteFromMovie = "DELETE FROM Movie WHERE name = ?";
        String deleteFromCatMovie = "DELETE FROM Movie WHERE name = ?";
        try (Connection connection = con.getConnection();
        PreparedStatement ps1 = connection.prepareStatement(deleteFromMovie)) {
            ps1.setString(1, name);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting movie and its dependencies: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateMovie(Movie movie) throws IOException{
        String sql = "UPDATE Movie SET name = ?, personal_rating = ?, file_path = ?, last_viewed = ? WHERE name = ? AND imdb_rating = ? AND personal_rating = ?";
        try (Connection connection = con.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
             ps.setString(1, movie.getName());
             ps.setDouble(2, movie.getImdbRating());
             ps.setDouble(3, movie.getPersonalRating());
             ps.setString(4, movie.getFilePath());
             //ps.setDate(5,movie.getLastView());                     );
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting movie and its dependencies: " + e.getMessage(), e);
        }
    }



    //This method checks if a movie exists in the database
    /*private boolean movieExists(Movie movie) throws SQLException {
        String sql = "SELECT * FROM Movies WHERE name=?";
        try (Connection connection = dbConnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, movie.getName());

            try (ResultSet resultSet = stmt.executeQuery()) {
                return resultSet.next(); // Returns true if the movie exists
            }
        }
    }

    //This method adds movies to the database (avoiding duplicates... hopefully)
    public void addMovies(List<Movie> movieList) throws SQLException {
        String sql = "INSERT INTO Movie (name, imdb_Rating, personal_rating, file_path, last_viewed) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            for (Movie movie : movieList) {

                //Check if the movie exists before inserting
                if (!movieExists(movie)) {
                    stmt.setString(1, movie.getName());
                    stmt.setDouble(2, movie.getImdbRating());
                    stmt.setDouble(3, movie.getPersonalRating());
                    stmt.setString(4, movie.getFilePath());
                    stmt.addBatch();
                } else {
                    System.out.println("The Movie already exists " + movie.getName());
                }
            }
            int[] result = stmt.executeBatch(); // Execute all insertions at once
            System.out.println("Movies were saved to the database successfully!");

            //Check if the movies were inserted
            int successCount = 0;
            for (int res : result) {
                if (res == PreparedStatement.SUCCESS_NO_INFO || res > 0) {
                    successCount++;
                }
            }
            System.out.println(successCount + " movies were successfully added.");
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean updateMovie(Movie updatedMovie, double originalRating) throws SQLException {
        String sql = "UPDATE Movie SET name = ?, personal_rating = ?, file_path = ?, last_viewed = ? WHERE name = ? AND imdb_rating = ? AND personal_rating = ?";
        try (Connection connection = dbConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, updatedMovie.getName());
            pstmt.setDouble(2, updatedMovie.getImdbRating());
            pstmt.setDouble(3, updatedMovie.getPersonalRating());
            pstmt.setString(4, updatedMovie.getFilePath());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Movie updated successfully. " + updatedMovie.getName());
                return true;
            } else {
                System.out.println("Failed to update movie " + updatedMovie.getName());
                return false;
            }
        }
    }

    public boolean deleteMovie(String name) throws SQLException {
        String sql = "DELETE FROM Movie WHERE name = ?";
        try (Connection connection = dbConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Movie deleted successfully. " + name);
                return true;
            } else {
                System.out.println("Failed to delete movie " + name);
                return false;
            }
        }
    }*/

}
