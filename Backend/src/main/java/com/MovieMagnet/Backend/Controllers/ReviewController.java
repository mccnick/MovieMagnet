package com.MovieMagnet.Backend.Controllers;

import com.MovieMagnet.Backend.Classes.*;
import com.MovieMagnet.Backend.Repositories.ReviewRepository;
import com.MovieMagnet.Backend.Repositories.UserRepository;
import com.MovieMagnet.Backend.Repositories.MovieRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Review", description = "Management APIs for Reviews")
@RestController
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";

    //----------------------------------//
    //          Review Controller       //
    //----------------------------------//

    @Operation(summary = "Add a review",
            description = "Adds a review to the database",
            tags = { "Review", "POST" })
    @PostMapping("/reviews")
    String createReview(@Parameter(description = "The Review object to be added") @RequestBody Review review) {
        if (review == null)
            return failure;

        reviewRepository.save(review); // save method from JPARepository import
        return success;
    }

    @Operation(summary = "Get reviews by movie",
            description = "Returns all reviews for a specific movie",
            tags = { "Review", "GET" })
    @GetMapping("/reviews/movie/{movie}")
    List<Review> getReviewsByMovie(@Parameter(description = "The ID of the movie") @PathVariable String movie) {
        return reviewRepository.findByMovie(movie);
    }

    // Method to check if the user is an admin based on the ageGroup field
    private boolean isAdminUser(String email) {
        User user = userRepository.findByEmail(email);
        return user != null && "Admin".equals(user.getAgeGroup());
    }

    @Operation(summary = "Delete review",
            description = "Deletes the review with the given ID. Only accessible by admin users.",
            tags = { "Review", "DELETE" })
    @DeleteMapping("/reviews/{id}/{email}")
    String deleteReview(@Parameter(description = "The ID of the review to be deleted") @PathVariable int id,
                        @Parameter(description = "Email address of the admin user") @PathVariable String email) {
        if (!isAdminUser(email)) {
            return "Only admin users can delete reviews.";
        }
        reviewRepository.deleteById(id);
        return success;
    }


    @Operation(summary = "Get reviews by user age group",
            description = "Returns reviews visible to the user's age group",
            tags = { "Review", "GET" })
    @GetMapping("/reviews/ageGroup/{email}")
    List<Review> getReviewsByAgeGroup(@Parameter(description = "Email address of the user") @PathVariable String email) {
        User user = userRepository.findByEmail(email);
        String ageGroup = user.getAgeGroup();

        return reviewRepository.findAll().stream()
                .filter(review -> review.getAgeGroup().equals(ageGroup))
                .collect(Collectors.toList());
    }

}
