package com.example.lags;

import java.time.LocalDate;
import java.util.List;

public class RevenueForm {
    private LocalDate start;
    private LocalDate end;
    private Integer revenue;
    private List<Order> orders;

    public RevenueForm(LocalDate start, LocalDate end, List<Order> orders) {
        this.start = start;
        this.end = end;
        this.revenue = 0;
        this.orders = orders;
    }

    public LocalDate getStart() { return this.start; }
    public LocalDate getEnd() { return this.end; }

    public List<Order> getOrders() { return this.orders; }
    public Integer getRevenue() {
        return revenue;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public void computeRevenue() {
        this.revenue = 0;
    }
}
