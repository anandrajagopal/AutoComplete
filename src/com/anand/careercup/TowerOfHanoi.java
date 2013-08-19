package com.anand.careercup;

import java.util.Stack;

public class TowerOfHanoi {

	public static class Peg {
		Stack<Integer> discs = new Stack<Integer>();
		int number;
		
		public Peg(int number) {
			this.number = number;
		}
		
		public void add(int disc) {
			if (!discs.isEmpty() &&  discs.peek() <= disc) {
				System.out.println("Cannot add disc " + disc + " above " + discs.peek() + " in " + this);
			} else {
				discs.push(disc);
			}
		}
		
		public int pop() {
			return discs.pop();
		}
		
		public int peek() {
			return discs.peek();
		}
		
		public String toString() {
			return "Peg number = " + number;
		}
		
		public void move(int disc, Peg destination, Peg buffer) {
			if (disc > 0) {
				System.out.println(disc + ", dest " + destination + ", buffer " + buffer);
				move(disc - 1, buffer, destination);
				moveTopTo(destination);
				buffer.move(disc - 1, destination, this);
			}
		}
		
		private void moveTopTo(Peg peg) {
			int top = discs.pop();
			peg.add(top);
			System.out.println("Move disk " + top + " from " + this + " to " + peg);
		}
	}
	
	public static void main(String[] args) {
		Peg[] pegs = new Peg[3];
		for(int i=0; i<3; i++) {
			pegs[i] = new Peg(i);
		}
		for(int i=4; i>=0; i--) {
			pegs[0].add(i);
		}
		pegs[0].move(3, pegs[2], pegs[1]);
	}
	
	
}
