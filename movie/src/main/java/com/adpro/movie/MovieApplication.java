package com.adpro.movie;

import com.adpro.movie.tmdb.TMDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MovieApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieApplication.class, args);
	}

	@Bean
	@Autowired
	public TMDBRepository getTmdbRepository(MovieRepository movieRepository) throws Exception {
		return new TMDBRepository(movieRepository);
	}

	@Bean
	@Autowired
	public MovieListProxy getMovieListProxy(MovieRepository movieRepository) {
		return new MovieListProxy(movieRepository);
	}

}
