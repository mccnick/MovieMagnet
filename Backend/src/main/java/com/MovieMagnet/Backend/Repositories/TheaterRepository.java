package com.MovieMagnet.Backend.Repositories;

import com.MovieMagnet.Backend.Classes.Movie;
import com.MovieMagnet.Backend.Classes.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TheaterRepository extends JpaRepository<Theater, Long> {

    Theater findById(int Id);

    Theater findByName(String name);

    @Transactional
    void deleteById(int Id);
}
