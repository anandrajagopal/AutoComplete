package com.test;

import java.util.Arrays;

public class Learning {

	public static void main(String[] args) {
		int x = Integer.parseInt("1001011", 2);
		System.out.println("Actual Number = " + x);
		System.out.println("Binary value = " + Integer.toBinaryString(x) +" ");
		
		String name = new String("DEEPA");
		byte[] bytes = name.getBytes();
		System.out.println(Arrays.toString(bytes));
		for(byte b : bytes)
		{
			System.out.print(Integer.toBinaryString(b) + " ");
		}
		//
	}
}
