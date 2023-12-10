package coms309;

import java.util.List;

public class Movie {
    private Long id;
    private String title;
    private List<String> showtimes;
    private MovieTheatre movieTheatre;  // reference to the MovieTheatre class

    /*
    empty constructor initialized
     */
    public Movie() {}


    public Movie(String title, List<String> showtimes) {
        this.title = title;
        this.showtimes = showtimes;
    }

    /*
    Movie Id getter and setter
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*
    Movie Title getter and setter
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public List<String> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<String> showtimes) {
        this.showtimes = showtimes;
    }

    /*
    Movie Theatre getter and setter
     */
    public MovieTheatre getMovieTheatre() {
        return movieTheatre;
    }

    public void setMovieTheatre(MovieTheatre movieTheatre) {
        this.movieTheatre = movieTheatre;
    }
}
