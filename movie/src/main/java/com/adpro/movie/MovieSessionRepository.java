package com.adpro.movie;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface MovieSessionRepository extends CrudRepository<MovieSession, Long> {
    List<MovieSession> findMovieSessionsByStartTimeAfter(LocalDateTime localDateTime);
    List<MovieSession> findMovieSessionsByMovieIdAndStartTimeAfter(Long movieId, LocalDateTime dateTime);
    List<MovieSession> findMovieSessionsByMovieId(Long movieId);
}
