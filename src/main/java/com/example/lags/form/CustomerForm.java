package com.example.lags.form;


import com.example.lags.model.Customer;
import com.example.lags.model.Order;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerForm {
    @NotNull
    @Size(min=2, max=8)
    private String id;

    @NotNull
    @Size(min=2, max=80)
    private String name;

    private List<OrderForm> orderForms;

    public CustomerForm(String id, String name, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.orderForms = new ArrayList<>();
        if(orders != null) {
            for (Order order : orders) {
                this.orderForms.add(new OrderForm(order.getId(),
                        order.getCustomerId(),
                        LocalDate.ofYearDay(order.getStart() / 1000, order.getStart() % 1000),
                        order.getDuration(),
                        order.getPrice()));
            }
        }
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
        List<Order> result = new ArrayList<>();
        for(OrderForm orderForm : orderForms) {
            result.add(new Order(orderForm.getId(),
                    orderForm.getCustomerId(),
                    orderForm.getStart().getDayOfYear() + orderForm.getStart().getYear()*1000,
                    orderForm.getDuration(),
                    orderForm.getPrice()));
        }
        return result;
    }

    public List<OrderForm> getOrderForms() {
        return this.orderForms;
    }
    public Customer getCustomer() {
        return new Customer(getId(), getName(), getOrders());
    }

}
