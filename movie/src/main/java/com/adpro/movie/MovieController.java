package com.adpro.movie;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class MovieController {

    private MovieRepository movieRepository;
    private MovieProxy movieProxy;

    @Autowired
    public MovieController(MovieRepository movieRepository, MovieProxy movieProxy) {
        this.movieRepository = movieRepository;
        this.movieProxy = movieProxy;
    }
    
    @RequestMapping("/")
    public RedirectView redirectToMovies() {
        return new RedirectView("/movies");
    }

    @RequestMapping("/movies")
    public List<Movie> movies() throws Exception {
        return movieProxy.getLastMovies();
    }
}
