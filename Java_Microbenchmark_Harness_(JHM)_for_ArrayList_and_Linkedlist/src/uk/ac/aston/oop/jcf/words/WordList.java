package uk.ac.aston.oop.jcf.words;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Reads the words from a Hunspell word list file.
 * Ignores suffixes, and organizes by first letter.
 */
public class WordList {

	private Map<Character, Set<String>> wordsByLetter;

	public WordList(InputStream is) throws IOException {
		try (Scanner sc = new Scanner(is)) {
			readWords(sc);
		}
	}

	private void readWords(Scanner sc) {
		// Ignore the number of words
		sc.nextLine();

		wordsByLetter = new HashMap<>();
		while (sc.hasNextLine()) {
			String line = sc.nextLine();

			/*
			 * Words may have a suffix ("word/suffix") or not.
			 * We split the word by / and take the first part. 
			 */
			String[] parts = line.split("/");
			String word = parts[0];

			char firstLetter = word.charAt(0);
			Set<String> words = wordsByLetter.get(firstLetter);
			if (words == null) {
				words = new HashSet<>();
				wordsByLetter.put(firstLetter, words);
			}
			words.add(word);
		}
	}

	public Set<String> searchWords(char firstLetter, String key) {
		final Set<String> results = new HashSet<>();
		Set<String> words = wordsByLetter.getOrDefault(firstLetter, Collections.emptySet());

		for (String w : words) {
			if (w.contains(key)) {
				results.add(w);
			}
		}
		return results;
	}

}
