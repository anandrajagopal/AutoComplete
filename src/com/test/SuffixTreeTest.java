package com.test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.List;

import java.util.LinkedList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SuffixTreeTest {
	
	private String input;
	
	private List<Integer> expectedIndices;

	private SuffixTree suffixTree;
	
	@Before
	public void buildTree() {
		this.suffixTree = new SuffixTree();
		List<String> words = new LinkedList<String>() {
			private static final long serialVersionUID = 1676489769011386326L;

			{
				add("banana$");
				add("mango$");
				add("minimize$");
				add("maximize$");
				add("bank$");
				add("development you$");
			}
		};
		suffixTree.add(words);
	}

	@Parameters()
    public static Iterable<Object[]> data() {
        return Arrays.asList(
        		new Object[][] { 
        				{ "an", new int[] {1, 2, 5} },
        				{ "m", new int[] {2, 3, 4, 6} },
        				{ "na", new int[] {1} },
        				{ "ban", new int[] {1, 5} },
        				{ "min", new int[] {3} },
        				{ "ize", new int[] {3, 4} },
        				{ "o", new int[] {2, 6} },
        				{ "man", new int[] {2} },
        				{ "mango", new int[] {2} },
        				{ "ximize", new int[] {4} },
        				{ "imize", new int[] {3, 4} },
        				{ "ze", new int[] {3, 4} },
        				{ "inimize", new int[] {3} },
        				{ "anana", new int[] {1} },
        				{ "ngo", new int[] {2} },
        				{ "aximize", new int[] {4} },
        				{ "ana", new int[] {1} },
        				{ "ank", new int[] {5} },
        				{ "k", new int[] {5} },
        				{ "opment", new int[] {6} },
        				{ " ", new int[] {6} },
        				{ "you", new int[] {6} },
        				{ "u", new int[] {6} },
        				{ "bank", new int[] {5} },
        				{ "extrodinary", new int[] {} },
        				{ "yj", new int[] {}},
        			}
        		);
    }
    
    public SuffixTreeTest(String input, int[] expected) {
    	this.input = input;
    	this.expectedIndices = new LinkedList<Integer>();
    	for(int index : expected) {
    		expectedIndices.add(index);
    	}
    }
    
	@Test
	public void test() {
		List<Integer> indices = new LinkedList<Integer>();
		suffixTree.find(input, indices);
		assertThat(indices, is(expectedIndices));
	}

}
