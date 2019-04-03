package com.adpro.movie;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.json.JSONObject;

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

    public static Movie parseMovie(JSONObject movieJson) {
        String name = movieJson.getString("original_title");
        String description = movieJson.getString("overview");
        String posterUrl = BASE_POSTER_URL + movieJson.getString("poster_path");
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
