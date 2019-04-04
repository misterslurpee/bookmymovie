package com.adpro.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
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
    @Column(unique=true)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String posterUrl;
    private LocalDate releaseDate;
    private Duration duration;

    protected Movie() {}

    public Movie(String name, String description, String posterUrl, LocalDate releaseDate, Duration duration) {
        this.name = name;
        this.description = description;
        this.posterUrl = posterUrl;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    /**
     * Parse movie from JSON returned by https://api.themoviedb.org/3/
     * @param movieJson the JSON object from the API
     * @return the Movie object.
     */
    public static Movie parseMovie(JSONObject movieJson) {
        String name = movieJson.getString("original_title");
        String description = movieJson.getString("overview");
        String posterUrl = BASE_POSTER_URL + movieJson.getString("poster_path");
        LocalDate releaseDate = LocalDate.parse(movieJson.getString("release_date"));
        Object intDuration = movieJson.get("runtime");
        if (intDuration == JSONObject.NULL) {
            intDuration = 120;
        }
        Duration duration = Duration.ofMinutes((int)intDuration);
        return new Movie(name, description, posterUrl, releaseDate, duration);
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

    /**
     * A hackish trick to format duration as HH:MM:SS
     * @return the LocalTime representation of duration
     */
    @JsonProperty("duration")
    public LocalTime getDurationTime() {
        return LocalTime.of(0, 0, 0).plus(duration);
    }

    @JsonIgnoreProperties
    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
