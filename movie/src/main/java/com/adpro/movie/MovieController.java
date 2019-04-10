package com.adpro.movie;

import com.adpro.movie.tmdb.TMDBRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;


@RestController
public class MovieController {

    private MovieRepository movieRepository;
    private MovieSessionRepository movieSessionRepository;
    private MovieListProxy movieListProxy;

    @Autowired
    public MovieController(MovieRepository movieRepository,
                           MovieSessionRepository movieSessionRepository,
                           MovieListProxy movieListProxy) {
        this.movieRepository = movieRepository;
        this.movieSessionRepository = movieSessionRepository;
        this.movieListProxy = movieListProxy;
    }

    @RequestMapping("/")
    public RedirectView redirectToMovies() {
        return new RedirectView("/movies");
    }

    @RequestMapping("/movies")
    public List<Movie> movies() throws Exception {
        return movieListProxy.findMoviesByReleaseDateAfter(LocalDate.now().minusDays(7));
    }

    @RequestMapping("/movie/{movieId}")
    public List<MovieSession> movieSessions(@PathVariable Long movieId) {
        LocalDateTime midnight = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        return movieSessionRepository.findMovieSessionsByMovieIdAndStartTimeAfter(movieId, midnight);
    }
}
