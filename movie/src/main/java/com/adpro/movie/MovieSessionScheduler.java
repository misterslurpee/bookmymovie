package com.adpro.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class MovieSessionScheduler {

    private MovieSessionRepository movieSessionRepository;

    @Autowired
    public MovieSessionScheduler(MovieSessionRepository movieSessionRepository) {
        this.movieSessionRepository = movieSessionRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void createMovieSession() {
    }
}
