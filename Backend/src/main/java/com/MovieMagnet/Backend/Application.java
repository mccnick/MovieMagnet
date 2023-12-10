package com.MovieMagnet.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import java.io.IOException;

//MovieMagnet Project file

@SpringBootApplication
@EntityScan(basePackages = {"com.MovieMagnet.Backend.Classes"})
@EnableJpaRepositories(basePackages = {"com.MovieMagnet.Backend.Repositories"})
@ComponentScan(basePackages = {"com.MovieMagnet.Backend"})
public class Application {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
    }
}
