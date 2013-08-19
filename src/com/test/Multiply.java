package com.test;

public class Multiply {

	public static void main(String[] args) {
		int x = -35, y = -55;
		
		if (x == 0 || y == 0) {
			System.out.println(0);
		}
		else {
			int ans = 0;
			for(int i=0; i< Math.abs(x); i++)
			{
				ans += Math.abs(y);
			}
			if ((x > 0 && y > 0) || (x < 0 && y < 0))
			{
				System.out.println(ans);
			}
			else
			{
				System.out.println(0 - ans);
			}
		}
	}
}
