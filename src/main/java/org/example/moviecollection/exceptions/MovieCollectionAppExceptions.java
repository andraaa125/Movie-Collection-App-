package org.example.moviecollection.exceptions;

import java.sql.SQLException;

public class MovieCollectionAppExceptions extends Exception {
    public MovieCollectionAppExceptions(Exception e) { super(e); }
    public MovieCollectionAppExceptions(String message) {
        super(message);
    }

    public MovieCollectionAppExceptions(String message, SQLException e) {
    }
}
