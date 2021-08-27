package com.example.lags;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(String id);

    List<Customer> findAll();
}
