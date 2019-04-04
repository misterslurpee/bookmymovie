package com.adpro.movie;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class MovieProxy {

    private static final String BASE_URL_STRING = "https://api.themoviedb.org/3";
    private static final String KEY = "8d14316a3ad3955af1670a00e39e50ab";

    private MovieRepository movieRepository;
    private List<Movie> lastMovies;
    private LocalDateTime lastUpdate;

    @Autowired
    public MovieProxy(MovieRepository movieRepository) throws Exception {
        this.movieRepository = movieRepository;
        lastMovies = new ArrayList<>();
        lastUpdate = LocalDateTime.MIN;
    }

    public String get(String relUrlString, String query) throws Exception {
        if (query == null) {
            query = "api_key=" + KEY;
        } else {
            query = query + "&api_key=" + KEY;
        }
        URL url = new URL(BASE_URL_STRING + relUrlString + "?" + query + "&api_key=" + KEY);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public JSONObject getJson(String relUrlString, String query) throws Exception {
        return new JSONObject(get(relUrlString, query));
    }

    private boolean isValidMovieJson(JSONObject movieJson) {
        return movieJson.has("poster_path") && !movieJson.isNull("poster_path");
    }

    /**
     * Get full JSON movie from the individual movie API.
     * @param movieJson the partial movie JSON
     * @return the Movie object
     */
    private Movie getMovie(JSONObject movieJson) throws Exception {
        final String relUrl = "/movie/" + movieJson.getInt("id");
        JSONObject result = getJson(relUrl, null);
        return Movie.parseMovie(result);
    }

    private void updateLastMovies() throws Exception {
        final String relUrl = "/discover/movie";
        String nowDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String lastDate = LocalDate.now().minusDays(7).format(DateTimeFormatter.ISO_LOCAL_DATE);
        final String query = "primary_release_date.gte=" + lastDate
                + "&primary_release_date.lte=" + nowDate
                + "&with_original_language=en";
        JSONObject result = getJson(relUrl, query);
        JSONArray moviesJsonArray = result.getJSONArray("results");

        lastMovies.clear();
        for (int i = 0; i < moviesJsonArray.length(); i++) {
            JSONObject movieJson = moviesJsonArray.getJSONObject(i);
            if (isValidMovieJson(movieJson)) {
                String movieName = movieJson.getString("title");
                Movie movie = movieRepository.findMovieByName(movieName);
                if (movie == null) {
                    movie = getMovie(movieJson);
                    movieRepository.save(movie);
                }
                lastMovies.add(movie);
            }
        }
        lastUpdate = LocalDateTime.now();
    }

    public List<Movie> getLastMovies() throws Exception {
        if (Duration.between(lastUpdate, LocalDateTime.now()).toMinutes() > 1) {
            updateLastMovies();
        }
        return lastMovies;
    }
}
