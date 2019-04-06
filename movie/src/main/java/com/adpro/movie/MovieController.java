package com.adpro.movie;

import com.adpro.movie.tmdb.TMDBRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;


@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieSessionRepository movieSessionRepository;

    @Autowired
    private TMDBRepository tmdbRepository;

    @RequestMapping("/")
    public RedirectView redirectToMovies() {
        return new RedirectView("/movies");
    }

    @RequestMapping("/movies")
    public List<PartialMovie> movies() throws Exception {
        return tmdbRepository.getLastMovies();
    }

    @RequestMapping("/movie/{movieId}")
    public List<MovieSession> movieSessions(@PathVariable Long movieId) {
        return movieSessionRepository.findMovieSessionsByMovieIdAndStartTimeAfter(movieId, LocalDateTime.now());
    }
}
