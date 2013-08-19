package com.test;

public class NamePrinter {

	public static void main(String[] args)
	{
		printHorizontal();
		System.out.println("SSS              SSS");
		System.out.println("SSS              SSS");
		System.out.println("SSS");
		System.out.println("SSS");
		printHorizontal();
		System.out.println("                 SSS");
		System.out.println("                 SSS");
		System.out.println("SSS              SSS");
		System.out.println("SSS              SSS");
		printHorizontal();
	}
	
	private static void printHorizontal()
	{
		for(int i=0;i<3;i++)
		{
			printChar('S', 20);
			System.out.println();
		}
	}
	
	private static void printChar(char c, int times)
	{
		for(int i=0; i<times; i++)
		{
			System.out.print(c);
		}
	}

}
