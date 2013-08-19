package com.test;

import java.io.Console;

public class PrimePattern {

	public static void main(String[] args) {

		Board board = new Board(202500);

		Console console = System.console();
		int numberOfTestCases = Integer.parseInt(console.readLine());
		String[] input = new String[numberOfTestCases];
		for(int i=0; i < numberOfTestCases; i++) {
			input[i] = console.readLine();
		}
		for(String inputString: input) {
			String[] numbers = inputString.split(" ");
		
			DistanceFinder distanceFinder = new DistanceFinder(board, Integer.parseInt(numbers[0].trim()), Integer.parseInt(numbers[1].trim()));
			distanceFinder.findNearestDistance();
			System.out.println(distanceFinder.getDistance());
		}
	}

	public static class EfficientFinder {
		public EfficientFinder(Board board, int targetX, int targetY) {
			//System.out.println(board.l);
		}
	}
	
	public static class DistanceFinder {
		private Board board;
		private int targetX;
		private int targetY;
		private int numberOfMoves;
		private int howManyMoreToGo;
		private int distance = Integer.MAX_VALUE;
		private Position nearestPrime;

		public DistanceFinder(Board board, int targetX, int targetY) {
			this.board = board;
			this.targetX = targetX;
			this.targetY = targetY;
		}

		public void findNearestDistance() {
			numberOfMoves = 0;
			howManyMoreToGo = 0;

			int sqrt = (int) Math.sqrt(board.count);

			int xIndex = sqrt / 2;
			int yIndex = sqrt / 2 - 1;

			Position targetPosition = null;
			Direction direction = Direction.EAST;
			for (int i = 0; i < board.count;) {
				int moves = getNumberOfMoves();
				for (int j = 0; j < moves; j++) {
					Position position = board.squares[xIndex][yIndex];
					i++;
					if (position.x() == targetX && position.y() == targetY)
					{
						targetPosition = position;
					}
					if (targetPosition != null)
					{
						calculateDistance(position, targetPosition);
					}

					Point point = direction.nextPoint(xIndex, yIndex);
					xIndex = point.x;
					yIndex = point.y;
				}
				direction = direction.nextStep();
			}
		}

		private void calculateDistance(Position position, Position targetPosition) {
			if (position.isPrime) {
				int newDistance = Math.abs(position.x() - targetPosition.x())
						+ Math.abs(position.y() - targetPosition.y());
				if (newDistance < distance) {
					distance = newDistance;
					nearestPrime = position;
				}
			}
		}

		public int getDistance() {
			return distance;
		}

		private int getNumberOfMoves() {
			if (howManyMoreToGo == 0) {
				howManyMoreToGo = 1;
				return ++numberOfMoves;
			}
			--howManyMoreToGo;
			return numberOfMoves;
		}
	}

	public static class Board {
		private int numberOfMoves;
		private int howManyMoreToGo;

		private Position[][] squares = null;

		private int count;

		public Board(int count) {
			this.count = count;
			int sqrt = (int) Math.sqrt(count);
			squares = new Position[sqrt][sqrt];

			int xIndex = sqrt / 2;
			int yIndex = sqrt / 2 - 1;
			int cx = 0;
			int cy = 0;

			Direction direction = Direction.EAST;
			for (int i = 0; i < count;) {
				int moves = getNumberOfMoves();
				for (int j = 0; j < moves; j++) {
					
					Position position = new Position(cx, cy, i++);
					squares[xIndex][yIndex] = position;
					
					Point point = direction.nextPoint(xIndex, yIndex);
					Point coordinate = direction.calculateCoordinate(cx, cy);
					xIndex = point.x;
					yIndex = point.y;
					cx = coordinate.x;
					cy = coordinate.y;
				}
				direction = direction.nextStep();
			}
		}

		private int getNumberOfMoves() {
			if (howManyMoreToGo == 0) {
				howManyMoreToGo = 1;
				return ++numberOfMoves;
			}
			--howManyMoreToGo;
			return numberOfMoves;
		}
		
		public void print()
		{
			for(int i=0; i<squares.length; i++)
			{
				for(int j=0; j<squares[i].length; j++)
				{
					System.out.print(squares[i][j] + "        ");
				}
				System.out.println("");
			}
		}

	}

	public enum Direction {
		EAST {
			@Override
			public Direction nextStep() {
				return NORTH;
			}

			@Override
			public Point nextPoint(int x, int y) {
				return new Point(x, ++y);
			}

			@Override
			public Point calculateCoordinate(int cx, int cy) {
				return new Point(++cx, cy);
			}

		},
		WEST {
			@Override
			public Direction nextStep() {
				return SOUTH;
			}

			@Override
			public Point nextPoint(int x, int y) {
				return new Point(x, --y);
			}

			@Override
			public Point calculateCoordinate(int cx, int cy) {
				return new Point(--cx, cy);
			}

		},
		NORTH {
			@Override
			public Direction nextStep() {
				return WEST;
			}

			@Override
			public Point nextPoint(int x, int y) {
				return new Point(--x, y);
			}

			@Override
			public Point calculateCoordinate(int cx, int cy) {
				return new Point(cx, ++cy);
			}
		},
		SOUTH {
			@Override
			public Direction nextStep() {
				return EAST;
			}

			@Override
			public Point nextPoint(int x, int y) {
				return new Point(++x, y);
			}

			@Override
			public Point calculateCoordinate(int cx, int cy) {
				return new Point(cx, --cy);
			}
		};

		public abstract Direction nextStep();

		public abstract Point calculateCoordinate(int cx, int cy);

		public abstract Point nextPoint(int x, int y);
	}

	public static class Position {
		private boolean isPrime;
		private int value;
		private Point point;

		public Position(int x, int y, int value) {
			this.point = new Point(x, y);
			this.value = value;
			this.isPrime = isPrime(value);
		}

		public int x() {
			return point.x;
		}

		public int y() {
			return point.y;
		}

		public String toString() {
			String str = "(x,y,value) = (" + point.x + "," + point.y + "," + value + "," + isPrime + ")";
			return str;
		}

		boolean isPrime(int n) {
			if (n == 2)
				return true;
			if (n == 1)
				return false;
			// check if n is a multiple of 2
			if (n % 2 == 0)
				return false;
			// if not, then just check the odds
			for (int i = 3; i * i <= n; i += 2) {
				if (n % i == 0)
					return false;
			}
			return true;
		}
	}

	public static class Point {
		int x;
		int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
