package uk.ac.aston.oop.uml.media;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractItemSubclassTest<T extends Item> {

	protected T item;

	protected abstract String getItemTitle();
	protected abstract int getPlayMinutes();
	protected abstract T createInstance();

	@BeforeEach
	public void setup() {
		item = createInstance();
	}
	
	@Test
	public void title() {
		assertTrue(item.toString().contains(getItemTitle()),
			"The value returned by toString() should mention the title");
	}
	
	@Test
	public void playMinutes() {
		assertEquals(getPlayMinutes(), item.getPlayMinutes(),
			"We should be able to get the play minutes of the item");
		assertTrue(item.toString().contains(getPlayMinutes() + ""),
			"The value returned by toString() should mention the playing minutes");
	}
	
	@Test
	public void comment() {
		final String comment = "this is a comment";
		assertEquals("", item.getComment(),
			"An item should have an empty string as a comment by default");

		item.setComment(comment);
		assertEquals(comment, item.getComment(),
			"We should be able to get and set the comment for the item");
		assertTrue(item.toString().contains(comment),
			"The value returned by toString() should mention the comment");
	}
	
	@Test
	public void isOwned() {
		assertFalse(item.isOwned(), "An item should not be owned by default");
		assertFalse(item.toString().contains("*"),
			"The value returned by toString() should not contain * if it is not owned");

		item.setOwned(true);
		assertTrue(item.isOwned(),
			"We should be able to get and set the 'is owned' flag for the item");

		assertTrue(item.toString().contains("*"),
			"The value returned by toString() should contain * if it is owned");
	}
}
