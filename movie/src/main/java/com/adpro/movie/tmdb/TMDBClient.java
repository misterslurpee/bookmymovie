package com.adpro.movie.tmdb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface TMDBClient {
    @GET("discover/movie")
    Call<Page<TMDBMovie>> discover(@Query("api_key") String apiKey, @QueryMap Map<String, String> params);
}