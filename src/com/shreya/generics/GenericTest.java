package com.shreya.generics;

import java.util.ArrayList;
import java.util.List;

public class GenericTest {

	public static void main(String[] args) {
		List<Number> l = new ArrayList<Number>();
		l.add(new Integer(42));
		System.out.println(l.get(0));
	}
}
