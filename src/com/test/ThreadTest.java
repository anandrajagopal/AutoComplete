package com.test;

public class ThreadTest {

	static boolean flag = true;
	
	public static void main(String[] args) {
		new Thread() {
			public void run() {
				while(flag) {
					System.out.println("hello");
				}
			};
		}.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		flag = false;
	}
}
