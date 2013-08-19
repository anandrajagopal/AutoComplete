package com.shreya;

public class LoopTest {

	public static void main(String[] args) {
		
		String acctFromFile1 = "12345";
		String [] accountsFromFile2 = {"2345", "5678"};
		findAccount(acctFromFile1, accountsFromFile2);
	}

	public static int findAccount(String acctFromFile1,
			String[] accountsFromFile2) {
		int i = 0;
		while (i < accountsFromFile2.length && acctFromFile1 != accountsFromFile2[i])
		{
			i++;
		}
		if (i >= accountsFromFile2.length) 
		{
			return -1;
		}
		return i;
	}
}
