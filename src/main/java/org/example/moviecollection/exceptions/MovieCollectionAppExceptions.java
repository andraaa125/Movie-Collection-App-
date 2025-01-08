package org.example.moviecollection.exceptions;

public class MovieCollectionAppExceptions extends Exception {
    public MovieCollectionAppExceptions(Exception e) { super(e); }
    public MovieCollectionAppExceptions(String message) {
        super(message);
    }
}
