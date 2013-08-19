package com.test;

import com.test.BinaryTree.LevelVisitor;

public class OddEvenSum {

	public static void main(String[] args) {
		BinaryTree<Integer> tree = new BinaryTree<Integer>()
									     .insert(5)
									     .insert(3)
									     .insert(6)
									     .insert(2)
									     .insert(4)
									     .insert(7)
									     .insert(1)
									     .insert(9)
									     .insert(8);
		TotalLevelVisitor<Integer> levelVisitor = new TotalLevelVisitor<Integer>() {
			
			private int total = 0;
			
			@Override
			public void visit(Integer data, int level) {
				System.out.println("Level = " + level + ", Data = " + data);
				if (level%2 == 0) {
					total -= data;
				} else {
					total += data;
				}
			}

			@Override
			public int getTotal() {
				return total;
			}
		};
		
	
		tree.levelAwareDFS(levelVisitor);
		System.out.println(levelVisitor.getTotal());
	}
	
	public interface TotalLevelVisitor<T> extends LevelVisitor<T> {
		public int getTotal();
	}
	
	
}
