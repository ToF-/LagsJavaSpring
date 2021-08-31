package com.example.lags;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public class OrderForm {
    private final String customerId;
    @NotNull
    @Size(min=8, max=8)
    private String id;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate start;

    @NotNull
    private Integer duration;

    @NotNull
    private Integer price;

    public OrderForm(String id, String customerId, LocalDate start,  Integer duration, Integer price) {
        this.id = id;
        this.customerId = customerId;
        this.start = start;
        this.duration = duration;
        this.price = price;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setStart(LocalDate start) { this.start = start; }
    public void setDuration(Integer duration) { this.duration = duration; }
    public void setPrice(Integer price) { this.price = price; }

    public String getId() { return this.id; }
    public String getCustomerId() { return this.customerId; }
    public LocalDate getStart() { return this.start; }
    public Integer getDuration() { return this.duration; }
    public Integer getPrice() { return this.price; }

    public Order getOrder() {
        return new Order(this.getId(), this.getStart(), this.getDuration(), this.getPrice());
    }
}
