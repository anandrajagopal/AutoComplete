auto-complete
=============

A very fast auto-complete utility using suffix tree.

How to use:

    		SuffixTree suffixTree = new SuffixTree();
		List<String> words = new LinkedList<String>() {
			private static final long serialVersionUID = 1676489769011386326L;

			{
				add("banana");
				add("mango");
				add("minimize");
				add("maximize");
				add("bank");
				add("development you$");
			}
		};
		suffixTree.add(words); //do this only once. If you have large number of words, building the tree over and over again is expensive.
		
		Then:
		
		List<Integer> indices = new LinkedList<Integer>();
		suffixTree.find("ban", indices);
		
		Now, indices will contain [1, 5]
		
		You can also search using suffixes. Try searching "ze", or "mize". 
		
Performance

I have tested it with over 100,000 words. Very rarely does the search takes 1 millisecond. Mostly the searches time can 
only be measured in nanoseconds. This is on a mac osx with 4 gb ram.

Download and use it and provide feedback!
