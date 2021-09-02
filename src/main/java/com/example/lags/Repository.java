package com.example.lags;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Repository {
    Optional<Customer> findCustomerById(String id);

    List<Customer> findAllCustomers();

    boolean createCustomer(Customer customer);

    boolean updateCustomer(Customer customer);

    boolean deleteCustomer(String id);

    String createOrder(String id, Order order);
}
