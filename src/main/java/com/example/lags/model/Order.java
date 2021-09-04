package com.example.lags.model;

public class Order {
    private final String id;
    private final String customerId;
    private final Integer start;
    private final Integer duration;
    private final Integer price;

    public Order(String id, String customerId, Integer start, Integer duration, Integer price) {
        this.id = id;
        this.customerId = customerId;
        this.start = start;
        this.duration = duration;
        this.price = price;
    }

    public String getId() {
        return this.id;
    }

    public String getCustomerId() { return this.customerId; }

    public Integer getStart() {
        return this.start;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public Integer getPrice() {
        return this.price;
    }
}
