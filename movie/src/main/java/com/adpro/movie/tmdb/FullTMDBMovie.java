package com.adpro.movie.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Duration;
import javax.validation.constraints.NotNull;
import lombok.Getter;

public class FullTMDBMovie extends TMDBMovie {
    @NotNull
    @JsonProperty("runtime")
    @Getter
    private Duration duration;

    public void setDuration(Long minDuration) {
        if (minDuration == null) {
            duration = Duration.ofMinutes(120);
        } else {
            duration = Duration.ofMinutes(minDuration);
        }
    }
}
