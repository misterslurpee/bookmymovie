package com.adpro.movie;

import com.adpro.movie.tmdb.TMDBMovie;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Movie implements Serializable {

    private static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/w500";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String posterUrl;

    protected Movie() {}

    public Movie(String name, String description, String posterUrl) {
        this.name = name;
        this.description = description;
        this.posterUrl = posterUrl;
    }

    public static Movie fromTMDBMovie(TMDBMovie movie) {
        String name = movie.getOriginalTitle();
        String description = movie.getOverview();
        String posterUrl = BASE_POSTER_URL + movie.getPosterPath();
        return new Movie(name, description, posterUrl);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
