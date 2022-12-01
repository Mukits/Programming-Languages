package uk.ac.aston.oop.rdd.sim;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class CountAwareFoxTest {

	@Test
	public void huntWithTwoRabbits() {
		Field field = new Field(3, 4);
		Fox f = new Fox();
		field.add(f, 2, 2);

		Rabbit r = new Rabbit();
		field.add(r, 2, 3);

		Random rnd = mock(Random.class);
		f.setActorCount(Rabbit.class, 2);
		f.hunt(rnd);
		assertTrue(r.isAlive(),
			"If the Fox thinks there are only 2 rabbits remaining, the Fox won't hunt");
	}

	@Test
	public void huntWithTwentyRabbits() {
		Field field = new Field(3, 4);
		Fox f = new Fox();
		field.add(f, 2, 2);

		Rabbit r = new Rabbit();
		field.add(r, 2, 3);

		Random rnd = mock(Random.class);
		when(rnd.nextDouble())
			.thenReturn(0.201)  // will not hunt (slightly above the threshold)
			.thenReturn(0.199); // will hunt (right below the threshold)

		f.setActorCount(Rabbit.class, 20);
		f.hunt(rnd);
		assertTrue(r.isAlive(),
			"If the Fox thinks there are 20 rabbits and we roll 0.0201 out of 1, "
			+ "it won't hunt it: hunting probability should be 0.20");

		f.moveTo(field.getGrid().get(2, 2));
		f.hunt(rnd);
		assertFalse(r.isAlive(),
			"If the Fox thinks there are 20 rabbits and we roll 0.0199 out of 1, "
			+ "it will hunt it: hunting probability should be 0.20");
	}

	@Test
	public void huntWithManyRabbits() {
		Field field = new Field(3, 4);
		Fox f = new Fox();
		field.add(f, 2, 2);

		Rabbit r = new Rabbit();
		field.add(r, 2, 3);

		// The fox will first not hunt, and then hunt on its default probability
		Random rnd = mock(Random.class);
		when(rnd.nextDouble())
			.thenReturn(Fox.DEFAULT_HUNT_PROBABILITY + 0.001)
			.thenReturn(Fox.DEFAULT_HUNT_PROBABILITY - 0.001);

		f.setActorCount(Rabbit.class, 50);
		f.hunt(rnd);
		assertTrue(r.isAlive(),
			"If the Fox thinks there are many rabbits and we roll above the default hunting probability, "
			+ "it won't hunt it");

		f.moveTo(field.getGrid().get(2, 2));
		f.hunt(rnd);
		assertFalse(r.isAlive(),
			"If the Fox thinks there are many rabbits and we roll below the default hunting probability, "
			+ "it will hunt it");
	}
	
}
