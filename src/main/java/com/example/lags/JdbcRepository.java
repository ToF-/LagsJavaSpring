package com.example.lags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public class JdbcRepository implements Repository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final RowMapper<Customer> customerRowMapper;
    private final RowMapper<Order> orderRowMapper;

    public JdbcRepository() {
        customerRowMapper = (rs, rowNum) -> new Customer(rs.getString("Id"), rs.getString("Name"), null);
        orderRowMapper = (rs, i) -> new Order(rs.getString("Id"),rs.getDate("Start").toLocalDate(),rs.getInt("Duration"), rs.getInt("Price") );
    }
    @Override
    public Optional<Customer> findCustomerById(String id) {
        List<Customer> result = jdbcTemplate.query("SELECT * FROM CUSTOMERS WHERE Id = ?", customerRowMapper, id);
        if(!result.isEmpty()) {
            String customerId = result.get(0).getId();
            List<Order> orders = jdbcTemplate.query("SELECT Id,Start,Duration,Price FROM ORDERS WHERE CustomerId = ? ORDER BY Start", orderRowMapper, customerId);
            Customer customer = new Customer(customerId, result.get(0).getName(), orders);
            return Optional.of(customer);
        }
        else
            return Optional.empty();
    }
    @Override
    public List<Customer> findAllCustomers() {
        return jdbcTemplate.query("SELECT * FROM CUSTOMERS ORDER BY Id", customerRowMapper);
    }

    @Override
    public boolean createCustomer(Customer customer) {
        try {
            jdbcTemplate.update("INSERT INTO CUSTOMERS (Id, Name) VALUES (?, ?)", customer.getId(), customer.getName());
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        try {
            jdbcTemplate.update("UPDATE CUSTOMERS SET Name = ? WHERE Id = ?", customer.getName(), customer.getId());
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteCustomer(String id) {
        try {
            jdbcTemplate.update("DELETE FROM CUSTOMERS WHERE Id = ?", id);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public String createOrder(String id, Order order) {
        try {
            jdbcTemplate.update("INSERT INTO ORDERS (Id, CustomerId, Start, Duration, Price) VALUES (?, ?, ?, ?, ?)",
                    order.getId(), id, Date.valueOf(order.getStart()), order.getDuration(), order.getPrice());
        }
        catch (Exception e) {
            return String.format("problem with Customer %s new Order : %s", id, e.getMessage());
        }
        return "";
    }

    @Override
    public List<Order> findOrders(LocalDate start, LocalDate end) {
        List<Order> result = jdbcTemplate.query("SELECT Id, CustomerId, Start, Duration, Price FROM ORDERS WHERE Start >= ? AND (Start + interval '1' day * duration) <= ? ORDER BY Start",
                orderRowMapper,
                Date.valueOf(start),
                Date.valueOf(end));
        return result;
    }
}
