package com.test;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class SuffixTree {

	private Node root = new EmptyNode(null);
	
	public static void main(String[] args) throws IOException {
		SuffixTree tree = new SuffixTree();
		
		List<String>  suffixes = new LinkedList<String>();
		
		File f = new File("/Users/anand/Apps/workspace/Test/src/com/test/words.txt");
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String word = null;
		while( (word = reader.readLine()) != null ) {
			suffixes.add(word.trim());
		}
		reader.close();
		tree.add(suffixes);
		System.out.println("Ready for search...");
		//GraphVizNodeVisitor graphVizNodeVisitor = new GraphVizNodeVisitor();
		//tree.bfs(graphVizNodeVisitor);
		//System.out.println(graphVizNodeVisitor.getDotNotation());
		suffixes = null;
		Console console = System.console();
		String str = null;
		List<Integer> indices = new LinkedList<>(); 
		while((str = console.readLine()) != null) {
			long start = System.nanoTime();
			tree.find(str, indices);
			long end = System.nanoTime() - start;
			System.out.println(indices);
			System.out.println("Found " + indices.size() + " words in " + end);
			//System.out.println("Time to find " + (System.nanoTime() - start) + "");
			indices.clear();
			/*
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
			*/
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
				newEdge.addLabel(suffix.toCharArray());
				characterEdgeMap.put(suffix.charAt(0), newEdge);
			}
		}

		public void find(String suffix, List<Integer> indices) {
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
		}

		public void addSuffixLevel(String suffix, int index) {
			Edge newEdge = new Edge(this);
			newEdge.addLabel(suffix.toCharArray());
			newEdge.node.characterEdgeMap.putAll(characterEdgeMap);
			if (newEdge.indices.isEmpty()) {
				newEdge.indices.addAll(this.parent.indices);
			}
			characterEdgeMap.clear();
			characterEdgeMap.put(suffix.charAt(0), newEdge);
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
		char[] labels = new char[0];
		Node node;
		Set<Integer> indices = new TreeSet<Integer>();
		

		public Edge(int index, Node parent) {
			this.node = new Node(this, parent.id + 1);
			indices.add(index);
		}
		
		public Edge(Node parent) {
			this.node = new Node(this, parent.id + 1);
		}
		
		public void find(String suffix, List<Integer> indices) {
			boolean matches = true;
			for(int i=0; i<suffix.length(); i++) {
				if (i > labels.length - 1 || this.labels[i] != suffix.charAt(i)) {
					matches = false;
					break;
				}
			}
			if (!matches && suffix.length() <= labels.length) {
				return;
			}
			if (!matches) {
				node.find(suffix.substring(labels.length), indices);
			} else {
				indices.addAll(this.indices);
				return;
			}
			
		}

		public boolean isTerminating() {
			return labels[0] =='$';
		}

		public void addLabel(char[] label) {
			labels = Arrays.copyOf(labels, labels.length + label.length);
			System.arraycopy(label, 0, labels, labels.length - label.length, label.length);
			//labels.add(label);
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
			return new String(labels).intern();
		}

		public void addSuffix(String suffix, int index) {
			int i=0;
			for(int j=0; j<suffix.length(); j++) {
				if (i>=labels.length || labels[i] != suffix.charAt(j)) {
					
					if (i < labels.length) {
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
			labels = null;
			labels = newSuffix.toCharArray();
		}
		
		public String toString() {
			return labels.toString();
		}

	}
}
