package uk.ac.aston.oop.rdd.sim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class FoxTest {

	@Test
	public void foxAge() {
		Field field = new Field(4, 4);
		Fox f = new Fox();
		field.add(f, 2, 2);

		assertEquals(0, f.getAge(), "Initial age for a fox is 0");
		assertTrue(f.isAlive(), "Initially, the fox is alive");
		f.setAge(f.getLifespan() + 1);
		assertFalse(f.isAlive(), "The fox can die of old age");
		assertNull(f.getCell(), "After dying, the fox is removed from the grid");
		assertNull(f.getField(), "After dying, the fox is removed from the field");
	}

	@Test
	public void foxBreed() {
		Field field = new Field(4, 4);
		Fox f = new Fox();
		field.add(f, 2, 2);

		// We create a fake Random, which we control
		Random mockRandom = mock(Random.class);

		// We force the breeding to happen 
		when(mockRandom.nextDouble()).thenReturn(0.01);
		// Out of the max litter size, 2 foxes should be born
		when(mockRandom.nextInt(f.getMaxLitterSize())).thenReturn(2);
		// From the first 8 free adjacent cells, pick the first one
		when(mockRandom.nextInt(8)).thenReturn(0);
		// From the remaining 7 free adjacent cells, pick the first one again
		when(mockRandom.nextInt(7)).thenReturn(0);

		f.breed(mockRandom);
		verify(mockRandom, description(
			"The random probability should have been applied")
		).nextDouble();
		verify(mockRandom, description(
			"nextInt(getMaxLitterSize()) should have been called"
		)).nextInt(f.getMaxLitterSize());
		verify(mockRandom, description(
			"nextInt(freeAdjacentSize) should have been called a first time, when there were 8 free adjacent cells"
		)).nextInt(8);
		verify(mockRandom, description(
			"nextInt(freeAdjacentSize) should have been called a second time, when there were 7 free adjacent cells"
		)).nextInt(7);

		assertEquals(3, field.getCount(Fox.class),
			"After the litter has been born, there should be 3 foxes");
	}

	@Test
	public void foxMove() {
		Field field = new Field(4, 4);
		Grid grid = field.getGrid();
		GridCell start = grid.get(2, 2);
		Fox f = new Fox();
		field.add(f, start.getRow(), start.getColumn());

		GridCell target = grid.get(start.getRow(), start.getColumn() - 1);
		f.moveTo(target);

		assertEquals(2, f.getCell().getRow());
		assertEquals(1, f.getCell().getColumn());
		assertTrue(target.getContents().contains(f),
			"After moving an animal, the animal is in the list of animals of the target cell");
		assertFalse(start.getContents().contains(f),
			"After moving an animal, the animal is in the list of animals of the starting cell");
	}

	@Test
	public void foxHunt() {
		Field field = new Field(4, 4);

		// Rabbit starts too far away
		Fox f = new Fox();
		Rabbit r = new Rabbit();
		field.add(f, 2, 2);
		field.add(r, 2, 0);

		Random rnd = new Random();
		f.hunt(rnd);
		assertTrue(r.isAlive(),
			"Rabbit is still alive if the fox is not adjacent to it");

		// Move the fox back to where it was, and now put the rabbit adjacent to it
		f.moveTo(field.getGrid().get(2, 2));
		GridCell targetRabbit = field.getGrid().get(2, 3);
		r.moveTo(targetRabbit);
		f.hunt(rnd);
		assertFalse(r.isAlive(),
			"After moving the rabbit to be adjacent to the fox, the fox killed it");
		assertSame(targetRabbit, f.getCell(),
			"The fox should have moved to where the rabbit was while killing it");
	}
}
