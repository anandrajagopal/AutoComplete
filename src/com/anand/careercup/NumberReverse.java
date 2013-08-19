package com.anand.careercup;

public class NumberReverse {

	public static void main(String[] args) {
		System.out.println(reverse(12345678));
	}

	private static int reverse(int num) {
		return reverse(num, 0);
	}
	
	private static int reverse(int num, int r) {
		if (num == 0)
			return r;
		return reverse(num/10, r*10 + num%10); 
	}
	
}
