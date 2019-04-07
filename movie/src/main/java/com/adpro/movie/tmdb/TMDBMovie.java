package com.adpro.movie.tmdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public abstract class TMDBMovie {

    @NotNull
    @JsonProperty("id")
    private Long id;

    @NotNull
    @JsonProperty("original_title")
    private String originalTitle;

    @NotNull
    @JsonProperty("overview")
    private String overview;

    @NotNull
    @JsonProperty("poster_path")
    private String posterPath;

    @NotNull
    @JsonProperty("release_date")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate releaseDate;

}
