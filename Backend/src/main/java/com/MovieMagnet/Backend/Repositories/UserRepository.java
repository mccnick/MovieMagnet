package com.MovieMagnet.Backend.Repositories;

import com.MovieMagnet.Backend.Classes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findById(int Id);

    User findByName(String name);

    User findByEmail(String email);

    @Transactional
    void deleteById(int Id);

}


