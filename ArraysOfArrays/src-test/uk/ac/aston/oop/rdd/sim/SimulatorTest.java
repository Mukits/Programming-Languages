package uk.ac.aston.oop.rdd.sim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class SimulatorTest {

	@Test
	public void populate() {
		Random rnd = mock(Random.class);
		Simulator s = new Simulator(rnd, new Field(3, 3));

		/*
		 * Set up probability and mock Random to force a Rabbit to appear on the top
		 * left, then a Fox, then nothing
		 */
		s.setFoxProbability(0.2);
		s.setRabbitProbability(0.3);
		when(rnd.nextDouble())
			.thenReturn(s.getRabbitProbability() - 0.01) // rabbit
			.thenReturn(s.getRabbitProbability() + 0.01) // fox
			.thenReturn(s.getRabbitProbability() + s.getFoxProbability()); // nothing

		s.populate();

		Grid grid = s.getField().getGrid();
		List<Actor> topLeftAnimals = grid.get(0, 0).getContents();
		assertEquals(1, topLeftAnimals.size(),
			"There should be 1 Rabbit on the top left - count check");
		assertEquals(Rabbit.class, topLeftAnimals.get(0).getClass(),
			"There should be 1 Rabbit on the top left - class check");

		List<Actor> topSecondAnimals = grid.get(0, 1).getContents();
		assertEquals(1, topSecondAnimals.size(),
			"There should be 1 Fox on the second cell of the top row - count check");
		assertEquals(Fox.class, topSecondAnimals.get(0).getClass(),
			"There should be 1 Fox on the second cell of the top row - class check");

		assertTrue(grid.get(0, 2).getContents().isEmpty(),
			"The rest of the grid should be empty");
	}

	@Test
	public void act() {
		Random rnd = new Random();
		Simulator s = new Simulator(rnd, new Field(3, 3));
		assertEquals(0, s.getStep(),
			"When created, the simulator is on step 0");

		Animal mockAnimal = mock(Animal.class);
		s.getField().add(mockAnimal, 0, 0);
		s.act();
		verify(mockAnimal).act(rnd);
		assertEquals(1, s.getStep(),
			"After a step, the simulator is on step 1");
	}
}
