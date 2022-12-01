package uk.ac.aston.oop.jcf.words;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Reads the words from a Hunspell word list file.
 * Ignores suffixes, and organizes by first letter.
 */
public class WordListSet {

	private Set<String> words;

	public WordListSet(InputStream is) throws IOException {
		try (Scanner sc = new Scanner(is)) {
			readWords(sc);
		}
	}

	private void readWords(Scanner sc) {
		// Ignore first line with number of words
		sc.nextLine();
		words = new HashSet<>();

		while (sc.hasNextLine()) {
			String line = sc.nextLine();

			/*
			 * Words may have a suffix ("word/suffix") or not.
			 * We split the word by / and take the first part. 
			 */
			String[] parts = line.split("/");
			words.add(parts[0]);
		}
	}

	public Set<String> searchWords(char firstLetter, String key) {
		final Set<String> results = new HashSet<>();
		for (String w : words) {
			if (w.startsWith(firstLetter + "") && w.contains(key)) {
				results.add(w);
			}
		}
		return results;
	}

}
