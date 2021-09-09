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
//		System.out.print("processing:");
//		System.out.println(order.getId());
		List<Order> compat = new ArrayList<Order>();
		for (Iterator<Order> iter = orders.listIterator(); iter.hasNext();) { // n
			iterationsOrig++;
			Order o = iter.next();
			if (order.getDuration() > 0 && !(o.getStart() <= order.getStart() + order.getDuration())) {
				compat.add(o);
			}
		}
		if (compat.size() > 0) {
//			System.out.print("Next compatible:");
//			System.out.println(compat.get(0).getId());
		}
//		System.out.println(compat.size());
		List<Order> next = new ArrayList<Order>();
		for (int i = 1; i < orders.size(); i++) {// n
			next.add(orders.get(i));
		}
		if (next.size() > 0) {
//			System.out.print("Next compatible:");
//			System.out.println(next.get(0).getId());
		}
		int revenueCompat = order.getPrice() + revenue(compat);// n
		// System.out.println("processing: " + next.size());
		int revenueNext = revenue(next);
		// System.out.println("resolving: " + next.size());
//		System.out.println(order.getId());
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

	public int fastComputeRevenue(List<Order> orders) {
		this.allOrders = orders;
		this.allOrders.add(new Order("SENTINEL", "any", 2100001, 0, 0));
		Collections.sort(orders, new Comparator<Order>() {
			@Override
			public int compare(Order o1, Order o2) {
				return o1.getStart() - o2.getStart();
			}
		});
		int[] revenue = new int[this.allOrders.size()];
		for (int i = 0; i < this.allOrders.size(); i++) {
			revenue[i] = 0;
		}
		for (int i = this.allOrders.size() - 2; i >= 0; i--) {
			int j = i + 1;
			Order order = this.allOrders.get(i);
			int k = nextCompatible(i);
			int revenueCompatible = order.getPrice() + revenue[k];
			int revenueNext = revenue[j];
			revenue[i] = Math.max(revenueCompatible, revenueNext);
		}
		this.allOrders.remove(this.allOrders.size() - 1);
		System.out.println("IterationsCT: " + iterationsCT);
		System.out.println("RevenueCT: " + revenue[0]);
		return revenue[0];
	}

	private int nextCompatible(int j) {
		int l = j + 1;
		int h = this.allOrders.size();
		int m;
		int end = this.allOrders.get(j).getStart() + this.allOrders.get(j).getDuration();
		int result = j;
		while (l <= h) {
			iterationsCT++;
			m = l + (h - l) / 2;
			if (this.allOrders.get(m).getStart() < end)
				l = m + 1;
			else {
				result = m;
				h = m - 1;
			}
		}
        System.out.print("nextCompatible"); System.out.println(result);
		return result;
	}

	public int fastComputeRevenueGP(List<Order> orders) {
		this.allOrders = orders;
		Collections.sort(orders, new Comparator<Order>() {
			public int compare(Order o1, Order o2) {
				return o1.getStart() - o2.getStart();
			}
		});
		this.allOrders.add(new Order("DUMMY", "DUMMY", 9999999, 0, 0));
		int[] revenue = new int[allOrders.size()];
		int[] best = new int[allOrders.size()];
		int[] nextOrder = new int[allOrders.size()];
		nextOrder[allOrders.size() - 1] = allOrders.size() - 1;
		best[allOrders.size() - 1] = allOrders.size() - 1;
		for (int i = allOrders.size() - 2; i >= 0; i--) {
			int j = i + 1;
			Order order = allOrders.get(i);
			int k = nextCompatibleGP(allOrders.get(i), i, allOrders.size() - 1);
			int revenueCompatible = order.getPrice() + revenue[k];
			int revenueNext = revenue[j];

			if (revenueNext > revenueCompatible) {
				revenue[i] = revenueNext;
				best[i] = best[j];
				nextOrder[i] = nextOrder[best[j]];
			} else {
				revenue[i] = revenueCompatible;
				best[i] = i;
				nextOrder[i] = best[k];
			}
		}
		this.allOrders.remove(this.allOrders.size() - 1);

		int greatestIdx = 0;
		for (int i = 0; i < revenue.length; i++) {
			if (revenue[i] > revenue[greatestIdx]) {
				greatestIdx = i;
			}
		}
		System.out.println("*** Best Combination ***");
		int idx = greatestIdx;
		while (idx < orders.size()) {
			System.out.println(idx + " - " + orders.get(idx) + " - " + revenue[idx]);
			idx = nextOrder[idx];
		}
		System.out.println("****************");
		System.out.println("IterationsGP: " + iterationsGP);
		System.out.println("RevenueGP: " + revenue[greatestIdx]);
		return revenue[greatestIdx];
	}

	private int nextCompatibleGP(Order order, int start, int end) {

		int result = end;
		int lastOrderEnd = order.getStart() + order.getDuration();
		int middle;

		while (start < end) {
			iterationsGP++;
			middle = start + (end - start) / 2;
			if (this.allOrders.get(middle).getStart() < lastOrderEnd)
				start = middle + 1;
			else {
				result = middle;
				end = middle;
			}
		}
        System.out.print("nextCompatible"); System.out.println(result);
		return result;
	}

}
