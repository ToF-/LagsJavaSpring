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

    public JdbcCustomerRepository() {
        customerRowMapper = new RowMapper<Customer>() {
            public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Customer(rs.getString("Id"), rs.getString("Name"));

            }
        };
    }
    @Override
    public Optional<Customer> findById(String id) {
        List<Customer> result = jdbcTemplate.query("SELECT * FROM CUSTOMERS WHERE Id = ?", customerRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM CUSTOMERS", customerRowMapper);
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
}
