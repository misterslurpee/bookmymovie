package com.adpro.movie;

import com.adpro.movie.tmdb.TMDBMovie;
import java.time.LocalDate;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("0")
public class PartialMovie extends Movie {

    private PartialMovie(Long tmdbId, String name, String description, String posterUrl, LocalDate releaseDate) {
        super(tmdbId, name, description, posterUrl, releaseDate);
    }

    public static PartialMovie fromTMDBMovie(TMDBMovie movie) {
        Long tmdbId = movie.getId();
        String name = movie.getOriginalTitle();
        String description = movie.getOverview();
        String posterUrl = BASE_POSTER_URL + movie.getPosterPath();
        LocalDate releaseDate = movie.getReleaseDate();
        return new PartialMovie(tmdbId, name, description, posterUrl, releaseDate);
    }
}
