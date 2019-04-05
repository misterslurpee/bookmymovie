package com.adpro.movie;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface MovieSessionRepository extends CrudRepository<MovieSession, Long> {
    public List<Movie> findMovieSessionsByStartTimeAfter(LocalDateTime localDateTime);
}
