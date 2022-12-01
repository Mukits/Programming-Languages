package uk.ac.aston.oop.jcf.words;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;

public class WordListTest {

	private static final String[] DUMMY_WORDS = {
		"6", "this", "is", "a", "dull", "dummy", "list"
	};

	@Test
	public void searchNoResults() throws IOException {
		WordList wl = createDummyList();
		assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
			Set<String> results = wl.searchWords('x', "dummy");
			assertEquals(0, results.size(), String.format(
				"Doing searchWords('x', \"dummy\") on %s should produce no results",
				Arrays.toString(DUMMY_WORDS)
			));
		});
	}

	@Test
	public void searchOneResult() throws IOException {
		WordList wl = createDummyList();
		assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
			Set<String> results = wl.searchWords('t', "his");
			assertEquals(Collections.singleton("this"), results, String.format(
				"Doing searchWords('t', \"his\") on %s should produce 1 result",
				Arrays.toString(DUMMY_WORDS)
			));
		});
	}

	@Test
	public void searchMultipleResults() throws IOException {
		WordList wl = createDummyList();
		assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
			Set<String> results = wl.searchWords('d', "du");
			assertEquals(new HashSet<>(Arrays.asList("dull", "dummy")),
				results, String.format(
				"Doing searchWords('d', \"du\") on %s should produce 2 results",
				Arrays.toString(DUMMY_WORDS)
			));
		});
	}

	@Test
	public void searchBetweenMultipleWordsWithSameInitial() throws IOException {
		WordList wl = createDummyList();
		assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
			Set<String> results = wl.searchWords('d', "um");
			assertEquals(Collections.singleton("dummy"),
				results, String.format(
				"Doing searchWords('d', \"um\") on %s should produce 1 result",
				Arrays.toString(DUMMY_WORDS)
			));
		});
	}

	@Test
	public void shouldBeUsingMap() throws FileNotFoundException {
		CompilationUnit cu = StaticJavaParser.parse(new File(
			"src/uk/ac/aston/oop/jcf/words/WordList.java"));
		ClassOrInterfaceDeclaration klass = cu.getClassByName("WordList").get();

		final String expected = "Map<Character,Set<String>>";
		boolean hasMap = false;
		for (FieldDeclaration f : klass.getFields()) {
			for (VariableDeclarator v : f.getVariables()) {
				final String sType = v.getTypeAsString();

				if (!expected.equals(sType) && !("java.util." + expected).equals(sType)) {
					fail(String.format("WordList should only have a %s field "
						+ "by the end of the lab, but you had a %s", expected, sType));
				} else {
					hasMap = true;
				}
			}
		}

		assertTrue(hasMap, "WordList should have a "
			+ expected + " field grouping the words by initial");
	}
	
	protected WordList createDummyList() throws IOException {
		String sLines = String.join("\n", DUMMY_WORDS);
		byte[] bytes = sLines.getBytes(StandardCharsets.UTF_8);
		WordList wl = new WordList(new ByteArrayInputStream(bytes));
		return wl;
	}
}
