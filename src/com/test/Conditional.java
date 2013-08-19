package com.test;

public class Conditional {

	public static void main(String[] args) {
		String a = "MD";
		
		if ( ! (a.equals("MD") || a.equals("BN")) ) {
			System.out.println("go to some other place");
		}
	}
}
