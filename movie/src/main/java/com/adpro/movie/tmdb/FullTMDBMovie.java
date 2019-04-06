package com.adpro.movie.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Duration;
import javax.validation.constraints.NotNull;

public class FullTMDBMovie extends TMDBMovie {
    @NotNull
    private Duration duration;

    public Duration getDuration() {
        return duration;
    }

    @JsonProperty("runtime")
    public void setDuration(Long minDuration) {
        if (minDuration == null) {
            duration = Duration.ofMinutes(120);
        } else {
            duration = Duration.ofMinutes(minDuration);
        }
    }
}
