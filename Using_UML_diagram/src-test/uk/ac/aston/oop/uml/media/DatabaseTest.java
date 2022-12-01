package uk.ac.aston.oop.uml.media;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DatabaseTest {

	private Database db;

	@BeforeEach
	public void setup() {
		this.db = new Database();
	}

	@Test
	public void printNoItems() {
		assertPrintWorksAsExpected(0);
	}

	@Test
	public void printOneItem() {
		assertPrintWorksAsExpected(1);
	}

	@Test
	public void printTenItems() {
		assertPrintWorksAsExpected(10);
	}

	protected void assertPrintWorksAsExpected(int n) {
		PrintStream oldOut = System.out;
		try {
			PrintStream mockOut = mock(PrintStream.class);
			System.setOut(mockOut);

			final List<Item> mockItems = new ArrayList<>(n);
			for (int i = 0; i < n; i++) {
				Item mockItem = mock(Item.class);
				mockItems.add(mockItem);
				db.addItem(mockItem);
			}

			db.print();
			verify(mockOut,
				times(n).description("System.out.println should have been called " + n + " times")
			).println(anyString());

			// Mockito cannot verify toString()
		} finally {
			System.setOut(oldOut);
		}
	}
}
