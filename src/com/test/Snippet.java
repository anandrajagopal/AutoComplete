package com.test;

public class Snippet {
	public static void reverse ( int[] array ) {
	   
	   int j = array.length -1;
	   for(int i=0; i<=j; i++) {
	       int temp = array[i];
	       array[i] = array[j];
	       array[j] = temp;
	       j--;
	   }
	   
	}
}

