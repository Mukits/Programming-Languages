package uk.ac.aston.oop.dpatterns.fmethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;

import org.junit.jupiter.api.Test;

public class ExecutingCommandReaderTest {

	@Test
	public void gettersSetters() {
		ExecutingCommandReader reader = new ExecutingCommandReader();

		assertEquals(0, reader.getX(), "The initial value of x should be 0");
		assertEquals(0, reader.getY(), "The initial value of y should be 0");

		final int finalX = 25;
		reader.setX(finalX);
		assertEquals(finalX, reader.getX(), String.format(
			"After calling setX(%d), getX() should return %d", finalX, finalX));

		final int finalY = 10;
		reader.setY(finalY);
		assertEquals(finalY, reader.getY(), String.format(
				"After calling setY(%d), getY() should return %d", finalY, finalY));
	}

	@Test
	public void createCommand() {
		ExecutingCommandReader reader = new ExecutingCommandReader();

		PrintStream oldOut = System.out;
		try {
			PrintStream mockOut = mock(PrintStream.class);
			System.setOut(mockOut);

			// Move one right
			final int dx1 = 1, dy1 = -2;
			reader.createMovementCommand(dx1, dy1).run();
			assertEquals(dx1, reader.getX(), String.format(
				"From a new reader, moving (%d, %d) should leave it at the expected X",
				dx1, dy1));
			assertEquals(dy1, reader.getY(), String.format(
				"From a new reader, moving (%d, %d) should leave it at the expected Y",
				dx1, dy1));
			verify(mockOut, description(String.format(
				"From a new reader, moving (%d, %d) should print a message with "
				+ "the new position", dx1, dy1))
			).println(argThat(new MovementMessageMatcher(dx1, dy1)));

			// Move two down
			final int dx2 = 4, dy2 = 1;
			reader.createMovementCommand(dx2, dy2).run();
			assertEquals(dx1 + dx2, reader.getX(), String.format(
				"From a new reader, moving (%d, %d) and then (%d, %d) should leave it at the expected X",
				dx1, dy1, dx2, dy2));
			assertEquals(dy1 + dy2, reader.getY(), String.format(
				"From a new reader, moving (%d, %d) and then (%d, %d) should leave it at the expected Y",
				dx1, dy1, dx2, dy2));
			verify(mockOut, description(String.format(
				"From a new reader, moving (%d, %d) and then (%d, %d) should "
				+ "print a message with the new position", dx1, dy1, dx2, dy2))
			).println(argThat(new MovementMessageMatcher(dx1 + dx2, dy1 + dy2)));
		} finally {
			System.setOut(oldOut);
		}

		
	}
}
