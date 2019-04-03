package com.adpro.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    private MovieRepository movieRepository;

    @Autowired
    public void setMovieRepository(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @RequestMapping("/movies")
    public Iterable<Movie> movies() {
        movieRepository.save(new Movie("Dilan", "Anak Bandung"));
        return movieRepository.findAll(Sort.by("startTime"));
    }
}
