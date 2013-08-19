package com.shreya;

import junit.framework.Assert;

import org.junit.Test;

public class TestLoop {

	@Test
	public void verifyAccountIsNotFound() {
		String acctFromFile1 = "12345";
		String [] accountsFromFile2 = {"2345", "5678"};
		int position = LoopTest.findAccount(acctFromFile1, accountsFromFile2);
		Assert.assertEquals(-1, position);
	}
	
	@Test
	public void verifyAccountIsFoundInTheFirstPosition()
	{
		String acctFromFile1 = "12345";
		String [] accountsFromFile2 = {"12345", "5678"};
		int position = LoopTest.findAccount(acctFromFile1, accountsFromFile2);
		Assert.assertEquals(0, position);
	}

	@Test
	public void verifyAccountIsFoundInTheLastPosition()
	{
		String acctFromFile1 = "12345";
		String [] accountsFromFile2 = {"2345", "5678", "12345"};
		int position = LoopTest.findAccount(acctFromFile1, accountsFromFile2);
		Assert.assertEquals(2, position);
	}
	
	@Test
	public void verifyAccountIsFoundInTheMiddlePosition()
	{
		String acctFromFile1 = "12345";
		String [] accountsFromFile2 = {"2345", "12345", "5678"};
		int position = LoopTest.findAccount(acctFromFile1, accountsFromFile2);
		Assert.assertEquals(1, position);
	}
}
