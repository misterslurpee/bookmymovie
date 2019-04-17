package com.adpro.movie.tmdb;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.adpro.movie.Movie;
import com.adpro.movie.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TMDBMovieSchedulerTest {
    @MockBean
    private TMDBRepository tmdbRepository;

    @MockBean
    private MovieRepository movieRepository;

    @SpyBean
    @Autowired
    TMDBMovieScheduler tmdbMovieScheduler;

    @Test
    public void givenNewMovie_thenInsertToDB() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        PartialTMDBMovie oldTMDBMovie = mapper.readValue("{\"id\": 1, " +
                "\"original_title\": \"Fairuzi Adventures\"}", PartialTMDBMovie.class);

        PartialTMDBMovie newTMDBMovie = mapper.readValue("{\"id\": 2," +
                "\"original_title\": \"[BUKAN] Fairuzi Adventures\"}", PartialTMDBMovie.class);

        given(tmdbRepository.getLastMovies())
                .willReturn(List.of(oldTMDBMovie, newTMDBMovie));

        Movie oldMovie = Movie.builder()
                .tmdbId(1L)
                .name("Fairuzi Adventures")
                .build();
;
        given(movieRepository.findAllByTmdbId(any()))
                .willReturn(List.of(oldMovie));

        FullTMDBMovie fullNewTMDBMovie = mapper.readValue("{\"id\": 2," +
                "\"original_title\": \"[BUKAN] Fairuzi Adventures\"," +
                "\"runtime\": 120}", FullTMDBMovie.class);

        given(tmdbRepository.getMovie(2L))
                .willReturn(fullNewTMDBMovie);

        tmdbMovieScheduler.updateMovieList();

        then(tmdbRepository)
                .should()
                .getMovie(2L);
    }
}