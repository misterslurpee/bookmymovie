package com.adpro.movie.tmdb;

import com.adpro.movie.Movie;
import com.adpro.movie.MovieRepository;
import java.util.ArrayList;
import java.util.List;
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
        List<Movie> existingMovies = movieRepository.findAllByTmdbId(movieIds);

        int ptrId = 0;
        int ptrExist = 0;
        while (ptrId < movieIds.size()) {
            if (ptrExist == existingMovies.size() ||
                    !movieIds.get(ptrId).equals(existingMovies.get(ptrExist).getTmdbId())) {
                FullTMDBMovie tmdbMovie = tmdbRepository.getMovie(movieIds.get(ptrId));
                Movie movie = Movie.fromTMDBMovie(tmdbMovie);
                movieRepository.save(movie);
                ptrId++;
            } else {
                ptrId++;
                ptrExist++;
            }
        }
    }
}
