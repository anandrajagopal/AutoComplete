package com.anand.careercup;

public class StringUniqueCharcters {

	public static void main(String[] args) {
		String string = "shreya";
		int checker = 0;
		int k = 0;
		k |= 1<<5;
		System.out.println(k);
		k |= 1<<4;
		for(int i=0; i<string.length(); i++) {
			int val = string.charAt(i) - 'a';
			if ((checker & (1 << val)) > 0) {
				System.out.println(false);
				break;
			}
			checker |= (1<<val);
		}
	}
}
