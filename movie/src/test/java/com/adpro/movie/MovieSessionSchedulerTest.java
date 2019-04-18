package com.adpro.movie;

import static org.mockito.BDDMockito.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
public class MovieSessionSchedulerTest {

    @MockBean
    private MovieSessionRepository movieSessionRepository;

    @MockBean
    private MovieRepository movieRepository;

    @Autowired
    @SpyBean
    MovieSessionScheduler movieSessionScheduler;

    @Test
    public void givenNoMovieSessionAlreadyCreatedForToday_thenCreateNewMovieSessions() {
        Movie newMovie = Movie.builder()
            .name("Fairuzi Adventures")
            .description("Petualangan seorang Fairuzi")
            .duration(Duration.ofMinutes(111))
            .posterUrl("sdada")
            .releaseDate(LocalDate.now())
            .build();

        Movie oldMovie = Movie.builder()
            .name("[BUKAN] Fairuzi Adventures")
            .description("Petualangan seorang Fairuzi")
            .duration(Duration.ofMinutes(111))
            .posterUrl("sdada")
            .releaseDate(LocalDate.now().minusDays(10))
            .build();

        given(movieRepository.findMoviesByReleaseDateAfterAndReleaseDateBefore(any(), any()))
                .willReturn(List.of(newMovie, oldMovie));

        given(movieSessionRepository.findMovieSessionsByStartTimeAfter(any()))
                .willReturn(Collections.emptyList());

        movieSessionScheduler.checkExistOrCreateMovieSession();
        then(movieSessionScheduler)
                .should()
                .createMovieSession(any());
    }
}