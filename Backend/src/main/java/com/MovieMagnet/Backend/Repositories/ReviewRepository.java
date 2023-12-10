package com.MovieMagnet.Backend.Repositories;

import com.MovieMagnet.Backend.Classes.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Find reviews by movie ID
    List<Review> findByMovieId(int movieId);

    List<Review> findByMovie(String movie);

    // Find reviews by user's age group
    List<Review> findByAgeGroup(String ageGroup);

    // Delete a review by its ID
    @Transactional
    void deleteById(int id);
}

