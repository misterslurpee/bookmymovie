package com.adpro.movie;

import com.adpro.movie.tmdb.FullTMDBMovie;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie implements Serializable {

    static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/w500";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long id;

    @JsonIgnore
    @NotNull
    private Long tmdbId;

    @NotNull
    private String name;

    @Column(columnDefinition = "TEXT")
    @NotNull
    private String description;

    @NotNull
    private String posterUrl;

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    private Duration duration;

    public static Movie fromTMDBMovie(@NotNull FullTMDBMovie movie) {
        return new MovieBuilder()
                .tmdbId(movie.getId())
                .name(movie.getOriginalTitle())
                .description(movie.getOverview())
                .posterUrl(BASE_POSTER_URL + movie.getPosterPath())
                .releaseDate(movie.getReleaseDate())
                .duration(movie.getDuration())
                .build();
    }

    /**
     * A hackish trick to format duration as HH:MM:SS
     * @return the LocalTime representation of duration
     */
    @JsonProperty("duration")
    public LocalTime getDurationTime() {
        return LocalTime.of(0, 0, 0).plus(duration);
    }

    @JsonIgnore
    public Duration getDuration() {
        return duration;
    }
}
