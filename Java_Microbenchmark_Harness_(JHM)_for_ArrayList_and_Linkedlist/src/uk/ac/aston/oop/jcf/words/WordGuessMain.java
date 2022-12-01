package uk.ac.aston.oop.jcf.words;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Set;

public class WordGuessMain {
	/*
	 * Path to resource with the Hunspell British English dictionary,
	 * with -ise word endings:
	 *
	 * http://wordlist.aspell.net/dicts/
	 */
	private static final String DICT_RESOURCE_PATH = "/en_GB-ise.dic";

	private final WordList wordList;
	private final Scanner sc;

	private char firstLetter;
	private Set<String> candidates;

	public WordGuessMain(WordList wl, Scanner sc) {
		this.wordList = wl;
		this.sc = sc;
	}
	
	public void run() {
		buildCandidateSet();
		askUserToGuess();
	}

	protected void buildCandidateSet() {
		firstLetter = promptCharacter("Enter the first letter: ");
		String initialSearch = promptString("Enter an initial search: ");
		candidates = wordList.searchWords(firstLetter, initialSearch);

		boolean done = false;
		while (!done) {
			System.out.println(candidates.size() + " candidates currently selected");
			String pattern = promptString(
				"Enter a command (+WORD, -WORD, *WORD) or enter an empty line to switch to guessing\n>");
			done = processLine(pattern);
		}
	}

	protected void askUserToGuess() {
		if (candidates.isEmpty()) {
			System.out.println("No candidates left: cannot do guess");
		} else {
			String word = promptString("Guess among " + candidates.size() + " candidates: ");
			if (candidates.contains(word)) {
				System.out.println("Correct - " + word + " is in the set!");
			} else {
				System.out.println("Incorrect - " + word + " is not in the set.");
				System.out.println("Valid options were:");
				for (String w : candidates) {
					System.out.println("* " + w);
				}
			}
		}
	}

	protected boolean processLine(String pattern) {
		if (pattern.isEmpty()) {
			return true;
		}

		final char cmd = pattern.charAt(0);
		final String substring = pattern.substring(1);

		if (cmd == '+') {
			Set<String> subset = wordList.searchWords(firstLetter, substring);
			candidates.addAll(subset);
		} else if (cmd == '-') {
			Set<String> subset = wordList.searchWords(firstLetter, substring);
			candidates.removeAll(subset);
		} else if (cmd == '*') {
			Set<String> subset = wordList.searchWords(firstLetter, substring);
			candidates.retainAll(subset);
		} else {
			System.out.println(
				"Command '" + cmd + "' not recognized: must start with +/-/* or be an empty line");
		}

		return false;
	}

	private char promptCharacter(String question) {
		System.out.print(question);

		// Read a single character and throw away the rest of the line
		char c = sc.findInLine(".").charAt(0);
		sc.nextLine();
		return c;
	}

	private String promptString(String question) {
		System.out.print(question);
		return sc.nextLine().strip();
	}

	public static void main(String[] args) {
		final InputStream dictStream =
			WordGuessMain.class.getResourceAsStream(DICT_RESOURCE_PATH);

		try (Scanner sc = new Scanner(System.in)) {
			final WordList wl = new WordList(dictStream);
			new WordGuessMain(wl, sc).run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
