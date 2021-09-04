package com.example.lags;

import com.example.lags.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class LagsApplication {

    @Autowired
    private Repository repository;
    public static void main(String[] args) {
        SpringApplication.run(LagsApplication.class, args);
    }

}
