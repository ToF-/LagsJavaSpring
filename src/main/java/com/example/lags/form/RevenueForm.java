package com.example.lags.form;

import com.example.lags.model.Order;
import com.example.lags.model.RevenueCalculator;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public List<OrderForm> getOrderForms() {
        List<OrderForm> result = new ArrayList<>();
        for(Order order : this.orders) {
            System.out.println(order.toString());
            result.add(new OrderForm(order.getId(),
                    order.getCustomerId(),
                    LocalDate.ofYearDay(order.getStart()/1000, order.getStart() % 1000),
                    order.getDuration(),
                    order.getPrice()));
        }
        return result;
    }
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
        RevenueCalculator revenueCalculator = new RevenueCalculator();
        this.revenue = revenueCalculator.fastComputeRevenue(orders);
    }
}
