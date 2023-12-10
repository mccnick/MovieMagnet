package com.MovieMagnet.Backend.Repositories;

import com.MovieMagnet.Backend.Classes.Message;
import com.MovieMagnet.Backend.Classes.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>{
    List<Message> findByType(String type);
}
