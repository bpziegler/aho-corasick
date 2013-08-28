package org.arabidopsis.ahocorasick;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Iterator;

/**
 * Quick and dirty code: measures the amount of time it takes to construct an
 * AhoCorasick tree out of all the words in <tt>/usr/share/dict/words</tt>.
 */

public class TimeTrial {

	private static final String TEST_SEARCH_STR = "now is the time for all good men to come to the aid of their country.  the quick brown fox jumped over the lazy dog.";

	private static void runTest(boolean includeDict)
			throws FileNotFoundException, IOException {
		System.out.println();
		System.out.println("testing includeDict = " + includeDict);

		double startPrepareTime = System.nanoTime();

		AhoCorasick tree = new AhoCorasick();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream("c:/users/bziegler/Desktop/words.txt")));
		int numLine = 0;
		if (includeDict) {
			String line;
			while ((line = reader.readLine()) != null) {
				tree.add(line, line);
				numLine++;
			}
		}

		tree.add("lazy", "lazy");
		tree.add("quick", "quick");
		numLine += 2;

		System.out.println("Num Words to Match:  " + numLine);

		tree.prepare();
		double endPrepareTime = System.nanoTime();

		System.out.println("Prepare Time = "
				+ ((endPrepareTime - startPrepareTime) / (1000 * 1000))
				+ " milliseconds");

		double startTime = System.nanoTime();

		Iterator<SearchResult> results = tree.search(TEST_SEARCH_STR.toCharArray());
		while (results.hasNext()) {
			SearchResult sr = results.next();
			System.out.println(sr);
		}
		double endTime = System.nanoTime();
		System.out.println("Search Time = "
				+ ((endTime - startTime) / (1000 * 1000)) + " milliseconds");
	}

	static public void main(String[] args) throws IOException {
		for (int i = 0; i < 10; i++) {
			System.out.println();
			System.out.println("Test # " + i);
			runTest(false);
			runTest(true);
		}
	}
}
