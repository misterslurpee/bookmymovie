package com.adpro.movie;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import org.json.JSONObject;

@Entity
public class Movie implements Serializable {

    private static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/w500";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String name;
    private String description;
    private String posterUrl;
    private LocalDate releaseDate;
    private Duration duration;

    protected Movie() {}

    public Movie(String name, String description, String posterUrl, Duration duration) {
        this.name = name;
        this.description = description;
        this.posterUrl = posterUrl;
        this.duration = duration;
    }

    @PrePersist
    protected void onCreate() {
        this.releaseDate = LocalDate.now();
    }

    /**
     * Parse movie from JSON returned by https://api.themoviedb.org/3/
     * @param movieJson
     * @return
     */
    public static Movie parseMovie(JSONObject movieJson) {
        String name = movieJson.getString("original_title");
        String description = movieJson.getString("overview");
        String posterUrl = BASE_POSTER_URL + movieJson.getString("poster_path");
        Duration duration = Duration.ofMinutes(movieJson.getInt("runtime"));
        return new Movie(name, description, posterUrl, duration);
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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
