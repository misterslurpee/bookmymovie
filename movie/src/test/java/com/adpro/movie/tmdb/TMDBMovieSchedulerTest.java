package com.adpro.movie.tmdb;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

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

        doReturn(List.of(oldTMDBMovie, newTMDBMovie))
                .when(tmdbRepository).getLastMovies();

        Movie oldMovie = Movie.builder()
                .tmdbId(1L)
                .name("Fairuzi Adventures")
                .build();
;
        doReturn(List.of(oldMovie))
                .when(movieRepository).findAllByTmdbId(any());

        FullTMDBMovie fullNewTMDBMovie = mapper.readValue("{\"id\": 2," +
                "\"original_title\": \"[BUKAN] Fairuzi Adventures\"," +
                "\"runtime\": 120}", FullTMDBMovie.class);

        doReturn(fullNewTMDBMovie)
                .when(tmdbRepository).getMovie(2L);

        tmdbMovieScheduler.updateMovieList();

        verify(tmdbRepository)
                .getMovie(2L);
    }
}