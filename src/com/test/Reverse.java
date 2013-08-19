package com.test;

public class Reverse {

	public static void main(String[] args) {
		int[] arr = new int[] {1, 2, 3};
		reverse(arr);
	}
	
	public static void reverse(int[] array) {
	   int j = array.length - 1;
	   for(int i=0; i<j; i++) {
		   array[i] ^= array[j];
		   array[j] ^= array[i];
		   array[i] ^= array[j];
		   
		   /*
		   int temp = array[i];
	       array[i] = array[j];
	       array[j] = temp;
	       */
	       j--;
	   }
		   
	   for(int k : array) {
			System.out.print(k);
	   }
	}
}
