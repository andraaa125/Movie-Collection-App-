package org.example.moviecollection.bll.util;

import org.example.moviecollection.be.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieSearcher {
    public List<Movie> search(List<Movie> searchBase, String query){
        List<Movie> searchResult = new ArrayList<>();
        for(Movie movie : searchBase){
            if(compareToMovieName(query, movie) || compareToMovieCategory(query,movie) || compareToIMDBRating(query, movie))
            {
                searchResult.add(movie);
            } else {
                System.out.println("No match for movie: " + movie); // Print non-matching songs
            }
        }
        return searchResult;
    }

    private boolean compareToMovieName(String query, Movie movie){
        return movie.getName().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToMovieCategory(String query, Movie movie){
        return movie.toString().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToIMDBRating(String query, Movie movie) {
        try {
            // Parse the query as a double to compare with the IMDb rating
            double queryRating = Double.parseDouble(query);

            // Check if the movie's IMDb rating matches the query
            return movie.getImdbRating() == queryRating;
        } catch (NumberFormatException e) {
            // If the query is not a valid number, ignore it for IMDb rating comparison
            System.out.println("Invalid IMDb rating query: " + query);
            return false;
        }
    }
}
