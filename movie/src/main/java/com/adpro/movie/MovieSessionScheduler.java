package com.adpro.movie;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

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
        createMovieSession();
    }

    @PostConstruct
    public void postConstruct() {
        createMovieSession();
    }

    public void createMovieSession() {
        List<Movie> movies = movieRepository.findMoviesByReleaseDateAfter(LocalDate.now().minusDays(14));
        LocalDate dateNow = LocalDate.now();

        List<Movie> alreadyCreatedTodayMovieSession = movieSessionRepository.findMovieSessionsByStartTimeAfter(
                LocalDate.now().atStartOfDay());

        if (alreadyCreatedTodayMovieSession.size() == 0) {
            for (Movie movie : movies) {
                long alreadyShowedFor = Duration.between(movie.getReleaseDate(), dateNow).toDays();
                if (alreadyShowedFor < 5) {
                    movieSessionRepository.save(new MovieSession(movie,
                            LocalDateTime.of(dateNow, LocalTime.of(10, 0))));
                    movieSessionRepository.save(new MovieSession(movie,
                            LocalDateTime.of(dateNow, LocalTime.of(13, 0))));
                    movieSessionRepository.save(new MovieSession(movie,
                            LocalDateTime.of(dateNow, LocalTime.of(17, 0))));
                    movieSessionRepository.save(new MovieSession(movie,
                            LocalDateTime.of(dateNow, LocalTime.of(20, 0))));
                } else {
                    movieSessionRepository.save(new MovieSession(movie,
                            LocalDateTime.of(dateNow, LocalTime.of(13, 0))));
                    movieSessionRepository.save(new MovieSession(movie,
                            LocalDateTime.of(dateNow, LocalTime.of(20, 0))));
                }
            }
        }
    }
}
