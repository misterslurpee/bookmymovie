package com.adpro.movie;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    private MovieRepository movieRepository;
    private MovieProxy movieProxy;

    @Autowired
    public MovieController(MovieRepository movieRepository, MovieProxy movieProxy) {
        this.movieRepository = movieRepository;
        this.movieProxy = movieProxy;
    }

    @RequestMapping("/movies")
    public List<Movie> movies() throws Exception {
//        movieRepository.save(new Movie("Dilan", "Anak Bandung"));
//        return movieRepository.findAll(Sort.by("startTime"));
        return movieProxy.getLastMovies();
    }
}
