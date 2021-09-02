package com.example.lags;


import org.springframework.context.annotation.Bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CustomerForm {
    @NotNull
    @Size(min=2, max=8)
    private String id;

    @NotNull
    @Size(min=2, max=80)
    private String name;

    private final List<Order> orders;

    public CustomerForm(String id, String name, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.orders = orders;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<Order> getOrders() {
        return this.orders;
    }
    public Customer getCustomer() {
        return new Customer(getId(), getName(), getOrders());
    }

}
