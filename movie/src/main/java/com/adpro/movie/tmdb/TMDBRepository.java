package com.adpro.movie.tmdb;

import com.adpro.movie.FullMovie;
import com.adpro.movie.Movie;
import com.adpro.movie.MovieRepository;
import com.adpro.movie.PartialMovie;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Parse movie from TMDB API and save it to DB.
 */
public class TMDBRepository {
    private static final String BASE_URL_STRING = "https://api.themoviedb.org/3/";
    private static final String KEY = "8d14316a3ad3955af1670a00e39e50ab";

    @Autowired
    @NotNull
    MovieRepository movieRepository;

    @NotNull
    private TMDBClient tmdbClient;

    public TMDBRepository(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        tmdbClient = new Retrofit.Builder()
                .baseUrl(BASE_URL_STRING)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(TMDBClient.class);
    }

    /**
     * Get FullMovie from the individual movie API.
     * @param movieId the tmdb ID of the movie
     * @return the FullMovie object
     */
    public Movie getMovie(Long movieId) throws Exception {
        final String relUrl = "/movie/" + movieId;
        FullTMDBMovie fullTMDBMovie = tmdbClient.movie(movieId, KEY).execute().body();
        if (fullTMDBMovie == null) {
            throw new RuntimeException("Got null from TMDB API");
        }
        Movie movie = FullMovie.fromTMDBMovie(fullTMDBMovie);
        movieRepository.save(movie);
        return movie;
    }

    public List<PartialMovie> getLastMovies() throws Exception {
        String lastDate = LocalDate.now().minusDays(7).format(DateTimeFormatter.ISO_LOCAL_DATE);
        var params = Map.of(
                "primary_release_date.gte", lastDate,
                "with_original_language", "en"
        );
        Page<PartialTMDBMovie> movies = tmdbClient.discover(KEY, params).execute().body();
        if (movies == null) {
            throw new RuntimeException("Got null from TMDB API");
        }
        return movies.getResults().stream()
                .filter(movie -> movie.getPosterPath() != null)
                .map(PartialMovie::fromTMDBMovie)
                .collect(Collectors.toList());
    }
}
