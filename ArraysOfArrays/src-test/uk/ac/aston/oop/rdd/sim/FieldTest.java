package uk.ac.aston.oop.rdd.sim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class FieldTest {

	@Test
	public void fieldSizes() {
		Field f = new Field(3, 4);
		assertEquals(3, f.getGrid().getHeight(),
			"A field of 3 rows and 4 columns should have a grid of height 3");
		assertEquals(4, f.getGrid().getWidth(),
				"A field of 3 rows and 4 columns should have a grid of width 4");
	}

	@Test
	public void addAnimal() {
		Field f = new Field(3, 4);
		Fox fox = new Fox();
		f.add(fox, 2, 1);
		assertSame(f.getGrid().get(2, 1), fox.getCell(),
			"After adding an animal to a field, the animal knows where it is");
		assertTrue(f.getGrid().get(2, 1).getContents().contains(fox),
			"After adding an animal to a field, the cell contains the animal");
		assertTrue(f.getContents().contains(fox),
			"After adding an animal to a field, the field knows about the animal");
		assertSame(f, fox.getField(),
			"After adding an animal to a field, the animal knows about the field");
	}

	@Test
	public void removeAnimal() {
		Field f = new Field(3, 4);
		Rabbit r = new Rabbit();
		f.add(r, 1, 1);

		// Removing when it was in the field should work
		f.remove(r);
		assertNull(r.getCell(),
			"After removing an animal from a field, the animal should not be in any cell anymore");
		assertFalse(f.getGrid().get(1, 1).getContents().contains(r),
			"After removing an animal from a field, the animal should not belong to its original cell");
		assertNull(r.getField(),
			"After removing an animal from a field, animal.getField() should return null");

		// Removing it when it is not in the field should be safe (not crash)
		try {
			f.remove(r);
		} catch (Exception e) {
			fail("Removing the animal when it is not in the field should be safe, but crashed with a " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void countAnimals() {
		Field f = new Field(3, 4);

		assertEquals(0, f.getCount(Rabbit.class),
			"Initially, there are no rabbits in the simulation");
		assertEquals(0, f.getCount(Fox.class),
			"Initially, there are no foxes in the simulation");

		Fox fox = new Fox();
		Rabbit rabbit = new Rabbit();
		f.add(fox, 1, 1);
		f.add(rabbit, 2, 1);
		f.add(new Rabbit(), 2, 2);

		assertEquals(2, f.getCount(Rabbit.class),
			"After adding 2 rabbits, Field should say there are 2");
		assertEquals(1, f.getCount(Fox.class),
			"After adding 1 fox, Field should say there is 1");

		f.remove(fox);
		f.remove(rabbit);

		assertEquals(1, f.getCount(Rabbit.class),
			"After adding 2 rabbits and removing 1, Field should say there are 1");
		assertEquals(0, f.getCount(Fox.class),
			"After adding 1 fox and removing it, Field should say there are none");
	}
}
