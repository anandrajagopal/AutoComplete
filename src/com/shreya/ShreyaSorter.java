package com.shreya;

import java.util.Set;
import java.util.TreeSet;

public class ShreyaSorter {

	public static void main(String[] args) {
		Set<String> animals = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		animals.add("Dog");
		animals.add("Cat");
		animals.add("Dolphin");
		animals.add("Whale");
		animals.add("Tiger");
		animals.add("Lion");
		animals.add("Jaguar");
		animals.add("Cheetah");
		
		System.out.println(animals.toString());
	}
}
