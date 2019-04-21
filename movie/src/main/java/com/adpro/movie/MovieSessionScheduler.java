package com.adpro.movie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MovieSessionScheduler {

    private MovieRepository movieRepository;
    private MovieSessionRepository movieSessionRepository;

    @Autowired
    public MovieSessionScheduler(MovieRepository movieRepository, MovieSessionRepository movieSessionRepository) {
        this.movieRepository = movieRepository;
        this.movieSessionRepository = movieSessionRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void midnightCron() {
        checkExistOrCreateMovieSession();
    }

    @PostConstruct
    public void postConstruct() {
        checkExistOrCreateMovieSession();
    }

    public void checkExistOrCreateMovieSession() {
        List<Movie> movies = movieRepository.findMoviesByReleaseDateAfterAndReleaseDateBefore(
                LocalDate.now().minusDays(14), LocalDate.now());

        List<MovieSession> alreadyCreatedTodayMovieSession = movieSessionRepository.findMovieSessionsByStartTimeAfter(
                LocalDate.now().atStartOfDay());

        if (alreadyCreatedTodayMovieSession.size() == 0) {
            createMovieSession(movies);
        }
    }

    public void createMovieSession(List<Movie> movies) {
        LocalDate dateNow = LocalDate.now();
        List<MovieSession> willBeInsertedMovieSession = new ArrayList<>();
        for (Movie movie : movies) {
            long alreadyShowedFor = Period.between(movie.getReleaseDate(), dateNow).getDays();
            if (alreadyShowedFor < 5) {
                willBeInsertedMovieSession.add(new MovieSession(movie,
                        LocalDateTime.of(dateNow, LocalTime.of(10, 0))));
                willBeInsertedMovieSession.add(new MovieSession(movie,
                        LocalDateTime.of(dateNow, LocalTime.of(13, 0))));
                willBeInsertedMovieSession.add(new MovieSession(movie,
                        LocalDateTime.of(dateNow, LocalTime.of(17, 0))));
                willBeInsertedMovieSession.add(new MovieSession(movie,
                        LocalDateTime.of(dateNow, LocalTime.of(20, 0))));
            } else {
                willBeInsertedMovieSession.add(new MovieSession(movie,
                        LocalDateTime.of(dateNow, LocalTime.of(13, 0))));
                willBeInsertedMovieSession.add(new MovieSession(movie,
                        LocalDateTime.of(dateNow, LocalTime.of(20, 0))));
            }
        }
        movieSessionRepository.saveAll(willBeInsertedMovieSession);
    }
}
