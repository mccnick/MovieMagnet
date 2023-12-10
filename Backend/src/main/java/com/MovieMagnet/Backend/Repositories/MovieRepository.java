package com.MovieMagnet.Backend.Repositories;

import com.MovieMagnet.Backend.Classes.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findById(int id);
    Movie findByTitle(String title);

    @Transactional
    void deleteById(int id);

}