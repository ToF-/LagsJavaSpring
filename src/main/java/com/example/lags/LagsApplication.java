package com.example.lags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
public class LagsApplication {

    @Autowired
    private CustomerRepository repository;
    public static void main(String[] args) {
        SpringApplication.run(LagsApplication.class, args);
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/customer")
    public String showCustomer(@RequestParam(value = "id", defaultValue = "all") String id) {
        if (id.equals("all")) {
            StringBuilder result = new StringBuilder();
            List<Customer> customers = repository.findAll();
            for (int i = 0; i < customers.size(); i++) {
                result.append(customers.get(i).toString());
            }
            return String.valueOf(result);
        }
        else {
            Optional<Customer> customer = repository.findById(id);
            return customer.map(value -> value.toString()).orElse("No customer found with id " + id);
        }
    }

}
