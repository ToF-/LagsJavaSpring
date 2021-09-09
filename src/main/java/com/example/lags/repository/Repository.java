package com.example.lags.repository;

import com.example.lags.model.Customer;
import com.example.lags.model.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface Repository {
    Optional<Customer> findCustomerById(String id);

    List<Customer> findAllCustomers();

    boolean createCustomer(Customer customer);

    boolean updateCustomer(Customer customer);

    boolean deleteCustomer(String id);

    String createOrder(String id, Order order);

    boolean updateOrder(Order order);

    List<Order> findOrders(LocalDate start, LocalDate end);

    Optional<Order> findOrderById(String orderId);

    boolean deleteOrder(String id);

}
