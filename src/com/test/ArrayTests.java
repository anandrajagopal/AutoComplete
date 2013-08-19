package com.test;

import java.util.Arrays;

public class ArrayTests {
	public static void main(String[] args) {
		char[] a = new char[] {'a', 'b', 'c'};
		char[] b = Arrays.copyOfRange(a, 1, a.length);
		System.out.println(Arrays.toString(a));
		System.out.println(Arrays.toString(b));
		Character[] ac = new Character[3];
		
	}
}
