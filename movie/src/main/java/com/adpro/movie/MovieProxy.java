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
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieProxy {

    private static final String BASE_URL_STRING = "https://api.themoviedb.org/3/";
    private static final String KEY = "8d14316a3ad3955af1670a00e39e50ab";

    @Autowired
    private MovieRepository movieRepository;

    private List<Movie> lastMovies;

    private LocalDateTime lastUpdate;

    private TMDBClient tmdbClient;
    public MovieProxy(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        lastMovies = new ArrayList<>();
        lastUpdate = LocalDateTime.MIN;
        tmdbClient = new Retrofit.Builder()
                .baseUrl(BASE_URL_STRING)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(TMDBClient.class);
    }

    /**
     * Get full JSON movie from the individual movie API.
     * @param movieJson the partial movie JSON
     * @return the Movie object
     */
//    private Movie getMovie(JSONObject movieJson) throws Exception {
//        final String relUrl = "/movie/" + movieJson.getInt("id");
//        JSONObject result = getJson(relUrl, null);
//        return Movie.parseMovie(result);
//    }

    private void updateLastMovies() throws Exception {
//        final String relUrl = "/discover/movie";
//        String nowDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
//        String lastDate = LocalDate.now().minusDays(7).format(DateTimeFormatter.ISO_LOCAL_DATE);
//        final String query = "primary_release_date.gte=" + lastDate
//                + "&primary_release_date.lte=" + nowDate
//                + "&with_original_language=en";
//        JSONObject result = getJson(relUrl, query);
//        JSONArray moviesJsonArray = result.getJSONArray("results");
//
//        lastMovies.clear();
//        for (int i = 0; i < moviesJsonArray.length(); i++) {
//            JSONObject movieJson = moviesJsonArray.getJSONObject(i);
//            if (isValidMovieJson(movieJson)) {
//                String movieName = movieJson.getString("title");
//                Movie movie = movieRepository.findMovieByName(movieName);
//                if (movie == null) {
//                    movie = getMovie(movieJson);
//                    movieRepository.save(movie);
//                }
//                lastMovies.add(movie);
//            }
//        }
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
