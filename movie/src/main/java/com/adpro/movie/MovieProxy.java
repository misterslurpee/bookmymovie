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

    private boolean isValidMovieJson(JSONObject movieJson) {
        return movieJson.has("poster_path") && !movieJson.isNull("poster_path");
    }

    private void updateLastMovies() throws Exception {
        final String relUrl = "/discover/movie";
        String lastDate = LocalDate.now().minusDays(7).format(DateTimeFormatter.ISO_LOCAL_DATE);
        final String query = "primary_release_date.gte=" + lastDate
                + "&with_original_language=en";
        String result = get(relUrl, query);
        JSONObject jsonObject = new JSONObject(result);
        JSONArray moviesJsonArray = jsonObject.getJSONArray("results");

        lastMovies.clear();
        for (int i = 0; i < moviesJsonArray.length(); i++) {
            JSONObject movieJson = moviesJsonArray.getJSONObject(i);
            if (isValidMovieJson(movieJson)) {
                Movie movie = Movie.parseMovie(movieJson);
                lastMovies.add(movie);
                Movie existingMovie = movieRepository.findMovieByName(movie.getName());
                if (existingMovie == null) {
                    movieRepository.save(movie);
                }
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
