package com.adpro.movie.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Duration;
import javax.validation.constraints.NotNull;
import lombok.Getter;

public class FullTMDBMovie extends TMDBMovie {
    static Duration DEFAULT_DURATION = Duration.ofMinutes(120);

    @NotNull
    @JsonProperty("runtime")
    @Getter
    private Duration duration = DEFAULT_DURATION;

    public void setDuration(Long minDuration) {
        if (minDuration == null) {
            duration = DEFAULT_DURATION;
        } else {
            duration = Duration.ofMinutes(minDuration);
        }
    }
}
