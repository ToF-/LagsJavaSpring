package com.example.lags;

import java.sql.Date;

public class Order {
    private final String id;
    private final Date start;
    private final Integer duration;
    private final Integer price;

    public Order(String id, Date start, Integer duration, Integer price) {
        this.id = id;
        this.start = start;
        this.duration = duration;
        this.price = price;
    }

    public String getId() {
        return this.id;
    }

    public Date getStart() {
        return this.start;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public Integer getPrice() {
        return this.price;
    }
}
