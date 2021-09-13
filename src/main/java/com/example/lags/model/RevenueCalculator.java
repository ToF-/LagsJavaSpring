package com.example.lags.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class RevenueCalculator {
	private List<Order> allOrders;

	private int iterationsOrig = 0;
	private int iterationsCT = 0;
	private int iterationsGP = 0;

	private int revenue(List<Order> orders) {
		if (orders.size() == 0)
			return 0;
		Order order = orders.get(0);
		List<Order> compat = new ArrayList<Order>();
		for (Iterator<Order> iter = orders.listIterator(); iter.hasNext();) {
			iterationsOrig++;
			Order o = iter.next();
			if (order.getDuration() > 0 && !(o.getStart() <= order.getStart() + order.getDuration())) {
				compat.add(o);
			}
		}
		List<Order> next = new ArrayList<Order>();
		for (int i = 1; i < orders.size(); i++) {
			next.add(orders.get(i));
		}
		int revenueCompat = order.getPrice() + revenue(compat);
		int revenueNext = revenue(next);
		return Math.max(revenueCompat, revenueNext);
	}

	public int computeRevenue(List<Order> orders) {
		this.allOrders = orders;
		Collections.sort(orders, new Comparator<Order>() {
			@Override
			public int compare(Order o1, Order o2) {
				return o1.getStart() - o2.getStart();
			}
		});
		int revenueCalculated = revenue(allOrders);
		System.out.println("IterationsOrig: " + iterationsOrig);
		System.out.println("RevenueOrig: " + revenueCalculated);
		return revenueCalculated;
	}

}
