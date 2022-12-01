package uk.ac.aston.oop.dpatterns.singleton;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;

import org.junit.jupiter.api.Test;

/**
 * This needs to go into its own class, as we need to start from a
 * fresh Surefire JVM to test the behaviour around the DiceRoller
 * static 'seed' field.
 *
 * When working from Eclipse, this tests needs to be run on its own.
 * It will not work when run together with DiceRollerTest.
 */
public class DiceRollerSetSeedTest {

	@Test
	public void setSeedComplainsAfterInstanceCreated() {
		PrintStream oldOut = System.err;
		try {
			PrintStream mockOut = mock(PrintStream.class);
			System.setErr(mockOut);

			DiceRoller.setSeed(42);
			verify(mockOut, times(0)
				.description("DiceRoller.setSeed should not complain when "
					+ "setting a seed if the instance has not been "
					+ "created yet from getInstance()")
			).println(anyString());

			DiceRoller.getInstance();
			DiceRoller.setSeed(60);
			verify(mockOut,
				description("DiceRoller.setSeed should complain to System.err "
						+ "when setting a seed after the instance has been "
						+ "created from getInstance()")
			).println(anyString());
		} finally {
			System.setOut(oldOut);
		}
	}

}
