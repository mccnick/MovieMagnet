package com.MovieMagnet.Backend.Classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

@Entity
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "The theater showing the movie")
    @ManyToOne
    @JoinColumn(nullable = false, name = "theater_id")
    @JsonIgnore
    private Theater theater;

    @Schema(description = "The movie being shown")
    @ManyToOne
    @JoinColumn(nullable = false, name = "movie_id")
    @JsonIgnore
    private Movie movie;

    @Schema(description = "The time of the showing")
    @Column(nullable = false)
    private String time;

    public Showtime(Theater theater, Movie movie, String time) {
        this.theater = theater;
        this.movie = movie;
        this.time = time;
    }

    public Showtime() {}

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    public Theater getTheater() { return this.theater; }
    public void setId(Theater theater) { this.theater = theater; }

    public Movie getMovie() { return this.movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    public String getTime() { return this.time; }
    public void setTime(String time) { this.time = time; }

    public String toString() {
        return theater.getName() + " " + movie.getTitle() + " " + time;
    }

}
