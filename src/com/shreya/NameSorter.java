package com.shreya;

import java.util.Arrays;

public class NameSorter {

	public static void main(String[] args) {
		String [] names = 
			{
				"Allen",
				"Aldrick",
				"Adrianna",
				"Ann",
				"Amin",
				"Adrian",
				"Alison",
				"Adrien",
				"Aimee",
				"Abigail",
				"Alice",
				"Alicia",
				"Abbey",
				"Alissa",
				"Abe",
				"Annie",
				"Amie",
				"Adam",
				"Ada",
				"Annilise"
			};
		Arrays.sort(names);
		
		for (int i = 0; i < names.length; i++) {
			System.out.println(i+1 + ".  " + names[i]);
		}
	}
}
