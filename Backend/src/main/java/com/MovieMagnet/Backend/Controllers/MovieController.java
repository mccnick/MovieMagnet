package com.MovieMagnet.Backend.Controllers;

import com.MovieMagnet.Backend.Classes.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import com.MovieMagnet.Backend.Repositories.MovieRepository;
import com.MovieMagnet.Backend.Repositories.UserRepository;

//import static com.MovieMagnet.Backend.Classes.User.findUser;

@Tag(name="Movie", description="Management APIs for Movies")
@RestController
public class MovieController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MovieRepository movieRepository;

    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";

    //----------------------------------//
    //          Movie Controller        //
    //----------------------------------//

    @Operation(summary = "Get all movies that a user can see",
            description = "Takes the email address sent in the RequestBody to get the user's age group and returns all of the movies that they are able to see.",
            tags = { "Movie", "POST" })
    @PostMapping("/movies/all")
    List<Movie> getMovies(@Parameter(description = "Email address of the user") @RequestBody String email){
        String rating = userRepository.findByEmail(email).getAgeGroup();
        List<Movie> movieList = movieRepository.findAll();
        if (rating.equals("R") || rating.equals("Admin")){
            return movieList;
        } else {
            for (int i = 0; i < movieList.size(); i++) {
                if (movieList.get(i).getRating().equals("R")) {
                    movieList.remove(i);
                    i--;
                } else if (rating.equals("PG") && movieList.get(i).getRating().equals("PG13")) {
                    movieList.remove(i);
                    i--;
                }
            }
            return movieList;
        }
    }

    @Operation(summary = "Search for a specific movie",
            description = "Takes the title of the movie and the email address of the user. Checks for the movie title in the database and returns it only if the user's age group is allowed to see it",
            tags = { "Movie", "POST" })
    @PostMapping("/movies/search/{title}")
    Movie searchMovies(@Parameter(description = "The title of the movie") @PathVariable String title, @Parameter(description = "The email address of the user") @RequestBody String email) {
        Movie movie = movieRepository.findByTitle(title);
        String ageGroup = userRepository.findByEmail(email).getAgeGroup();
        if (ageGroup.equals("R") || ageGroup.equals("Admin")) {
            return movie;
        } else if (ageGroup.equals("PG13") && !movie.getRating().equals("R")) {
            return movie;
        } else if (ageGroup.equals("PG") && movie.getRating().equals("PG")) {
            return movie;
        } else {
            return null;
        }
    }
/*
    @Operation(summary = "Add a movie",
            description = "Adds the movie object in the RequestBody to the database",
            tags = { "Movie", "POST" })
    @PostMapping("/movies")
    String createMovie(@Parameter(description = "The Movie object to be added") @RequestBody Movie movie){
        if (movie == null)
            return failure;
        movieRepository.save(movie);
        return success;
    }
*/
/*
    @Operation(summary = "Remove movie",
            description = "Deletes the movie at the table ID provided in the PathVariable",
            tags = { "Movie", "DELETE" })
    @DeleteMapping("/movies/{id}")
    String deleteMovie(@Parameter(description = "The table ID of the movie to be deleted") @PathVariable int id){
        movieRepository.deleteById(id);
        return success;
    }
 */
}
