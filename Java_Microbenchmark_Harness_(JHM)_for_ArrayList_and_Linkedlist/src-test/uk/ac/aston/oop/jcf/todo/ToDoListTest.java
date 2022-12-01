package uk.ac.aston.oop.jcf.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ToDoListTest {

	private ToDoList list;
	private ToDoItem i1, i2, i3;

	@BeforeEach
	public void setup() {
		this.list = new ToDoList();
		i1 = todo("Do the laundry");
		i2 = todo("Wash the dishes");
		i3 = todo("Conquer the world");
	}
	
	@Test
	public void empty() {
		assertTrue(list.isEmpty(), "An empty list should be reported as such");
		assertEquals(0, list.size(), "An empty list should have size 0");

		Random mockRandom = mock(Random.class);
		assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
			list.shuffle(mockRandom);
		});
		verifyNoInteractions(mockRandom);

		// These shouldn't crash if the list is empty
		list.removeAllContaining("dummy");
		list.removeAllDone();
	}

	@Test
	public void markDone() {
		ToDoItem item = todo("Walk the dog");
		list.add(item);
		assertSame(item, list.items.get(0),
			"add should have added the item we provided to it");

		assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
			list.markDone(0);
		});
		assertTrue(item.isDone(),
			"markDone(0) should have marked the first item as done");
	}

	@Test
	public void removeInMiddle() {
		list.add(i1);
		list.add(i2);
		list.add(i3);

		list.remove(1);
		assertEquals(Arrays.asList(i1, i3), list.items,
			"Removing the middle item in a list of 3 should leave the first "
			+ "and last elements");
	}

	@Test
	public void removeAllContaining() {
		list.add(i1);
		list.add(i2);
		list.add(i3);

		assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
			list.removeAllContaining("dishes");
		});
		assertEquals(Arrays.asList(i1, i3), list.items,
			"After adding 'Wash the dishes', "
			+ "removeAllContaining(\"dishes\") should remove it");
	}

	@Test
	public void removeAllDone() {
		list.add(i1); i1.setDone(true);
		list.add(i2);
		list.add(i3); i3.setDone(true);

		assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
			list.removeAllDone();
		});
		assertEquals(Arrays.asList(i2), list.items,
			"After adding three items and marking the first and the last as "
			+ "done, removeAllDone() should only leave the one that has not "
			+ "been done yet");
	}

	@Test
	public void moveToTop() {
		list.add(i1);
		list.add(i2);
		list.add(i3);

		assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
			list.moveToTop(0);
		});
		assertEquals(Arrays.asList(i1, i2, i3), list.items,
			"Moving the first item of a list of 3 items to the top "
			+ "should not produce any changes");

		assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
			list.moveToTop(1);
		});
		assertEquals(Arrays.asList(i2, i1, i3), list.items,
			"Moving the second item of a list of 3 items to the top");

		assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
			list.moveToTop(1); // undo the previous change
			list.moveToTop(2);
		});
		assertEquals(Arrays.asList(i3, i1, i2), list.items,
			"Moving the third item of a list of 3 items to the top");
	}

	@Test
	public void shuffle() {
		list.add(i1);
		list.add(i2);
		list.add(i3);

		/*
		 * We create a fake Random that forces a specific
		 * permutation to be produced.
		 */
		Random rnd = mock(Random.class);
		when(rnd.nextInt(3))
			.thenReturn(1)
			.thenReturn(2)
			.thenReturn(2);

		assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
			list.shuffle(rnd);
		});
		verify(rnd, times(3).description(
			"Random should be used exactly once for each element in the list"
		)).nextInt(3);

		assertEquals(Arrays.asList(i2, i3, i1), list.items,
			"The random positions should be used to do swaps");
	}

	private ToDoItem todo(String description) {
		return new ToDoItem(description);
	}

}
