package com.adpro.movie;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
public class MovieListProxyTest {
    @MockBean
    MovieRepository movieRepository;

    @Autowired
    MovieListProxy movieListProxy;

    @Test
    public void givenUpdatedRecently_thenReturnFromMemory() {
        movieListProxy.findMoviesByReleaseDateAfter(LocalDate.now());

        movieListProxy.findMoviesByReleaseDateAfter(LocalDate.now());
        verify(movieRepository, times(1))
                .findMoviesByReleaseDateAfter(LocalDate.now());
    }
}
