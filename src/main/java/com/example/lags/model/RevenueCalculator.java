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
        List<Order> compat = new ArrayList<Order>();
        for (Iterator<Order> iter = orders.listIterator(); iter.hasNext(); ) {
            Order o = iter.next();
            if (order.getDuration() > 0 && ! (o.getStart() <= order.getStart() + order.getDuration())) {
                compat.add(o);
            }
        }
        System.out.println(compat.size());
        List<Order> next = new ArrayList<Order>();
        for(int i=1; i<orders.size(); i++) {
            next.add(orders.get(i));
        }
        Integer revenueCompat = order.getPrice() + revenue(compat);
        Integer revenueNext = revenue(next);
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
    public Integer fastComputeRevenue(List<Order> orders) {
        this.allOrders = orders;
        this.allOrders.add(new Order("SENTINEL", "any", 2100001, 0, 0));
        Collections.sort(orders, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getStart() - o2.getStart();
            }
        });
        Integer [] revenue = new Integer[this.allOrders.size()];
        for(int i=0; i < this.allOrders.size(); i++) {
            revenue[i] = 0;
        }
        for(int i = this.allOrders.size()-2; i>=0; i--) {
            int j = i+1;
            Order order = this.allOrders.get(i);
            int k = nextCompatible(i);
            Integer revenueCompatible = order.getPrice() + revenue[k];
            Integer revenueNext = revenue[j];
            revenue[i] = Math.max(revenueCompatible, revenueNext);
        }
        this.allOrders.remove(this.allOrders.size()-1);
        return revenue[0];
    }

    private int nextCompatible(int j) {
        int l = j + 1;
        int h = this.allOrders.size();
        int m;
        int end = this.allOrders.get(j).getStart() + this.allOrders.get(j).getDuration();
        int result = j;
        while ( l <= h) {
            m = l + (h - l) / 2;
            if (this.allOrders.get(m).getStart() < end)
                l = m + 1;
            else {
                result = m;
                h = m - 1;
            }
        }
        return result;
    }
}
