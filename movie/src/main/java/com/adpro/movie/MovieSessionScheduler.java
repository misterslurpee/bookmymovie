package com.adpro.movie;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class MovieSessionScheduler {

    private MovieRepository movieRepository;
    private MovieSessionRepository movieSessionRepository;

    @Autowired
    public MovieSessionScheduler(MovieRepository movieRepository, MovieSessionRepository movieSessionRepository) {
        this.movieSessionRepository = movieSessionRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void createMovieSession() {
        List<Movie> movies = movieRepository.findMoviesByReleaseDateAfter(LocalDate.now().minusDays(7));
        LocalDate dateNow = LocalDate.now();
        for (Movie movie: movies) {
            long alreadyShowedFor = Duration.between(movie.getReleaseDate(), dateNow).toDays();
            if (alreadyShowedFor < 5) {
                movieSessionRepository.save(new MovieSession(movie,
                        LocalDateTime.of(dateNow, LocalTime.of(10, 0)),
                        LocalDateTime.of(dateNow, LocalTime.of(13, 0))));

            }
        }
    }
}
