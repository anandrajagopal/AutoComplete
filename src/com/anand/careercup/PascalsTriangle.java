package com.anand.careercup;

public class PascalsTriangle {

	public static void main(String[] args) {
		int maxRows = 6;
		int space = maxRows;
		for(int row = 0; row < maxRows; row++) {
			printspace(4 * space);

			for(int col = 0; col <= row; col++) {
				System.out.printf("%3d", pascal(row, col));
				printspace(5);
			}
			System.out.println();
			--space;
		}
	}
	
	private static void printspace(int space) {
		for(int i=0; i<space; i++) {
			System.out.print(" ");
		}
	}

	private static int pascal(int row, int col) {
		if (col == 0 || row == col) {
			return 1;
		} 
		return pascal(row - 1, col) + pascal(row - 1, col - 1);
	}
}
