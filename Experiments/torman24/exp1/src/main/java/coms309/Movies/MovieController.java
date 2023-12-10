package coms309.Movies;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.HashMap;

@RestController
public class MovieController {

    HashMap<String, Movie> movieList = new HashMap<>();
    HashMap<String, Movie> movieListPG13 = new HashMap<>();
    HashMap<String, Movie> movieListPG = new HashMap<>();

    @GetMapping("/movie")
    public @ResponseBody HashMap<String,Movie> getAllMovies() {

        return movieList;
    }

    @GetMapping("/movie/PG13")
    public @ResponseBody HashMap<String,Movie> getPG13Movies() {

        return movieListPG13;
    }

    @GetMapping("/movie/PG")
    public @ResponseBody HashMap<String,Movie> getPGMovies() {

        return movieListPG;
    }

    @PostMapping("/movie/generate")
    public @ResponseBody String generateMovies() {
        Movie m1 = new Movie("Cars", "PG", "6-9-2006", "John Lasseter");
        movieList.put(m1.getTitle(), m1);
        movieListPG.put(m1.getTitle(), m1);
        movieListPG13.put(m1.getTitle(), m1);
        Movie m2 = new Movie("Shrek", "PG", "5-18-2001", "Vicky Jenson");
        movieList.put(m2.getTitle(), m2);
        movieListPG.put(m2.getTitle(), m2);
        movieListPG13.put(m2.getTitle(), m2);
        Movie m3 = new Movie("Velocipastor", "NA", "8-13-2019", "Brendan Steere");
        movieList.put(m3.getTitle(), m3);
        Movie m4 = new Movie("Barbie", "PG-13", "7-21-2023", "Greta Gerwig");
        movieList.put(m4.getTitle(), m4);
        movieListPG13.put(m4.getTitle(), m4);
        Movie m5 = new Movie("Oppenheimer", "R", "7-21-2023", "Christopher Nolan");
        movieList.put(m5.getTitle(), m5);
        return "Movie data has been generated.";
    }


    // Add movie to the database

    @PostMapping("/movie/add/{title}/{rating}/{releaseDate}/{director}")
    public @ResponseBody String addMovie(@PathVariable String title, @PathVariable String rating, @PathVariable String releaseDate, @PathVariable String director) {
        Movie newMovie = new Movie(title, rating, releaseDate, director);
        if (newMovie.getRating().equals("PG")) {
            movieListPG.put(newMovie.getTitle(), newMovie);
            movieListPG13.put(newMovie.getTitle(), newMovie);

        } else if (newMovie.getRating().equals("PG13")) {
            movieListPG13.put(newMovie.getTitle(), newMovie);
        }
        movieList.put(newMovie.getTitle(), newMovie);
        return title + " has been added.";
    }


    // Get movie from database
    @GetMapping("/movie/{title}")
    public @ResponseBody Movie getMovie(@PathVariable String title) {
        Movie p = movieList.get(title);
        return p;
    }


    // Edit movie in database
    @PutMapping("/movie/{title}")
    public @ResponseBody Movie updateMovie(@PathVariable String title, @RequestBody Movie p) {
        movieList.replace(title, p);
        return movieList.get(title);
    }


    // Delete movie from database
    @DeleteMapping("/movie/{title}")
    public @ResponseBody HashMap<String, Movie> deleteMovie(@PathVariable String title) {
        movieList.remove(title);
        return movieList;
    }
    @GetMapping("movie/remove/{title}")
    public @ResponseBody String removeMovie(@PathVariable String title) {
        movieList.remove(title);
        return title + " has been removed.";
    }
}

