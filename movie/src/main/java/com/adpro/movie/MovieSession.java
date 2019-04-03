package com.adpro.movie;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Movie movie;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    protected MovieSession() {}

    public MovieSession(Movie movie, LocalDateTime startTime, LocalDateTime endTime) {
        this.movie = movie;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
