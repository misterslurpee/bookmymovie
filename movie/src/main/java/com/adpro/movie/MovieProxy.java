package com.adpro.movie;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.json.BasicJsonParser;

public class MovieProxy {

    private static String baseUrlString = "https://api.themoviedb.org/3";
    private static String key = "8d14316a3ad3955af1670a00e39e50ab";

    public String get(String relUrlString, String query) throws Exception {
        URL url = new URL(baseUrlString + relUrlString + "?" + query + "api_key=" + key);

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

    public List<Movie> getLastMovies() throws Exception {
        final String relUrl = "/discover/movie";
        String lastDate = LocalDate.now().minusDays(7).format(DateTimeFormatter.ISO_LOCAL_DATE);
        final String query = "primary_release_date.gte=" + lastDate;
        String result = get(relUrl, query);
        JSONObject jsonObject = new JSONObject(result);
        JSONArray moviesJsonArray = jsonObject.getJSONArray("results");

        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < moviesJsonArray.length(); i++) {
            JSONObject movieJson = moviesJsonArray.getJSONObject(i);
            movies.add(Movie.parseMovie(movieJson));
        }
    }
}
