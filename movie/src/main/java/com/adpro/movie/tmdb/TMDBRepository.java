package com.adpro.movie.tmdb;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Get TMDBMovie from TMDB API.
 */
public class TMDBRepository {
    private static final String BASE_URL_STRING = "https://api.themoviedb.org/3/";
    private static final String KEY = "8d14316a3ad3955af1670a00e39e50ab";

    @NonNull
    private TMDBClient tmdbClient;

    public TMDBRepository() {
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
    public FullTMDBMovie getMovie(Long movieId) throws Exception {
        FullTMDBMovie fullTMDBMovie = tmdbClient.movie(movieId, KEY).execute().body();
        if (fullTMDBMovie == null) {
            throw new RuntimeException("Got null from TMDB API");
        }
        return fullTMDBMovie;
    }

    public List<PartialTMDBMovie> getLastMovies() throws Exception {
        String lastDate = LocalDate.now().minusDays(7).format(DateTimeFormatter.ISO_LOCAL_DATE);
        var params = Map.of(
                "primary_release_date.gte", lastDate,
                "with_original_language", "en"
        );
        Page<PartialTMDBMovie> movies = tmdbClient.discover(KEY, params).execute().body();
        if (movies == null) {
            throw new RuntimeException("Got null from TMDB API");
        }
        return movies.getResults();
    }
}
