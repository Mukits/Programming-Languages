package uk.ac.aston.oop.uml.media;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

public class ItemClassesTest {

	@Test
	public void itemIsAbstract() {
		assertTrue(
			(Item.class.getModifiers() & Modifier.ABSTRACT) > 0,
			"Item should be abstract");
	}

	@Test
	public void videoExtendsItem() {
		assertClassExtendsItem(Video.class);
	}

	@Test
	public void cdExtendsItem() {
		assertClassExtendsItem(CD.class);
	}

	protected void assertClassExtendsItem(Class<?> itemClass) {
		Class<?> superClass = itemClass.getSuperclass();
		assertNotNull(superClass, itemClass.getSimpleName() + " should extend Item");
		assertEquals("uk.ac.aston.oop.uml.media.Item",
			superClass.getName(), itemClass.getSimpleName() + " should extend Item");
	}

}
