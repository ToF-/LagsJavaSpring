package com.example.lags.model;

import java.util.List;

public class Customer {
    private final String id;

    private final String name;

    private final List<Order> orders;

    public Customer(String id, String name, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.orders = orders;
    }

    public String toString() {
        return "Customer [id=" + id + ", name=" + name + "]";
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
}
