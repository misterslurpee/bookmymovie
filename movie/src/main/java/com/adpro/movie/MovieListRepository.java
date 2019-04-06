package com.adpro.movie;

import java.time.LocalDate;
import java.util.List;

public interface MovieListRepository {
    List<Movie> findMoviesByReleaseDateAfter(LocalDate date);
}
