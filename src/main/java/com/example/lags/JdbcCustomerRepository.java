package com.example.lags;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Customer> customerRowMapper;
    private RowMapper<Order> orderRowMapper;

    public JdbcCustomerRepository() {
        customerRowMapper = new RowMapper<Customer>() {
            public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Customer(rs.getString("Id"), rs.getString("Name"), null);
            }
        };
        orderRowMapper = new RowMapper<Order>() {
            @Override
            public Order mapRow(ResultSet rs, int i) throws SQLException {
                return new Order(rs.getString("Id"),rs.getDate("Start"),rs.getInt("Duration"), rs.getInt("Price") );
            }
        };
    }
    @Override
    public Optional<Customer> findById(String id) {
        List<Customer> result = jdbcTemplate.query("SELECT * FROM CUSTOMERS WHERE Id = ?", customerRowMapper, id);
        if(!result.isEmpty()) {
            String customerId = result.get(0).getId();
            List<Order> orders = jdbcTemplate.query("SELECT Id,Start,Duration,Price FROM ORDERS WHERE CustomerId = ?", orderRowMapper, customerId);
            Customer customer = new Customer(customerId, result.get(0).getName(), orders);
            return Optional.of(customer);
        }
        else
            return Optional.empty();
    }
    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM CUSTOMERS ORDER BY Id", customerRowMapper);
    }

    @Override
    public boolean create(Customer customer) {
        try {
            jdbcTemplate.update("INSERT INTO CUSTOMERS (Id, Name) VALUES (?, ?)", customer.getId(), customer.getName());
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Customer customer) {
        try {
            jdbcTemplate.update("UPDATE CUSTOMERS SET Name = ? WHERE Id = ?", customer.getName(), customer.getId());
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(String id) {
        try {
            jdbcTemplate.update("DELETE FROM CUSTOMERS WHERE Id = ?", id);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
}
