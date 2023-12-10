package com.MovieMagnet.Backend.Controllers;


import com.MovieMagnet.Backend.Classes.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import com.MovieMagnet.Backend.Repositories.ShowtimeRepository;
import com.MovieMagnet.Backend.Repositories.MovieRepository;
import com.MovieMagnet.Backend.Repositories.UserRepository;
import com.MovieMagnet.Backend.Repositories.TheaterRepository;

import static com.MovieMagnet.Backend.Refresh.*;

@Tag(name="Database", description="Management APIs for the database tables")
@RestController
public class DatabaseController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    TheaterRepository theaterRepository;
    @Autowired
    ShowtimeRepository showtimeRepository;

    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";

    //---------------------------------//
    //          DB Controller          //
    //---------------------------------//
/*
    @Operation(summary = "Clear the 'users' table",
            description = "Deletes all of the items in the 'users' table",
            tags = { "Database", "DELETE" })
    @DeleteMapping("/clear/users")
    String clearUserDB() {
        userRepository.deleteAll();
        return success;
    }

    @Operation(summary = "Clear the 'movies' table",
            description = "Deletes all of the items in the 'movies' table",
            tags = { "Database", "DELETE" })
    @DeleteMapping("/clear/movies")
    String clearMovieDB() {
        movieRepository.deleteAll();
        return success;
    }

    @Operation(summary = "Clear the 'theaters' table",
            description = "Deletes all of the items in the 'theaters' table",
            tags = { "Database", "DELETE" })
    @DeleteMapping("/clear/theaters")
    String clearTheaters() {
        theaterRepository.deleteAll();
        return success;
    }

    @Operation(summary = "Clear the 'showtimes' table",
            description = "Deletes all of the items in the 'showtimes' table",
            tags = { "Database", "DELETE" })
    @DeleteMapping("/clear/showtimes")
    String clearShowtimes() {
        showtimeRepository.deleteAll();
        return success;
    }

    @Operation(summary = "Clear the 'users', 'theaters', and 'movies' tables",
            description = "Deletes all of the items in the 'users', 'theaters', and 'movies' tables",
            tags = { "Database", "DELETE" })
    @DeleteMapping("/clear/all")
    String clearDB() {
        userRepository.deleteAll();
        movieRepository.deleteAll();
        theaterRepository.deleteAll();
        return success;
    }

    @Operation(summary = "Initialize a basic set of users",
            description = "Create users for each of the three age groups and the Admin user",
            tags = { "Database", "POST" })
    @PostMapping("/init/users")
    String initUserDB() {

        User adminUser = new User("Admin", "admin", "admin", "admin@moviemagnet.com");
        User pgUser = new User("PG", "01/01/2023", "pg", "pg@moviemagnet.com");
        User pg13User = new User("PG13", "01/01/2010", "pg13", "pg13@moviemagnet.com");
        User rUser = new User("R", "01/01/2000", "r", "r@moviemagnet.com");

        userRepository.save(adminUser);
        userRepository.save(pgUser);
        userRepository.save(pg13User);
        userRepository.save(rUser);

        return success;
    }

    @Operation(summary = "Initialize the 'movie' table",
            description = "Scrape the currently showing movies and add them to the 'movie' table. This is not needed in normal use cases.",
            tags = { "Database", "POST" })
    @PostMapping("/init/movies")
    String initMovieDB() {
        try {
            List<Movie> movieList = movieRequest();
            for (Movie m : movieList) {
                movieRepository.save(m);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return success;
    }

    @Operation(summary = "Initialize the 'theater' table",
            description = "Scrap the web and add the nearby theaters to the 'theater' database. This is not needed in normal use cases.",
            tags = { "Database", "POST" })
    @PostMapping("/init/theaters")
    String initTheaterDB() {
        try {
            for (Theater t : theaterRequest()) {
                theaterRepository.save(t);
            }
            return success;
        } catch (IOException ex) {
            System.out.println(ex);
            return failure;
        }
    }
 */
    @Operation(summary = "Refresh the movies and showtimes in the database",
            description = "Scrape the web and gather all of the currently showing movies and their showtimes. Adds these showtimes to the database and establishes the internal DB relations.",
            tags = { "Database", "POST" })
    @PostMapping("/refresh")
    String refreshDB() {
        try {
            //refresh movies
            for (Movie m : movieRequest()) {
                if (movieRepository.findByTitle(m.getTitle()) == null) {
                    movieRepository.save(m);
                }
                m.clearTheaterList();
            }

            //refresh theaters
            for (Theater t : theaterRequest()) {
                if (theaterRepository.findByName(t.getName()) == null) {
                    theaterRepository.save(t);
                }
                t.clearMovieList();
            }

            //create showtimes and populate movie/theater lists
            showtimeRepository.deleteAll();
            for (String s : RefreshTheaters()) {
                String[] info = s.split("     ");
                Showtime showtime = new Showtime(theaterRepository.findByName(info[0]), movieRepository.findByTitle(info[1]), info[2]);
                if (!(showtime.getMovie() == null)) {
                    showtimeRepository.save(showtime);
                    Theater theater = theaterRepository.findByName(showtime.getTheater().getName());
                    Movie movie = movieRepository.findByTitle(showtime.getMovie().getTitle());

                    if (!theater.getMovieList().contains(movie)) {
                        theater.addMovie(movie);
                    }
                    if (!movie.getTheaterList().contains(theater)) {
                        movie.addTheater(theater);
                    }

                    theaterRepository.save(theater);
                    movieRepository.save(movie);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex);
            return failure;
        }

        for (Movie m : movieRepository.findAll()) {
            if (m.getTheaterList().isEmpty()) {
                movieRepository.deleteById(m.getId());
            }
        }

        return success;
    }
}