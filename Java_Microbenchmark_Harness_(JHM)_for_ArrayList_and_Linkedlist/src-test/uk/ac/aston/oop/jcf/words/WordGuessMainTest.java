package uk.ac.aston.oop.jcf.words;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

public class WordGuessMainTest {

	private static final String[] DUMMY_WORDS = {
		"6", "happy", "hoppy", "hippy", "hyper", "hypocritical", "hippos" 
	};
	
	@Test
	public void straightGuess() throws IOException {
		WordList wl = createDummyList();

		// This should straight up tell us we're correct
		final String[] input = {"h", "p", "", "happy"};
		Scanner sc = new Scanner(String.join("\n", input));
		assertInSession(wl, sc, (mockOut) -> {
			verify(mockOut, description(String.format(
				"Having read %s, inputting the lines %s should result in a list of 6 candidate words",
				Arrays.toString(DUMMY_WORDS), Arrays.toString(input)
			))).println(matches("6\\b.*\\bcandidates"));
			verify(mockOut, description(String.format(
				"Having read %s, inputting the lines %s should result in a correct guess being reported",
				Arrays.toString(DUMMY_WORDS), Arrays.toString(input)
			))).println(matches("\\b[Cc]orrect"));
		});
	}

	@Test
	public void incorrectGuess() throws Exception {
		WordList wl = createDummyList();

		// Oops, we guessed wrong!
		final String[] input = {"h", "p", "", "potato"};
		Scanner sc = new Scanner(String.join("\n", input));
		assertInSession(wl, sc, (mockOut) -> {
			verify(mockOut, description(String.format(
				"Having read %s, inputting the lines %s should result in a list of 6 candidate words",
				Arrays.toString(DUMMY_WORDS), Arrays.toString(input)
			))).println(matches("6\\b.*\\bcandidates"));
			verify(mockOut, description(String.format(
				"Having read %s, inputting the lines %s should result in an incorrect guess being reported",
				Arrays.toString(DUMMY_WORDS), Arrays.toString(input)
			))).println(matches("\\b[Ii]ncorrect"));
		});
	}

	@Test
	public void noCandidatesLeft() throws Exception {
		WordList wl = createDummyList();

		// Oops, we guessed wrong!
		final String[] input = {"h", "p", "*x", "", ""};
		Scanner sc = new Scanner(String.join("\n", input));
		assertInSession(wl, sc, (mockOut) -> {
			verify(mockOut, description(String.format(
				"Having read %s, inputting the lines %s should result in no candidates being left",
				Arrays.toString(DUMMY_WORDS), Arrays.toString(input)
			))).println(matches("[Nn]o\\b.*\\bcandidates"));
		});
	}

	@Test
	public void notRecognized() throws Exception {
		WordList wl = createDummyList();

		// Oops, we guessed wrong!
		final String[] input = {"h", "p", "?x", "", "blah"};
		Scanner sc = new Scanner(String.join("\n", input));
		assertInSession(wl, sc, (mockOut) -> {
			verify(mockOut, description(String.format(
				"Having read %s, inputting the lines %s should result in a warning about a command that is 'not recognized'",
				Arrays.toString(DUMMY_WORDS), Arrays.toString(input)
			))).println(matches("not.*recogni[zs]ed"));
			verify(mockOut, description(String.format(
				"Having read %s, inputting the lines %s should mention the unrecognized command \"?x\"",
				Arrays.toString(DUMMY_WORDS), Arrays.toString(input)
			))).println(contains("?"));
		});
	}

	@Test
	public void add() throws Exception {
		WordList wl = createDummyList();

		// Oops, we guessed wrong!
		final String[] input = {"h", "q", "+ppy", "", "hippy"};
		Scanner sc = new Scanner(String.join("\n", input));
		assertInSession(wl, sc, (mockOut) -> {
			verify(mockOut, description(String.format(
				"Having read %s, inputting the lines %s should result in an correct guess being reported",
				Arrays.toString(DUMMY_WORDS), Arrays.toString(input)
			))).println(matches("\\b[Cc]orrect"));
		});
	}

	@Test
	public void remove() throws Exception {
		WordList wl = createDummyList();

		// Oops, we guessed wrong!
		final String[] input = {"h", "p", "-ppy", "", "hippy"};
		Scanner sc = new Scanner(String.join("\n", input));
		assertInSession(wl, sc, (mockOut) -> {
			verify(mockOut, description(String.format(
				"Having read %s, inputting the lines %s should result in an incorrect guess being reported",
				Arrays.toString(DUMMY_WORDS), Arrays.toString(input)
			))).println(matches("\\b[iI]ncorrect"));
		});
	}

	protected void assertInSession(WordList wl, Scanner sc, Consumer<PrintStream> r) {
		PrintStream oldOut = System.out;
		try {
			PrintStream mockOut = mock(PrintStream.class);
			System.setOut(mockOut);
			assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
				new WordGuessMain(wl, sc).run();
				r.accept(mockOut);
			});
		} finally {
			System.setOut(oldOut);
		}
	}

	protected WordList createDummyList() throws IOException {
		String sLines = String.join("\n", DUMMY_WORDS);
		byte[] bytes = sLines.getBytes(StandardCharsets.UTF_8);
		WordList wl = new WordList(new ByteArrayInputStream(bytes));
		return wl;
	}
}
