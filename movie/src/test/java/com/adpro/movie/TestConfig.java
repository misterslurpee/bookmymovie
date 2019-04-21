package com.adpro.movie;

import static org.mockito.Mockito.mock;

import com.adpro.movie.tmdb.TMDBRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan
public class TestConfig {

    /**
     * Don't hit TMDB API on test.
     * @return Mocked TMDBRepository.
     */
    @Bean
    @Primary
    TMDBRepository getTMDBRepository() {
        return mock(TMDBRepository.class);
    }

}
