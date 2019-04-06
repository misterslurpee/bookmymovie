package com.adpro.movie;

import com.adpro.movie.tmdb.TMDBClient;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Cache the result from DB into memory for at least 5 minutes for every unique parameter given.
 */
public class MovieListProxy implements MovieListRepository {

    private final static int MIN_DELAY = 5;

    private MovieRepository movieRepository;
    private Map<LocalDate, List<Movie>> lastMovies;
    private Map<LocalDate, LocalDateTime> lastUpdate;

    @Autowired
    public MovieListProxy(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        lastMovies = new HashMap<>();
        lastUpdate = new HashMap<>();
    }

    public List<Movie> findMoviesByReleaseDateAfter(LocalDate date) {
        LocalDateTime dateLastUpdate = this.lastUpdate.getOrDefault(date, LocalDateTime.MIN);
        if (Duration.between(dateLastUpdate, LocalDateTime.now()).toMinutes() > MIN_DELAY) {
            List<Movie> result = movieRepository.findMoviesByReleaseDateAfter(date);
            lastMovies.put(date, result);
            lastUpdate.put(date, LocalDateTime.now());
        }
        return lastMovies.get(date);
    }
}
