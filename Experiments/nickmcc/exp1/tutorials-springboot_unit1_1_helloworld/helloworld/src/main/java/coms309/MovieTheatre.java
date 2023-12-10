package coms309;

import java.util.ArrayList;
import java.util.List;

public class MovieTheatre {


        private Long id;
        private String name;
        private String location;
        private String phoneNumber;

        private List<Movie> movies = new ArrayList<>();

        public MovieTheatre() {}

        public MovieTheatre(String name, String location, String phoneNumber) {
            this.name = name;
            this.location = location;
            this.phoneNumber = phoneNumber;
        }

        /*
        Movie Theatre Id getter and setter
         */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*
        Movie Theatre Name getter and setter
         */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*
    Movie Theatre location getter and setter
     */
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /*
    Movie Theatre phone number getter and stter
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /*
    Movie Theatre movie list getter, setter and array list adder
     */
    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
        movie.setMovieTheatre(this);
    }
}

