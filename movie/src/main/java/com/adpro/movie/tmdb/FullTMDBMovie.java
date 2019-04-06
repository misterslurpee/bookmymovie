package com.adpro.movie.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Duration;
import org.springframework.lang.Nullable;

public class FullTMDBMovie extends TMDBMovie {
    @Nullable
    private Duration duration;

    public Duration getDuration() {
        return duration;
    }

    @JsonProperty("runtime")
    public void setDuration(Duration duration) {
        if (duration == null) {
            duration = Duration.ofMinutes(120);
        }
        this.duration = duration;
    }
}
