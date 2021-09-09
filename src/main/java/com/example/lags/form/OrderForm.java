package com.example.lags.form;


import com.example.lags.model.Order;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class OrderForm {
    private final String customerId;
    @NotNull
    @Size(min=8, max=8)
    private String id;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate start;

    @Min(1)
    private Integer duration;

    @NotNull
    private Integer price;

    public OrderForm(String id, String customerId, LocalDate start, Integer duration, Integer price) {
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
        return new Order(this.getId(), this.getCustomerId(), this.getStart().getDayOfYear() + this.getStart().getYear()*1000, this.getDuration(), this.getPrice());
    }

    public String toString() {
        return String.format("OrderForm id=%s customerId=%s start=%s duration=%s price=%s",
                this.getId(),
                this.getCustomerId(),
                this.getStart().toString(),
                this.getDuration().toString(),
                this.getPrice().toString());
    }
}
