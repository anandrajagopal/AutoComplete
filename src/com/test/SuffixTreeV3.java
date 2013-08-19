package com.test;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class SuffixTreeV3 {

	private Node root = new EmptyNode(null);
	
	public static void main(String[] args) throws IOException {
		SuffixTreeV3 tree = new SuffixTreeV3();
		
		List<String>  suffixes = new LinkedList<String>();
		
		File f = new File("/Users/anand/Apps/workspace/Test/src/com/test/words-small.txt");
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String word = null;
		while( (word = reader.readLine()) != null ) {
			suffixes.add(word.trim());
		}
		reader.close();
		tree.add(suffixes);
		System.out.println("Ready for search...");
		GraphVizNodeVisitor graphVizNodeVisitor = new GraphVizNodeVisitor();
		tree.bfs(graphVizNodeVisitor);
		System.out.println(graphVizNodeVisitor.getDotNotation());
		
		Console console = System.console();
		String str = null;
		List<Integer> indices = new LinkedList<>(); 
		while((str = console.readLine()) != null) {
			long start = System.nanoTime();
			tree.find(str, indices);
			System.out.println("Time to find " + (System.nanoTime() - start));
			System.out.println(indices);
			indices.clear();
			
			start = System.nanoTime();
			int i=1;
			for(String suffix : suffixes) {
				if (suffix.contains(str)) {
					indices.add(i);
				}
				++i;
			}
			System.out.println("Time to find " + (System.nanoTime() - start) + " - # of searches " + i);
			System.out.println(indices);
			indices.clear();
		}
	}
	
	public void find(String suffix, List<Integer> indices) {
		root.find(suffix, indices);
	}
	
	public static class GraphVizNodeVisitor implements NodeVisitor {

		StringBuilder builder = new StringBuilder("digraph {");
		
		@Override
		public void visit(Node node) {
			for(Edge edge : node.characterEdgeMap.values()) {
				builder.append(node.description() + " -> " + edge.node.description() + "[label=\"" + edge.getLabelAsString() + "-" + edge.indices +"\"];\n");
			}
		}

		public String getDotNotation() {
			builder.append("}");
			return builder.toString();
		}
		
	}
	
	public interface NodeVisitor {
		void visit(Node node);
	}
	
	public void bfs(NodeVisitor visitor) {
		Node node = root;
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(node);
		while(!queue.isEmpty()) {
			node = queue.remove();
			visitor.visit(node);
			for(Edge edge : node.characterEdgeMap.values()) {
				queue.add(edge.node);
			}
		}
	}

	private static class NameValuePair<T extends Comparable<T>, V extends Comparable<V>> implements Comparable<NameValuePair<T, V>> {
		T name;
		V value;
		public NameValuePair(T name, V value) {
			this.name = name;
			this.value = value;
		}
		
		@Override
		public int compareTo(NameValuePair<T, V> o) {
			return name.compareTo(o.name);
		}
		
		public String toString() {
			return "Name = " + name + ", Value = " + value;
		}
	}
	
	public void add(List<String> suffixes) {
		if (suffixes == null) {
			return;
		}
		List<NameValuePair<String, Integer>> suffixList = new LinkedList<>();

		long start = System.currentTimeMillis();
		System.out.println("Generating suffixes");
		Integer index = 1;
		for(String key : suffixes) {
			int i = key.length() - 1;
			while(i>=0) {
				suffixList.add(new NameValuePair<String, Integer>(key.substring(i), index));
				--i;
			}
			++index;
		}
		System.out.println("Done generating suffixes in " + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		System.out.println("Sorting suffixes");
		Collections.sort(suffixList);
		System.out.println("Done sorting suffixes in " + (System.currentTimeMillis() - start));
		
		start = System.currentTimeMillis();
		System.out.println("Building tree");
		for(NameValuePair<String, Integer> nameValuePair : suffixList) {
			root.addSuffix(nameValuePair.name, nameValuePair.value);
		}
		System.out.println("Done building tree in " + (System.currentTimeMillis() - start));
		suffixList = null;
	}

	public static class Node {
		Edge parent;
		Map<Character, Edge> characterEdgeMap = new TreeMap<>();
		int id;
		
		public Node(Edge parent, int id) {
			this.parent = parent;
			this.id = id;
		}
		
		public void addSuffix(String suffix, int index) {
			if (characterEdgeMap.containsKey(suffix.charAt(0))) {
				addToEdge(suffix.charAt(0), suffix, index);
			} else {
				Edge newEdge = new Edge(index, this);
				char[] charArray = suffix.toCharArray();
				for(char label : charArray) {
					newEdge.addLabel(label);
				}
				characterEdgeMap.put(charArray[0], newEdge);
			}
		}

		public void find(String suffix, List<Integer> indices) {
			System.out.println("searching...");
			if (characterEdgeMap.containsKey(suffix.charAt(0))) {
				characterEdgeMap.get(suffix.charAt(0)).find(suffix, indices);
			}
		}

		private void addToEdge(char charAt, String suffix, int index) {
			characterEdgeMap.get(charAt).addSuffix(suffix, index);
		}
		
		public String toString() {
			return characterEdgeMap.toString();
		}
		
		public String description() {
			if (parent == null) return "root";
			return parent.description() + id;
			//return parent.description() + "\"" + parent.indices.toString() + "\"";
		}

		public void addSuffixLevel(String suffix, int index) {
			Edge newEdge = new Edge(this);
			char[] charArray = suffix.toCharArray();
			for(char label : charArray) {
				newEdge.addLabel(label);
			}
			newEdge.node.characterEdgeMap.putAll(characterEdgeMap);
			if (newEdge.indices.isEmpty()) {
				newEdge.indices.addAll(this.parent.indices);
			}
			characterEdgeMap.clear();
			characterEdgeMap.put(charArray[0], newEdge);
		}
	}
	
	public static class EmptyNode extends Node {
	
		public EmptyNode(Edge parent) {
			super(parent, 0);
		}

		@Override
		public String toString() {
			return "root";
		}
	}
	

	public static class Edge {
		List<Character> labels = new ArrayList<Character>();
		Node node;
		Node parent;
		Set<Integer> indices = new TreeSet<Integer>();

		public Edge(int index, Node parent) {
			this.parent = parent;
			this.node = new Node(this, parent.id + 1);
			indices.add(index);
		}
		
		public Edge(Node parent) {
			this.parent = parent;
			this.node = new Node(this, parent.id + 1);
		}
		
		public void find(String suffix, List<Integer> indices) {
			System.out.println("searching...");
			if (getLabelAsString().startsWith(suffix)) {
				indices.addAll(this.indices);
			} else {
				node.find(suffix.substring(labels.size()), indices);
			}
		}

		public boolean isTerminating() {
			return labels.get(0).equals('$');
		}

		public void addLabel(char label) {
			labels.add(label);
		}
		
		public void addIndex(int index) {
			indices.add(index);
		}
		
		public String description() {
			if (isTerminating()) {
				return "t";
			}
			return getLabelAsString().replace("$", "");
		}
		
		public String getLabelAsString() {
			Character[] characters = labels.toArray(new Character[0]);
			StringBuilder builder = new StringBuilder();
			for(char c : characters) {
				builder.append(c);
			}
			return builder.toString();
		}

		public void addSuffix(String suffix, int index) {
			char[] charArray = suffix.toCharArray();
			int i=0;
			for(char character : charArray) {
				if (i>=labels.size() || !labels.get(i).equals(character)) {
					
					if (i < labels.size()) {
						node.addSuffixLevel(getLabelAsString().substring(i), index);
						resetLabels(suffix.substring(0, i));
					}

					node.addSuffix(suffix.substring(i), index);
					break;
				}
				++i;
			}
			indices.add(index);
		}

		public void resetLabels(String newSuffix) {
			labels.clear();
			for(char character : newSuffix.toCharArray()) {
				labels.add(character);
			}
		}
		
		public String toString() {
			return labels.toString();
		}

	}
	
	/*
	 * USAA
	 * The development bank
	 * Development board
	 * Austin Development Circle
	 * 
	 * USAA$The development bank$Development board$Austin Development Circle
	 */
}
