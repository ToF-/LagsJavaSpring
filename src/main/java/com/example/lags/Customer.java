package com.example.lags;

public class Customer {
    private String id;

    private String name;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
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
}
