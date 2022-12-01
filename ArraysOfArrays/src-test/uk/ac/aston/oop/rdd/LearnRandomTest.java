package uk.ac.aston.oop.rdd;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class LearnRandomTest {

	@Test
	public void useRandom() {
		Random rnd = mock(Random.class);
		LearnRandom.useRandom(rnd);

		verify(rnd, times(10)
			.description("nextDouble should have been called 10 times"))
			.nextDouble();

		verify(rnd, times(10)
			.description("nextInt(6) should should have been called 10 times"))
			.nextInt(6);
	}

}
