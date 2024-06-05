package com.sparta.moviefeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MovieFeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieFeedApplication.class, args);
    }

}
