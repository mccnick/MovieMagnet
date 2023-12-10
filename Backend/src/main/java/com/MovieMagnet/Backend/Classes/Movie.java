package com.MovieMagnet.Backend.Classes;

import com.MovieMagnet.Backend.Repositories.MovieRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {

    @Autowired
    private static MovieRepository movieRepository;

    @Id
    @Schema(description = "Unique identifier of the movie")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "Title of the movie")
    @Column(nullable = false)
    private String title;

    @Schema(description = "Rating of the movie")
    @Column(nullable = false)
    private String rating;

    @Schema(description = "Genre of the movie")
    @Column(nullable = false)
    private String genre;

    @Schema(description = "Runtime duration of the movie")
    @Column(nullable = false)
    private String runtime;

    @Schema(description = "List of showtimes associated with the movie")
    @OneToMany
    @JsonIgnore
    private List<Showtime> showtimeList;

    @Schema(description = "List of theaters where the movie is being shown")
    @ManyToMany
    @Column(nullable = false, name = "theaters")
    private List<Theater> theaterList;

    public Movie(){

    }

    public Movie(String title, String rating, String genre, String runtime){
        this.title = title;
        this.rating = rating;
        this.genre = genre;
        this.runtime = runtime;
        this.showtimeList = new ArrayList<>();
        this.theaterList = new ArrayList<>();
    }

    /*public static Movie findMovie(List<Movie> movieList, String title) {

        Movie movie = null;
        for (Movie m : movieList)
            if (m.getTitle().equals(title)){
                movie = m;
            }
        return movie;
    }*/

    /*
    public static Movie findByTitle(String title) {
        return movieRepository.findByTitle(title);
    }
    */

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getRating(){
        return this.rating;
    }

    public void setRating(String rating){
        this.rating = rating;
    }

    public String getGenre(){
        return this.genre;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public String getRuntime(){
        return this.runtime;
    }

    public void setRuntime(String director){
        this.runtime = runtime;
    }

    public List<Showtime> getShowtimeList() {
        return showtimeList;
    }

    public void setShowtimeList(List<Showtime> showtimeList) {
        this.showtimeList = showtimeList;
    }

    public void addShowtime(Showtime showtime){
        this.showtimeList.add(showtime);
    }

    public List<Theater> getTheaterList() {
        return theaterList;
    }

    public void setTheaterList(List<Theater> theaterList) {
        this.theaterList = theaterList;
    }

    public void addTheater(Theater theater){
        this.theaterList.add(theater);
    }

    public void clearTheaterList() {this.theaterList = new ArrayList<>(); }

    @Override
    public String toString(){
        return title + " " + rating + " " + genre + " " + runtime;
    }
}

