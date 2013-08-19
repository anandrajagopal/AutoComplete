package com.test;

import java.util.LinkedList;
import java.util.List;

public class ThePegProblem {

	LinkedList<Disc>[] pegs = null;
	LinkedList<Disc> emptyPegs = new LinkedList<>();
	int[] initial;
	private int totalMoves;
	List<String> moves = new LinkedList<String>();
	public ThePegProblem(int discs, int numberOfPegs, int[] initialConfig) {
		pegs = new LinkedList[numberOfPegs + 1];
		initial = initialConfig;
		for(int i=1; i<pegs.length; i++) {
			pegs[i] = new LinkedList<Disc>();
		}
		for(int i=1; i<initialConfig.length; i++) {
			pegs[initialConfig[i]].add(new Disc(i));
		}
	}
	
	private void print() {
		for(int i=1; i<pegs.length; i++) {
			LinkedList<Disc> peg = pegs[i];
			while(!peg.isEmpty()) {
				System.out.println("Peg " + i + "  " + peg.pop().radius);
			}
		}
	}

	private void move(int[] finalConfig) {
		for (int i= finalConfig.length - 1; i > 0;) {
			int destination = finalConfig[i];
			int disc = i;
			int originalPeg = initial[i];
			if (destination == originalPeg) {
				--i;
				continue;
			}
			if (canMove(disc, originalPeg, destination)) {
				LinkedList<Disc> destPeg = pegs[destination];
				destPeg.push(pegs[originalPeg].pop());
				moves.add(originalPeg + "  " + destination);
				--i;
				++totalMoves;
				continue;
			}
			adjustPositions(disc, originalPeg, destination);
		}
		System.out.println(totalMoves);
		for(String move : moves) {
			System.out.println(move);
		}
	}
	
	private void adjustPositions(int disc, int originalPeg, int destination) {
		LinkedList<Disc> source = pegs[originalPeg];
		while (source.peek().radius != disc) {
			Disc pop = source.pop();
			pushToAnyOtherThan(pop, originalPeg, destination);
		}
	}

	private void pushToAnyOtherThan(Disc pop, int originalPeg, int destination) {
		LinkedList<Disc> empty = null;
		for(int i=1; i<pegs.length; i++) {
			if (i == originalPeg || i==destination) {
				continue;
			}
			if (pegs[i].isEmpty()) {
				empty = pegs[i];
				empty.push(pop);
				++totalMoves;
				reconfigOriginal(pop, originalPeg, i);
				break;
			}
		}
		
	}

	private void reconfigOriginal(Disc pop, int originalPeg, int newPlace) {
		moves.add(originalPeg + "  " + newPlace);
		int temp = initial[pop.radius];
		initial[pop.radius] = newPlace;
		initial[originalPeg] = temp;
	}

	private boolean canMove(int disc, int fromPeg, int toPeg) {
		LinkedList<Disc> source = pegs[fromPeg];
		Disc peek = source.peek();
		if (peek.radius == disc) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		int discs = 2, pegs = 3;
		int[] initialConfiguration = {0, 1, 1};
		
		ThePegProblem moves = new ThePegProblem(discs, pegs, initialConfiguration);
		int[] finalConfig = {0, 2, 2};
		moves.move(finalConfig);

		/*
		int discs = 6, pegs = 4;
		int[] initialConfiguration = {0, 4, 2, 4, 3, 1, 1};
		
		ThePegProblem moves = new ThePegProblem(discs, pegs, initialConfiguration);
		int[] finalConfig = {0, 1, 1, 1, 1, 1, 1};
		moves.move(finalConfig);
		*/
	}
	
	public static class Disc
	{
		int radius;
		public Disc(int radius) {
			this.radius = radius;
		}
	}
}
