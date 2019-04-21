package com.adpro;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adpro.movie.Movie;
import com.adpro.movie.MovieListProxy;
import com.adpro.movie.MovieSession;
import com.adpro.movie.MovieSessionRepository;
import com.adpro.seat.Theatre;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestConfig.class })
@AutoConfigureMockMvc
public class MovieApplicationTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private MovieListProxy movieListProxy;

	@MockBean
	private MovieSessionRepository movieSessionRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetMovies() throws Exception {
		Movie movie = Movie.builder()
				.name("Fairuzi Adventures")
				.description("Petualangan seorang Fairuzi")
				.duration(Duration.ofMinutes(111))
				.posterUrl("sdada")
				.releaseDate(LocalDate.now())
				.id(1L)
				.build();

		given(movieListProxy.findMoviesByReleaseDateAfter(any())).willReturn(List.of(movie));
		this.mvc.perform(get("/movies"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].description", is(movie.getDescription())))
				.andExpect(jsonPath("$[0].posterUrl", is(movie.getPosterUrl())))
				.andExpect(jsonPath("$[0].releaseDate", is(movie.getReleaseDate().toString())))
				.andExpect(jsonPath("$[0].duration", is("01:51:00")));
	}

	@Test
	public void testRootRedirectToMovies() throws Exception {
		this.mvc.perform(get("/"))
				.andExpect(redirectedUrl("/movies"));
	}

	@Test
	public void testGetMovieSessions() throws Exception {
		Movie movie = Movie.builder()
				.name("Fairuzi Adventures")
				.description("Petualangan seorang Fairuzi")
				.duration(Duration.ofMinutes(111))
				.posterUrl("sdada")
				.releaseDate(LocalDate.now())
				.id(1L)
				.build();

		LocalDateTime dayTime = LocalDateTime.of(1999, 8, 10, 10, 0);
		LocalDateTime nightTime = LocalDateTime.of(1999, 8, 10, 19, 0);
		MovieSession daySession = new MovieSession(movie, dayTime);
		MovieSession nightSession = new MovieSession(movie, nightTime);

		given(movieSessionRepository.findMovieSessionsByMovieIdAndStartTimeAfter(any(), any()))
				.willReturn(List.of(daySession, nightSession));

		mvc.perform(get("/movie/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].startTime",
						is(dayTime.format(DateTimeFormatter.ISO_DATE_TIME))))
				.andExpect(jsonPath("$[1].startTime",
						is(nightTime.format(DateTimeFormatter.ISO_DATE_TIME))));
	}


	@Test
	public void getTheatreWithSeat() throws Exception {
		Theatre theatre = new Theatre(1, "A", 50);

		this.mvc.perform(get("/seat"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].seatNumber", is(1)))
				.andExpect(jsonPath("$[0].type", is(theatre.getRows().get(0).getType())))
				.andExpect(jsonPath("$[0].booked", is(theatre.getRows().get(0).isBooked())));
	}
}
