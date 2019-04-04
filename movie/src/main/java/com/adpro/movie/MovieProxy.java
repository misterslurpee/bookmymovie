package com.adpro.movie;

import com.adpro.movie.tmdb.TMDBClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MovieProxy {

    private static final String BASE_URL_STRING = "https://api.themoviedb.org/3/";
    private static final String KEY = "8d14316a3ad3955af1670a00e39e50ab";

    private List<Movie> lastMovies;
    private LocalDateTime lastUpdate;
    private TMDBClient tmdbClient;

    public MovieProxy() {
        lastMovies = new ArrayList<>();
        lastUpdate = LocalDateTime.MIN;
        tmdbClient = new Retrofit.Builder()
                .baseUrl(BASE_URL_STRING)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(TMDBClient.class);
    }

    private void updateLastMovies() throws Exception {
        String lastDate = LocalDate.now().minusDays(7).format(DateTimeFormatter.ISO_LOCAL_DATE);
        var params = Map.of(
                "primary_release_date.gte", lastDate,
                "with_original_language", "en"
        );
        lastMovies = tmdbClient.discover(KEY, params).execute().body().getResults().stream()
                .filter(movie -> movie.getPosterPath() != null)
                .map(Movie::fromTMDBMovie)
                .collect(Collectors.toList());

        lastUpdate = LocalDateTime.now();
    }

    public List<Movie> getLastMovies() throws Exception {
        if (Duration.between(lastUpdate, LocalDateTime.now()).toMinutes() > 1) {
            updateLastMovies();
        }
        return lastMovies;
    }
}
