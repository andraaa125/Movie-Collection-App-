package org.example.moviecollection.bll.util;

import org.example.moviecollection.be.Movie;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class MoviesToDelete {
    public List<Movie> filterMoviesToDelete(List<Movie> movies) {
        List<Movie> warningMovies = new ArrayList<>();

        for (Movie movie : movies) {
            if (isLowRated(movie) && isNotOpenedForTwoYears(movie)) {
                warningMovies.add(movie);
            }
        }
        return warningMovies;
    }

    private boolean isLowRated(Movie movie) {
        return movie.getPersonalRating() < 6;
    }

    private boolean isNotOpenedForTwoYears(Movie movie) {
        LocalDate lastOpenedDate = movie.getLastView(); // Assuming Movie has a method getLastOpenedDate() returning LocalDate
        if (lastOpenedDate == null) {
            return false; // If lastOpenedDate is not set, consider it as recently opened
        }

        long yearsSinceOpened = ChronoUnit.YEARS.between(lastOpenedDate, LocalDate.now());
        return yearsSinceOpened > 2;
    }
}
