package com.MovieMagnet.Backend.Controllers;


import com.MovieMagnet.Backend.Classes.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import com.MovieMagnet.Backend.Repositories.ShowtimeRepository;
import com.MovieMagnet.Backend.Repositories.UserRepository;
import com.MovieMagnet.Backend.Repositories.TheaterRepository;

@Tag(name="Theater", description="Controller for Theater information")
@RestController
public class TheaterController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TheaterRepository theaterRepository;
    @Autowired
    ShowtimeRepository showtimeRepository;

    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";

    //---------------------------------//
    //        Theater Controller       //
    //---------------------------------//

    @Operation(summary = "Retrieves all theaters",
            description = "Fetches a list of all theaters from the theaterRepository",
            tags = { "Theater", "GET" })
    @GetMapping("/theaters")
    List<Theater> getAllTheaters() {
        return theaterRepository.findAll();
    }

    @Operation(summary = "Finds a theater by name",
            description = "Looks up a theater in the theaterRepository by its name",
            tags = { "Theater", "GET" })
    @GetMapping("/theaters/search/{theater}")
    Theater getTheaterById(@Parameter(description = "The name of the theater to retrieve") @PathVariable String theater) {
        return theaterRepository.findByName(theater);
    }

    @Operation(summary = "Gets showtimes for a theater",
            description = "Retrieves a list of showtimes for a specific theater, filtered by the user's age group",
            tags = { "Theater", "POST" })
    @PostMapping("/theaters/showtimes/{theater}")
    List<ShowtimeByMovie> getShowtimesByTheater(@Parameter(description = "The name of the theater for which to retrieve showtimes") @PathVariable String theater, @Parameter(description = "The email of the user to apply age group filter for the showtimes") @RequestBody String email) {
        int id = theaterRepository.findByName(theater).getId();
        List<Showtime> showtimes = showtimeRepository.findByTheater_Id(id);
        List<ShowtimeByMovie> output = new ArrayList<>();
        List<String> completed = new ArrayList<>();
        for (Showtime s : showtimes) {
            if (!completed.contains(s.getMovie().getTitle())) {
                ShowtimeByMovie current = new ShowtimeByMovie(s.getMovie().getTitle(), s.getMovie().getRating(), s.getMovie().getRuntime());
                for (Showtime s2 : showtimes) {
                    if (s2.getMovie().getTitle().equals(current.getTitle())) {
                        current.addTime(s2.getTime());
                    }
                }
                output.add(current);
                completed.add(s.getMovie().getTitle());
            }
        }
        String userAgeGroup = userRepository.findByEmail(email).getAgeGroup();
        if (userAgeGroup.equals("R") || userAgeGroup.equals("Admin")) {
            return output;
        } else {
            for (int i = 0; i < output.size(); i++) {
                if (output.get(i).getRating().equals("R")) {
                    output.remove(i);
                    i--;
                } else if (userAgeGroup.equals("PG") && output.get(i).getRating().equals("PG13")) {
                    output.remove(i);
                    i--;
                }
            }
        }
        return output;
    }
/*
    @Operation(summary = "Creates a new theater",
            description = "Adds a new theater to the theaterRepository",
            tags = { "Theater", "POST" })
    @PostMapping("/theaters")
    String createTheater(@Parameter(description = "The Theater object to be created") @RequestBody Theater theater) {
        if (theater == null) {
            return failure;
        }
        theaterRepository.save(theater);
        return success;
    }

    @Operation(summary = "Updates a theater's information",
            description = "Modifies the details of an existing theater in the theaterRepository",
            tags = { "Theater", "PUT" })
    @PutMapping("/theaters/{id}")
    Theater updateTheater(@Parameter(description = "The ID of the theater to update") @PathVariable int id, @Parameter(description = "The Theater object with updated information") @RequestBody Theater request) {
        Theater theater = theaterRepository.findById(id);
        if (theater == null)
            return null;
        theaterRepository.save(request);
        return theaterRepository.findById(id);
    }

    @Operation(summary = "Deletes a theater",
            description = "Removes a theater from the theaterRepository by its ID",
            tags = { "Theater", "DELETE" })
    @DeleteMapping("/theaters/{id}")
    String deleteTheater(@Parameter(description = "The ID of the theater to delete") @PathVariable int id) {
        theaterRepository.deleteById(id);
        return success;
    }
 */
}