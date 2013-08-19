package com.anand.careercup;

import java.util.Arrays;
import java.util.Random;

public class Sorter {

	public static void main(String[] args) {
		int[] arr = {5, 6, 4, 3, 7, 8, 2, 14, 1, 20};
		sort(arr, 0, arr.length - 1, new Random());
		System.out.println(Arrays.toString(arr));
	}
	
	private static void sort(int[] arr, int start, int end, Random random) {
		if (start >= end) {
			return;
		}
		int pivotIndex = random.nextInt((end - start) + 1) + start;
		int pivot = arr[pivotIndex];
		//5 6 4 3 7 8
		arr[pivotIndex] = arr[end];
		arr[end] = pivot;
		int j = end - 1;
		int i = start;
		for(; i<=j; ) {
			while(i<=end && arr[i] <= pivot) i++;
			while(j >=0 && arr[j] > pivot) j--;
			if (i < j) {
				int k = arr[i];
				arr[i] = arr[j];
				arr[j] = k;
			}
		}
		if (i < end) 
		{
			arr[end] = arr[i];
			arr[i] = pivot;
		}
		System.out.println(Arrays.toString(arr));
		sort(arr, start, i-1, random);
		sort(arr, i + 1, end, random);
	}
}
