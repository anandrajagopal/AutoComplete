package com.test;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BogoSort {

	public static void main(String[] args) {
		Console console = System.console();
		String line = null;
		int count = Integer.parseInt(console.readLine());

		List<Integer> input = new ArrayList<Integer>(count);
		int i=0;
		while ( i < count && (line = console.readLine()) != null) {
			input.add(Integer.parseInt(line));
			++i;
		}

		int suffleCount;
		for(;;)
		{
			Collections.shuffle(input);
			System.out.println(input);
		}
		
	}
	
	private static List<Integer> unsortedList(List<Integer> partiallySortedList, int count)
	{
		List<Integer> newList = new ArrayList<>();
		for(int i=0; i<count; i++)
		{
			if (partiallySortedList.get(i) != (i+1))
			{
				newList.add(partiallySortedList.get(i));
			}
		}
		return newList;
	}
}
