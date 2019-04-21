package com.adpro;

import com.adpro.movie.MovieListProxy;
import com.adpro.movie.MovieRepository;
import com.adpro.movie.tmdb.TMDBClient;
import com.adpro.movie.tmdb.TMDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class MainConfig {
    @Bean
    @Autowired
    public TMDBRepository getTmdbRepository(TMDBClient tmdbClient) {
        return new TMDBRepository(tmdbClient);
    }

    @Bean
    @Autowired
    public MovieListProxy getMovieListProxy(MovieRepository movieRepository) {
        return new MovieListProxy(movieRepository);
    }

    @Bean
    public TMDBClient getTMDBClient() {
        final String BASE_URL_STRING = "https://api.themoviedb.org/3/";
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_STRING)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(TMDBClient.class);
    }
}
