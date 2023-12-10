package com.movieMagnet;

import com.movieMagnet.classes.Movie;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.Map;

import com.movieMagnet.classes.User;
import static com.movieMagnet.classes.Encryption.Encrypt;


@RestController
public class ProjectController {

    HashMap<String, User> userList = new HashMap<>();
    HashMap<String, Movie> movieList = new HashMap<>();

    @PostMapping("/test/generate")
    public @ResponseBody String generateTest() {
        User testR = new User("testR", "R", "paSSworD123", "tstrR", "");
        userList.put(testR.getName(), testR);
        User testPG13 = new User("testPG13", "PG13", "paSSworD123", "tstrPG13", "");
        userList.put(testPG13.getName(), testPG13);
        User testPG = new User("testPG", "PG", "paSSworD123", "tstrPG", "");
        userList.put(testPG.getName(), testPG);
        Movie m1 = new Movie("Cars", "PG", "6-9-2006", "John Lasseter");
        movieList.put(m1.getTitle(), m1);
        Movie m2 = new Movie("Shrek", "PG", "5-18-2001", "Vicky Jenson");
        movieList.put(m2.getTitle(), m2);
        Movie m3 = new Movie("Velocipastor", "NA", "8-13-2019", "Brendan Steere");
        movieList.put(m3.getTitle(), m3);
        Movie m4 = new Movie("Barbie", "PG13", "7-21-2023", "Greta Gerwig");
        movieList.put(m4.getTitle(), m4);
        Movie m5 = new Movie("Oppenheimer", "R", "7-21-2023", "Christopher Nolan");
        movieList.put(m5.getTitle(), m5);
        return "Test data has been generated.";
    }


    //---------------------------------//
    //          User Controller        //
    //---------------------------------//

    @GetMapping("/user")
    public @ResponseBody HashMap<String,User> getAllPersons() {
        return userList;
    }

    @GetMapping("/user/{name}")
    public @ResponseBody User getUser(@PathVariable String name) {
        return userList.get(name);
    }

    @PostMapping("/user/{name}")
    public @ResponseBody String createPerson(@PathVariable String name) {
        User user = new User(name);
        userList.put(user.getName(), user);
        return "New person "+ user.getName() + " Saved";
    }

    @DeleteMapping("/user/{name}")
    public @ResponseBody HashMap<String, User> deleteUser(@PathVariable String name) {
        userList.remove(name);
        return userList;
    }


    //---------- Passwords and Authentication ----------//

    @GetMapping("/user/{name}/password")
    public @ResponseBody String getPassword(@PathVariable String name) {
        User u = userList.get(name);
        return u.getPassword();
    }

    @GetMapping("/user/{name}/auth")
    public @ResponseBody String authenicatePassword(@PathVariable String name, @RequestBody String password) {

        User user = userList.get(name);
        String userHash = user.getPassword();
        if (userHash.equals(Encrypt(password))) {

            return "User authenticated";
        }
        else {

            return "Incorrect password";
        }
    }

    @PutMapping("/user/{name}/{password}")
    public @ResponseBody String setPassword(@PathVariable String name, @PathVariable String password) {
        User user = userList.get(name);
        user.setPassword(password);
        return "Password encryption complete";
    }


    //----------------------------------//
    //          Movie Controller        //
    //----------------------------------//

    @GetMapping("/movie")
    public @ResponseBody HashMap<String,Movie> getAllMovies() {

        return movieList;
    }

    @GetMapping("/movie/retrieve/{user}")
    public @ResponseBody HashMap<String,Movie> getMovies(@PathVariable String user) {
        HashMap<String, Movie> tempMap = new HashMap<>();
        String rating = userList.get(user).getAgeGroup();

        if (rating.equals("R")) {
            return getAllMovies();
        }

        for(Map.Entry<String,Movie> entry : movieList.entrySet()) {
            Movie current = movieList.get(entry.getKey());

            if (current.getRating().equals("PG")) {
                tempMap.put(current.getTitle(), current);

            } else if (current.getRating().equals(rating)) {
                tempMap.put(current.getTitle(), current);
            }
        }

        return tempMap;
    }

    @GetMapping("/movie/get/{name}/{title}")
    public @ResponseBody Movie getMovie(@PathVariable String name, @PathVariable String title) {
        Movie movie = movieList.get(title);
        User user = userList.get(name);
        String rating = movie.getRating();

        if (user.getAgeGroup().equals("R")) {
            return movie;
        } else if (user.getAgeGroup().equals("PG") && movie.getRating().equals("PG")) {
            return movie;
        } else if (user.getAgeGroup().equals("PG13") && !rating.equals("R")) {
            return movie;
        } else {
            throw new RuntimeException("User can't access this film.");
        }
    }

    @PostMapping("/movie")
    public @ResponseBody String createMovie(@RequestBody Movie movie) {
        System.out.println(movie);
        movieList.put(movie.getTitle(), movie);
        return "New movie "+ movie.getTitle() + " Saved";
    }

    @PostMapping("/movie/add/{title}/{rating}/{releaseDate}/{director}")
    public @ResponseBody String addMovie(@PathVariable String title, @PathVariable String rating, @PathVariable String releaseDate, @PathVariable String director) {
        Movie newMovie = new Movie(title, rating, releaseDate, director);
        movieList.put(newMovie.getTitle(), newMovie);
        return title + " has been added.";
    }

    @DeleteMapping("/movie/{title}")
    public @ResponseBody HashMap<String, Movie> deleteMovie(@PathVariable String title) {
        movieList.remove(title);
        return movieList;
    }
}
