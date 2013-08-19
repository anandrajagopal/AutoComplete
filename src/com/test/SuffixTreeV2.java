package com.test;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class SuffixTreeV2 {

	private Node root = new EmptyNode(null);
	
	public static void main(String[] args) throws IOException {
		SuffixTreeV2 tree = new SuffixTreeV2();
		
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
		//GraphVizNodeVisitor graphVizNodeVisitor = new GraphVizNodeVisitor();
		//tree.bfs(graphVizNodeVisitor);
		//System.out.println(graphVizNodeVisitor.getDotNotation());
		
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
			//builder.append(node.description() + " -> " + node.description() + "[label=\"" + edge.getLabelAsString() + "-" + edge.indices +"\"];\n");
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
			for(Node child : node.characterNodeMap.values()) {
				queue.add(child);
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
		Node parent;
		Map<Character, Node> characterNodeMap = new TreeMap<>();
		List<Integer> indices = new LinkedList<>();
		char[] labels;
		
		public Node(Node parent) {
			this.parent = parent;
		}
		
		private void addLabels(char[] suffix, int index) {
			if (labels == null) {
				labels = suffix;
			} else {
				labels = Arrays.copyOf(labels, labels.length + suffix.length);
				System.arraycopy(suffix, 0, labels, labels.length - suffix.length, suffix.length);
			}
			indices.add(index);
		}
		
		public void addSuffix(String suffix, int index) {
			if (labels == null) {
				addLabels(suffix.toCharArray(), index);
			} else 
			if (labels[0] == suffix.charAt(0)) {
				adjustNode(suffix.charAt(0), suffix, index);
			} 
			else 
			if (characterNodeMap.containsKey(suffix.charAt(0))) {
				characterNodeMap.get(suffix.charAt(0)).addSuffix(suffix, index);
			}
		}

		public void find(String suffix, List<Integer> indices) {
			System.out.println("searching...");
			if (characterNodeMap.containsKey(suffix.charAt(0))) {
				//characterNodeMap.get(suffix.charAt(0)).find(suffix, indices);
			}
		}

		private void adjustNode(char charAt, String suffix, int index) {
			for(int i=0; i<suffix.length(); i++) {
				if (i>=labels.length || labels[i] != suffix.charAt(i)) {
					if (i < labels.length) {
						Node newNode = new Node(this);
						newNode.addLabels(Arrays.copyOfRange(labels, i, labels.length), index);
						newNode.characterNodeMap.putAll(characterNodeMap);
						newNode.indices.addAll(indices);
						characterNodeMap.clear();
						char[] newLabels = Arrays.copyOf(labels, i);
						this.labels = null;
						this.addLabels(newLabels, index);
						characterNodeMap.put(newLabels[0], newNode);
					}
					this.addSuffix(suffix.substring(i), index);
					break;
				}
				++i;
			}
			this.indices.add(index);
			//characterNodeMap.get(charAt).addSuffix(suffix, index);
		}
		
		public String toString() {
			return characterNodeMap.toString();
		}
		
		public String description() {
			if (parent == null) return "root";
			return parent.description();
			//return parent.description() + "\"" + parent.indices.toString() + "\"";
		}
	}
	
	public static class EmptyNode extends Node {
	
		public EmptyNode(Node parent) {
			super(parent);
		}
		
		@Override
		public String toString() {
			return "root";
		}
	}
	
}
