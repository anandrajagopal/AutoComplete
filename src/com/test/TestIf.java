package com.test;

public class TestIf {

	public static void main(String[] args) {
		String str = "Abcd";
		int checker = 0;
		for(int i=0; i < str.length(); ++i) {
			int val = str.charAt(i) ;
			if ((checker & (1 << val)) > 0) {
				System.out.println("false");
				break;
			}
			checker |= (1 << val);
		}
		System.out.println(1 << ('z' - 'a'));
	}
	
	
	 
}
