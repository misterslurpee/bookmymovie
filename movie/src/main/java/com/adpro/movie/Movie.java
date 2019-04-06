package com.adpro.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import javax.persistence.Column;
import com.adpro.movie.tmdb.TMDBMovie;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Movie implements Serializable {

    private static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/w500";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @Column(columnDefinition = "TEXT")
    @NotNull
    private String description;

    private String posterUrl;

    private LocalDate releaseDate;

    @NotNull
    private Duration duration;

    protected Movie() {}

    private Movie(String name, String description, String posterUrl, LocalDate releaseDate, Duration duration) {
        this.name = name;
        this.description = description;
        this.posterUrl = posterUrl;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public static Movie fromTMDBMovie(TMDBMovie movie) {
        String name = movie.getOriginalTitle();
        String description = movie.getOverview();
        String posterUrl = BASE_POSTER_URL + movie.getPosterPath();
        LocalDate releaseDate = movie.getReleaseDate();
        Duration duration = movie.getDuration().orElse(Duration.ofMinutes(120));
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
