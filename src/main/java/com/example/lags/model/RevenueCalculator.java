package com.example.lags.model;

import com.example.lags.model.Order;

import java.util.*;

public class RevenueCalculator {
    private List<Order> allOrders;

    private Integer revenue(List<Order> orders)
    {
        if (orders.size() == 0)
            return 0;
        Order order = orders.get(0);
        System.out.print("processing:"); System.out.println(order.getId());
        List<Order> compat = new ArrayList<Order>();
        for (Iterator<Order> iter = orders.listIterator(); iter.hasNext(); ) {
            Order o = iter.next();
            if (order.getDuration() > 0 && ! (o.getStart() <= order.getStart() + order.getDuration())) {
                compat.add(o);
            }
        }
        if(compat.size()>0) {
            System.out.print("Next compatible:");System.out.println(compat.get(0).getId());
        }
        System.out.println(compat.size());
        List<Order> next = new ArrayList<Order>();
        for(int i=1; i<orders.size(); i++) {
            next.add(orders.get(i));
        }
        if(next.size()>0) {
            System.out.print("Next compatible:");System.out.println(next.get(0).getId());
        }
        Integer revenueCompat = order.getPrice() + revenue(compat);
        Integer revenueNext = revenue(next);
        System.out.print("resolving "); System.out.println(order.getId());
        return Math.max(revenueCompat, revenueNext);
    }

    public Integer computeRevenue(List<Order> orders)
    {
        this.allOrders = orders;
        Collections.sort(orders, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getStart() - o2.getStart();
            }
        });
        return revenue(allOrders);
    }
    }

