package com.adpro.movie;

import com.adpro.movie.tmdb.FullTMDBMovie;
import com.adpro.movie.tmdb.TMDBMovie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("1")
public class FullMovie extends Movie {

    @NotNull
    private Duration duration;

    private FullMovie(Long tmdbId, String name, String description, String posterUrl, LocalDate releaseDate, Duration duration) {
        super(tmdbId, name, description, posterUrl, releaseDate);
        this.duration = duration;
    }

    public static FullMovie fromTMDBMovie(@NotNull FullTMDBMovie movie) {
        Long tmdbId = movie.getId();
        String name = movie.getOriginalTitle();
        String description = movie.getOverview();
        String posterUrl = BASE_POSTER_URL + movie.getPosterPath();
        LocalDate releaseDate = movie.getReleaseDate();
        Duration duration = movie.getDuration();
        if (duration == null) {
            duration = Duration.ofMinutes(120);
        }
        return new FullMovie(tmdbId, name, description, posterUrl, releaseDate, duration);
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
