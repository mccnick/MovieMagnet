package com.MovieMagnet.Backend.Repositories;

import com.MovieMagnet.Backend.Classes.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    List<Showtime> findByTheater_Id(int theater_id);
    Showtime findById(long id);
}
