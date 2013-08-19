package com.test;

public class LinkedList<E> {
	
	private Node<E> first;
	
	private Node<E> last;
	
	public LinkedList() {
		
	}
	
	public static void main(String[] args) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		list.add(1)
			.add(2)
			.add(3)
			.add(4)
			.add(5)
			.print();
		list.reverse()
			.print();
	}
	
	public LinkedList<E> reverse() {
		if (first == null){
			return this;
		}
		Node<E> nextNode = first.next;
		last = first;
		last.next = null;
		while(nextNode != null) {
			Node<E> temp = nextNode.next;
			nextNode.next = first;
			first = nextNode;
			nextNode = temp;
		}
		return this;
	}
	
	public LinkedList<E> add(E e) {
		final Node<E> l = last;
		Node<E> newNode = new Node<E>(l, e, null);
		last = newNode;
		if (l == null) {
			first = newNode;
		} else {
			l.next = newNode;
		}
		return this;
	}
	
	public void print() {
		Node<E> node = first;
		while(node != null) {
			System.out.print(node.item + "--->");
			node = node.next;
		}
		System.out.println();
	}
	
	public void printReverse() {
		Node<E> node = last;
		while (node != null) {
			System.out.print(node.item + "<---");
			node = node.previous;
		}
	}

	private static class Node<E> {
		Node<E> previous;
		Node<E> next;
		E item;
		
		Node(Node<E> previous, E item, Node<E> next) {
			this.previous = previous;
			this.item = item;
			this.next = next;
		}
	}
}
