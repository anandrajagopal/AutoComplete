package com.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree<T extends Comparable<T>> {

	private Node<T> root;
	
	public static void main(String[] args) {
		BinaryTree<Integer> tree = new BinaryTree<>();
		tree.insert(10)
			.insert(20)
			.insert(5)
			.insert(16)
			.insert(3)
			.insert(13)
			.insert(30)
			.insert(25)
			.insert(2)
			.insert(1);
		//System.out.println(tree.lookup(3));
		tree.printTree();
		
		/*
		BinaryTree<String> sTree = new BinaryTree<>();
		sTree.insert("Anand")
			 .insert("Deepa")
			 .insert("Shreya");
		sTree.printTree();
		*/
		//tree.bfs();
		//System.out.println("");
		//tree.dfs();
		
		
		BinaryTree<Integer> iTree = new BinaryTree<>();
		iTree.insert(5)
			 .insert(2)
			 .insert(6)
			 .insert(1)
			 .insert(3)
			 .insert(7);
		//System.out.println(iTree.isBST(iTree.root));
		//iTree.isBST(iTree.root);
		//tree.isBST(tree.root);
		//tree.isBSTWithoutRecursion(tree.root);
		System.out.println(tree.maxDepth(tree.root));
		//System.out.println(iTree.maxDepth(iTree.root));
		
		iTree = new BinaryTree<>();
		iTree.insert(new int[] {1, 2, 3, 4, 5, 6});
		iTree.printTree();
	}
	
	private int maxDepth(Node<Integer> node) {
		if (node == null) {
			return 0;
		}
		return 1 + Math.max(maxDepth(node.left), maxDepth(node.right));
	}
	
	private void isBST(Node<Integer> node) {
		System.out.println(isBST(node, null, false));
	}
	
	private boolean isBST(Node<Integer> node, Integer previous, Boolean left) {
		if (node == null) {
			return true;
		}
		if (previous != null && node != null) {
			System.out.println("Previous = " + previous + ", Data = " + node.data);
			if (left) {
				if (previous.compareTo(node.data) < 0) {
					return false;
				}
			} else {
				if (previous.compareTo(node.data) > 0) {
					return false;
				}
			}
		}
		return isBST(node.left, node.data, true) && isBST(node.right, node.data, false);
	}
	
	public Node<T> root() {
		return root;
	}
	
	public Node<T> leftChild(Node<T> node) {
		return node.left;
	}
	
	public Node<T> rightChild(Node<T> node) {
		return node.right;
	}
	
	public interface NodeVisitor<T> {
		public boolean visit(T t);
	}
	
	public void inorder(NodeVisitor<T> visitor) {
		inorder(root, visitor);
	}
	
	public void inorder(Node<T> node, NodeVisitor<T> visitor) {
		if (node.left != null) {
			inorder(node.left, visitor);
		}
		if (visitor.visit(node.data)) {
			return;
		}
		//System.out.println(node.data);
		if (node.right != null) {
			inorder(node.right, visitor);
		}
	}
	
	public T findMax() {
		return findMax(root);
	}
	
	private T findMax(Node<T> node) {
		if (node.right == null) {
			return node.data;
		}
		return findMax(node.right);
	}

	public interface LevelVisitor<T>
	{
		public void visit(T data, int level);
	}
	
	public void levelAwareBFS(LevelVisitor<T> visitor) {
		Node<T> node = root;
		Queue<Node<T>> queue = new LinkedList<Node<T>>();
		queue.add(node);
		int level = 1;
		while(!queue.isEmpty()) {
			node = queue.remove();
			visitor.visit(node.data, level);
			if (node.left != null) {
				queue.add(node.left);
			}
			if (node.right != null) {
				queue.add(node.right);
			}
		}
	}
	
	public void levelAwareDFS(LevelVisitor<T> visitor) {
		Stack<Node<T>> stack = new Stack<>();
		stack.push(root);
		int level = 1;
		while(!stack.isEmpty()) {
			Node<T> node = stack.peek();
			if (!node.visited) {
				visitor.visit(node.data, level);
				node.visited = true;
			}
			Node<T> unvisitedNode = unvisitedNode(node);
			if (unvisitedNode != null) {
				stack.push(unvisitedNode);
				++level;
			} else {
				stack.pop();
				--level;
			}
		}
	}
	
	public List<List<Node<T>>> levelLinkedList(Node<T> root) {
		Node<T> node = root;
		List<List<Node<T>>> levelList = new ArrayList<List<Node<T>>>();
		List<Node<T>> list = new LinkedList<Node<T>>();
		int level = 0;
		list.add(node);
		levelList.add(level, list);
		while(true) {
			if (levelList.get(level).isEmpty()) {
				break;
			}
			levelList.add(level + 1, new LinkedList<Node<T>>());
			for(Node<T> n : levelList.get(level)) {
				if (n.left != null) {
					levelList.get(level + 1).add(n.left);
				}
				if (n.right != null) {
					levelList.get(level + 1).add(n.right); 
				}
			}
			level++;
		}
		return levelList;
	}
	
	public void bfs() {
		Node<T> node = root;
		Queue<Node<T>> queue = new java.util.LinkedList<Node<T>>();
		queue.add(node);
		while(!queue.isEmpty()) {
			node = queue.remove();
			System.out.print(node + "  ");
			if (node.left != null) {
				queue.add(node.left);
			}
			if (node.right != null) {
				queue.add(node.right);
			}
		}
	}
	
	public void dfs() {
		Stack<Node<T>> stack = new Stack<>();
		stack.push(root);
		while(!stack.isEmpty()) {
			Node<T> node = stack.peek();
			if (!node.visited) {
				System.out.print(node + "  ");
			}
			node.visited = true;
			Node<T> unvisitedNode = unvisitedNode(node);
			if (unvisitedNode != null) {
				stack.push(unvisitedNode);
			} else {
				stack.pop();
			}
		}
	}
	
	private Node<T> unvisitedNode(Node<T> node) {
		if (node.left != null && !node.left.visited) {
			return node.left;
		} else if (node.right != null && !node.right.visited) {
			return node.right;
		}
		return null;
	}

	public static class Node<T extends Comparable<T>> {
		private Node<T> left;
		private Node<T> right;
		T data;
		private boolean visited;

		public Node(T data) {
			this.data = data;
			left = null;
			right = null;
		}
		
		public String toString() {
			return data.toString();
		}
		
		public void insert(T data) {
			
			if (data.compareTo(this.data) < 0) {
				if (left == null) {
					left = new Node<T>(data);
				} else {
					left.insert(data);
				}
			}
			if (data.compareTo(this.data) > 0) {
				if (right == null) {
					right = new Node<T>(data);
				} else {
					right.insert(data);
				}
			}
		}
		
	}
	
	public void printTree() {
		printTree(root);
	}
	
	private void printTree(Node<T> node) {
		List<List<Node<T>>> levelLinkedList = levelLinkedList(node);
		for(List<Node<T>> levelList : levelLinkedList) {
			for(Node<T> n : levelList) {
				System.out.print(n + "  ");
			}
			System.out.println();
		}
	}

	public Node<T> lookup(T data) {
		return lookup(root, data);
	}

	private Node<T> lookup(Node<T> node, T data) {
		while(node != null) {
			if (node.data.compareTo(data) == 0) {
				return node;
			}
			if (data.compareTo(node.data) < 0) {
				node = node.left;
			}
			if (data.compareTo(node.data) > 0) {
				node = node.right;
			}
		}
		return null;
	}

	public BinaryTree<T> insert(T data) {
		Node<T> newNode = new Node<>(data);
		if (root == null) {
			root = newNode;
		} else {
			root.insert(data);
		}
		return this;
	}
	
	
	public void insert(int[] arr) {
		root = insert(arr, 0, arr.length - 1);
	}
	
	@SuppressWarnings("unchecked")
	private Node<T> insert(int[] arr, int start, int end) {
		if (end < start) {
			return null;
		}
		int mid = (start + end) / 2;
		Node<Integer> node = new Node<>(arr[mid]);
		node.left = (Node<Integer>) insert(arr, start, mid - 1);
		node.right = (Node<Integer>) insert(arr, mid + 1, end);
		return (Node<T>) node;
	}
	
}
