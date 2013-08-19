package com.test;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class Hanoi {

	Peg[] pegs = null;
	LinkedList<Disc> emptyPegs = new LinkedList<>();
	int[] initial;
	int[] finalConfig;
	List<String> moves = new LinkedList<String>();
	
	public Hanoi(int discs, int numberOfPegs, int[] initialConfig, int[] finalConfig) {
		pegs = new Peg[numberOfPegs + 1];
		initial = initialConfig;
		this.finalConfig = finalConfig;
		for(int i=1; i<pegs.length; i++) {
			pegs[i] = new Peg(i);
		}

		for(int i=(initialConfig.length - 1); i >= 1; --i) {
			pegs[initialConfig[i]].add(new Disc(i));
		}
		
		for(int i=1; i<finalConfig.length; i++) {
			pegs[finalConfig[i]].expect(new Disc(i));
		}
		
	}
	
	private void print() {
		for(int i=1; i<pegs.length; i++) {
			Peg peg = pegs[i];
			System.out.print(peg);
			while(!peg.isEmpty()) {
				System.out.print("  " + peg.pop());
			}
			System.out.println();
		}
	}
	
	private void printExpecting() {
		for(int i=1; i<pegs.length; i++) {
			Peg peg = pegs[i];
			System.out.print(peg);
			if (peg.expecting != null) {
				while(!peg.expecting.isEmpty()) {
					System.out.print("  " + peg.expecting.remove());
				}
				System.out.println();
			}
		}
	}

	public void move() {
		Stack<Disc> unmovedDiscs = new Stack<Disc>();
		for(int i=(finalConfig.length-1); i>=1;) {
			int destinationPegNumber = finalConfig[i];
			int currentPegNumber = initial[i];
			Disc disc = pegs[currentPegNumber].peek();
			if (pegs[destinationPegNumber].contains(disc)) {
				--i;
				pegs[currentPegNumber].pop();
				continue;
			}
			if (canMoveFromSourceToDestination(disc, currentPegNumber, destinationPegNumber)) {
				Disc poppedDisc = pegs[currentPegNumber].pop();
				pegs[destinationPegNumber].add(poppedDisc);
				--i;
				moves.add(currentPegNumber + " " + destinationPegNumber);
				System.out.println(moves);
			} else {
				unmovedDiscs.push(disc);
				--i;
				//adjust(disc, currentPegNumber);
			}
		}
		System.out.println(unmovedDiscs);
	}
	
	private void adjust(Disc disc, int fromPegNumber) {
		Peg peg = pegs[fromPegNumber];
		Disc popped = null;
		while(true) {
			if (peg.peek().equals(disc)) {
				return;
			}
			popped = peg.pop();
			moveDisc(popped, fromPegNumber);
		}
	}
	
	private void moveDisc(Disc disc, int fromPegNumber) {
		for(int i=pegs.length-1; i>=1; --i) {
			Peg peg = pegs[i];
			if (peg.number() == fromPegNumber) {
				continue;
			}
			if (peg.canAccept(disc)) {
				peg.add(disc);
				initial[disc.radius] = peg.number();
				moves.add(fromPegNumber + " " + peg.number);
				System.out.println(moves);
				return;
			}
		}
		System.out.println("Unable to move " + disc + " from " + fromPegNumber);
	}
	
	private boolean canMoveFromSourceToDestination(Disc disc, int currentPegNumber, int destinationPegNumber) {
		if (pegs[currentPegNumber].peek().equals(disc) && pegs[destinationPegNumber].canAccept(disc)) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		int discs = 2, pegs = 3;
		int[] initialConfiguration = {0, 1, 1};
		int[] finalConfig = {0, 2, 2};
		
		/*
		Hanoi moves = new Hanoi(discs, pegs, initialConfiguration, finalConfig);
		moves.move();
		moves.print();
		System.out.println(moves.moves);
		System.out.println("----------------------");

		Hanoi h2 = new Hanoi(6, 4, new int[]{0, 4, 2, 4, 3, 1, 1}, new int[] {0, 1, 1, 1, 1, 1, 1});
		h2.move();
		h2.print();
		h2.printExpecting();
		System.out.println(h2.moves);
		System.out.println("---------");
		*/
		
		Hanoi h3 = new Hanoi(6, 4, new int[]{0, 3, 3, 2, 1, 4, 1}, new int[] {0, 1, 4, 2, 2, 3, 4});
		//System.out.println(h2.pegs[4].peek());
		//h3.move();
		h3.print();
		System.out.println(h3.moves);
	}
	
	
	public static class Disc
	{
		int radius;
		int currentPeg;
		int destinationPeg;
		
		public static Comparator<Disc> REVERSE = new ReverseComparator();
		
		public Disc(int radius) {
			this.radius = radius;
		}
		
		@Override
		public String toString() {
			return "R" + radius;
		}
		
		@Override
		public boolean equals(Object obj) {
			Disc disc = (Disc) obj;
			return this.radius == disc.radius;
		}
		
		@Override
		public int hashCode() {
			return radius;
		}
		
		private static class ReverseComparator implements Comparator<Disc>{
			@Override
			public int compare(Disc o1, Disc o2) {
				return o2.radius - o1.radius;
			}
		}
	}
	
	public static class Peg
	{
		private LinkedList<Disc> discs = new LinkedList<>();
		
		private PriorityQueue<Disc> expecting = new PriorityQueue<>(8, Disc.REVERSE);
		
		private Set<Disc> discSet = new HashSet<>();
		
		private int number;
		
		public Peg(int number) {
			this.number = number;
		}
		
		public boolean add(Disc disc) {
			System.out.println("Trying to add " + disc + " to peg " + number);
			Disc expectingDisc = expecting.peek();
			if (expecting.isEmpty() || expectingDisc.radius == disc.radius) {
				System.out.println("Added " + disc + " to peg " + number);
				discs.push(disc);
				discSet.add(disc);
				if (!expecting.isEmpty()) {
					Disc remove = expecting.remove();
					System.out.println("Removed " + remove + " from peg " + number);
				}
				return true;
			}
			return false;
		}
		
		public boolean canAccept(Disc disc) {
			Disc expectingDisc = expecting.peek();
			System.out.print("Can accept " + disc + " into " + this + " ");
			if (!expecting.isEmpty() && !discs.isEmpty()) {
				return disc.radius < discs.peek().radius && expectingDisc.radius == disc.radius;
			}
			if ((expecting.isEmpty() && (discs.isEmpty() || disc.radius < discs.peek().radius)) || (!expecting.isEmpty() && expectingDisc.radius == disc.radius)) {
				System.out.println(true);
				return true;
			}
			System.out.println(false);
			return false;
		}
		
		public Disc pop() {
			return discs.pop();
		}
		
		public Disc peek() {
			return discs.peek();
		}
		
		public boolean isEmpty() {
			return discs.isEmpty();
		}
		
		public int number() {
			return this.number;
		}
		
		public boolean contains(Disc disc) {
			return discSet.contains(disc);
		}
		
		public void expect(Disc disc) {
			if (!discs.contains(disc)) {
				expecting.add(disc);
			}
		}
		
		@Override
		public String toString() {
			return "Peg = " + number;
		}
	}
}
