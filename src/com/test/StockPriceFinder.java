package com.test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.test.BinaryTree.NodeVisitor;

public class StockPriceFinder {

	public static void main(String[] args) throws ParseException {
		final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Map<Date, BigDecimal> input = new HashMap<Date, BigDecimal> (){
			private static final long serialVersionUID = 1L;
			{
				try {
					put(format.parse("12/31/2012"), new BigDecimal(31));
					put(format.parse("01/01/2013"), new BigDecimal(100));
					put(format.parse("01/02/2013"), new BigDecimal(30));
					put(format.parse("01/03/2013"), new BigDecimal(60));
					put(format.parse("01/04/2013"), new BigDecimal(150));
					put(format.parse("01/05/2013"), new BigDecimal(45));
					put(format.parse("01/06/2013"), new BigDecimal(225));
					put(format.parse("01/07/2013"), new BigDecimal(250));
					put(format.parse("01/08/2013"), new BigDecimal(25));
					put(format.parse("01/09/2013"), new BigDecimal(20));
					put(format.parse("01/10/2013"), new BigDecimal(10));
					put(format.parse("01/11/2013"), new BigDecimal(5));
					put(format.parse("01/12/2013"), new BigDecimal(15));
					put(format.parse("01/13/2013"), new BigDecimal(5));
					put(format.parse("01/14/2013"), new BigDecimal(5));
				} catch (Exception e) {
					
				}
			}
		};
		long tStart = System.currentTimeMillis();
		Date[] dates = input.keySet().toArray(new Date[0]);
		Arrays.sort(dates);
		BigDecimal lowPrice = new BigDecimal(1000);
		BigDecimal highPrice = BigDecimal.ZERO;
		
		Stack<Date> lowPrices = new Stack<>();
		Stack<Date> highPrices = new Stack<>();
		
		for(Date date : dates) {
			BigDecimal price = input.get(date);
			if (price.compareTo(lowPrice) < 0) {
				lowPrice = price;
				lowPrices.push(date);
			}
			if (price.compareTo(highPrice) > 0) {
				highPrice = price;
				highPrices.push(date);
			}
		}
		while (lowPrices.peek().after(highPrices.peek())) {
			lowPrices.pop();
		}
		System.out.println("low price date (Buy Date) " + lowPrices.pop() + "\nhigh price date (Sell Date) " + highPrices.pop());
		System.out.println("Total time = " + (System.currentTimeMillis() - tStart));
		System.out.println("-------------------------------------------------");
		
		tStart = System.currentTimeMillis();
		highLowGraph(input);
		System.out.println("Total time for tree = " + (System.currentTimeMillis() - tStart));
	}
	
	public static void highLowGraph(Map<Date, BigDecimal> input) {
		BinaryTree<Stock> tree = new BinaryTree<Stock>();
		for(Date date : input.keySet()) {
			tree.insert(new Stock(date, input.get(date)));
		}

		final Stock max = tree.findMax();
		System.out.println("Max = " + max);
		NodeVisitor<Stock> stack = new NodeVisitor<Stock>() {
			@Override
			public boolean visit(Stock t) {
				if (max.date.after(t.date)) {
					System.out.println("Min = " + t);
					return true;
				}
				return false;
			}
		};
		tree.inorder(stack);
	}
	
	public static class Stock implements Comparable<Stock> {
		private Date date;
		private BigDecimal price;
		
		public Stock(Date date, BigDecimal price) {
			this.date = date;
			this.price = price;
		}

		@Override
		public int compareTo(Stock o) {
			return price.compareTo(o.price);
		}
		
		public String toString() {
			return "Date = " + date + ", price = " + price;
		}
	}
	
}
