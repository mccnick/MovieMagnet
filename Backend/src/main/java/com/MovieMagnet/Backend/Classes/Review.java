package com.MovieMagnet.Backend.Classes;

import java.util.Date;

import javax.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Entity
@Table(name = "reviews")
@Data
public class Review {

    @Column(name = "movie", nullable = false)
    private String movie;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "movie_id")
    private int movieId;

    @Column
    private int rating;

    @Lob
    private String comment;

    @Column(name = "age_group")
    private String ageGroup;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt = new Date();

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }
}

