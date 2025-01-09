package org.example.moviecollection.dal.db;

import org.example.moviecollection.be.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class MovieDAODB {

    private DBConnection dbConnection = new DBConnection();
    public MovieDAODB() throws SQLException {}
    public DBConnection getDbConnection() {
        return dbConnection;
    }
    public void setDbConnection(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<Movie> getALlMovies(){
        List<Movie> allMovies = new ArrayList<>();
        String sql = "SELECT * FROM Movies";

        try (Statement statement = dbConnection.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double imdbRating = resultSet.getDouble("imdb_rating");
                double personalRating = resultSet.getDouble("personal_rating");
                String filePath = resultSet.getString("file_path");
                Date lastViewDate = resultSet.getDate("last_viewed");

                Movie movie = new Movie(id, name, imdbRating, personalRating, filePath, lastViewDate);
                allMovies.add(movie);
            }
            System.out.println("Fetched Movies: " + allMovies);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allMovies;
    }

    //This method checks if a movie exists in the database
    private boolean movieExists(Movie movie) throws SQLException {
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
        String sql = "INSERT INTO Movies (name, imdbRating, personalRating, filePath, lastViewed) VALUES (?, ?, ?, ?, ?)";
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
        String sql = "UPDATE Movies SET name = ?, PersonalRating = ?, FilePath = ?, LastViewed = ? WHERE Name = ? AND IMDBRating = ? AND PersonalRating = ?";
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
        String sql = "DELETE FROM Movies WHERE Name = ?";
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
    }

}
