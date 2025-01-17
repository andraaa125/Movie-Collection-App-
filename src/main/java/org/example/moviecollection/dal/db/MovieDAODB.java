package org.example.moviecollection.dal.db;

import org.example.moviecollection.be.Movie;
import org.example.moviecollection.dal.IMovieDAO;
import org.example.moviecollection.exceptions.MovieCollectionAppExceptions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class MovieDAODB implements IMovieDAO {
    private DBConnection con = new DBConnection();

    @Override
    public List<Movie> getAllMovies() throws MovieCollectionAppExceptions {
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
                LocalDate lastViewLocalDate = (lastViewDate != null) ? lastViewDate.toLocalDate() : null;
                Movie movie = new Movie(id, name, imdbRating, personalRating, filePath, lastViewLocalDate);
                allMovies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MovieCollectionAppExceptions("Error fetching movies from database: " + e.getMessage(), e);
        }
        return allMovies;
    }

    @Override
    public void addMovie(Movie movie) throws MovieCollectionAppExceptions{
        String sql = "INSERT INTO Movie (name, imdb_Rating, personal_rating, file_path, last_viewed) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = con.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, movie.getName());
            ps.setDouble(2, movie.getImdbRating());
            ps.setDouble(3, movie.getPersonalRating());
            ps.setString(4, movie.getFilePath());
            if (movie.getLastView() != null) {
                ps.setDate(5, java.sql.Date.valueOf(movie.getLastView()));
            } else {
                ps.setNull(5, java.sql.Types.DATE);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new MovieCollectionAppExceptions("Error adding movie to database: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteMovie(int movieId) throws MovieCollectionAppExceptions{
        String deleteMovie = "DELETE FROM Movie WHERE id = ?";

        try (Connection connection = con.getConnection();
            PreparedStatement ps1 = connection.prepareStatement(deleteMovie))
             {
            ps1.setInt(1, movieId);
            ps1.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting movie and its dependencies: " + e.getMessage(), e);
        }
    }


    @Override
    public void updateMovie(Movie movie) throws MovieCollectionAppExceptions {
        String sql = "UPDATE Movie SET name = ?, imdb_rating = ?, personal_rating = ?, file_path = ?, last_viewed = ? WHERE id = ?";
        try (Connection connection = con.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, movie.getName());
            ps.setDouble(2, movie.getImdbRating());
            ps.setDouble(3, movie.getPersonalRating());
            ps.setString(4, movie.getFilePath());
            if (movie.getLastView() != null) {
                ps.setDate(5, java.sql.Date.valueOf(movie.getLastView())); // Convert LocalDate to java.sql.Date
            } else {
                ps.setNull(5, java.sql.Types.DATE);
            }
            ps.setInt(6, movie.getId());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new MovieCollectionAppExceptions("No movie found with the given ID to update.");
            }
        } catch (SQLException e) {
            throw new MovieCollectionAppExceptions("Error updating movie in the database: " + e.getMessage(), e);
        }
    }

    public void updateLastView(int movieId) {
        String sql = "UPDATE Movie SET last_viewed = ? WHERE id = ?";
        try (Connection connection = con.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            // Use current date for last viewed
            LocalDate today = LocalDate.now();
            ps.setDate(1, java.sql.Date.valueOf(today)); // Convert LocalDate to SQL Date
            ps.setInt(2, movieId);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new MovieCollectionAppExceptions("No movie found with the given ID to update the last view.");
            }
        } catch (SQLException e) {
            try {
                throw new MovieCollectionAppExceptions("Error updating the last view in the database: " + e.getMessage(), e);
            } catch (MovieCollectionAppExceptions ex) {
                throw new RuntimeException(ex);
            }
        } catch (MovieCollectionAppExceptions e) {
            throw new RuntimeException(e);
        }
    }


    }

