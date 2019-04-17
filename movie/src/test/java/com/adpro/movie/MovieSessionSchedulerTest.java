package com.adpro.movie;

import static org.mockito.BDDMockito.*;

import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
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
        given(movieSessionRepository.findMovieSessionsByStartTimeAfter(any()))
                .willReturn(Collections.emptyList());
        movieSessionScheduler.checkExistOrCreateMovieSession();

        then(movieSessionScheduler)
                .should()
                .createMovieSession(any());
    }
}