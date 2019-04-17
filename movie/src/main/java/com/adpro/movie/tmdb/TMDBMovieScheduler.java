package com.adpro.movie.tmdb;

import com.adpro.movie.Movie;
import com.adpro.movie.MovieRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TMDBMovieScheduler {
    private TMDBRepository tmdbRepository;
    private MovieRepository movieRepository;

    @Autowired
    public TMDBMovieScheduler(TMDBRepository tmdbRepository, MovieRepository movieRepository) {
        this.tmdbRepository = tmdbRepository;
        this.movieRepository = movieRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void midnightCron() throws Exception {
        updateMovieList();
    }

    @PostConstruct
    public void postConstruct() throws Exception {
        updateMovieList();
    }

    private void updateMovieList() throws Exception {
        List<PartialTMDBMovie> movies = tmdbRepository.getLastMovies();
        List<Long> movieIds = new ArrayList<>();
        for (TMDBMovie movie: movies) {
            movieIds.add(movie.getId());
        }

        Set<Long> existingMovies = movieRepository.findAllByTmdbId(movieIds)
                .stream()
                .map(Movie::getId)
                .collect(Collectors.toSet());

        List<Movie> notExistMovies = new ArrayList<>();
        for (PartialTMDBMovie movie: movies) {
            if (!existingMovies.contains(movie.getId())) {
                FullTMDBMovie tmdbMovie = tmdbRepository.getMovie(movie.getId());
                notExistMovies.add(Movie.fromTMDBMovie(tmdbMovie));
            }
        }
        movieRepository.saveAll(notExistMovies);
    }
}
