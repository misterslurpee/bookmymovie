package com.adpro.movie;

import com.adpro.movie.tmdb.TMDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig {

    @Bean
    public TMDBRepository getTmdbRepository() {
        return new TMDBRepository();
    }

    @Bean
    @Autowired
    public MovieListProxy getMovieListProxy(MovieRepository movieRepository) {
        return new MovieListProxy(movieRepository);
    }
}
