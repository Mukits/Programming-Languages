package uk.ac.aston.oop.dpatterns.fmethod;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

public class AbstractCommandReaderTest {

	public static class DummyCommandReader extends AbstractCommandReader {
		@Override
		protected Runnable createMovementCommand(int dx, int dy) {
			return () -> {};
		}
	}

	@Test
	public void hasCreateMethod() throws SecurityException {
		try {
			Method m = AbstractCommandReader.class.getDeclaredMethod(
				"createMovementCommand", int.class, int.class);
			assertSame(Runnable.class, m.getReturnType(),
				"createMovementCommand should return a Runnable");
			assertTrue((m.getModifiers() & Modifier.ABSTRACT) != 0,
				"createMovementCommand should be abstract");
		} catch (NoSuchMethodException e) {
			fail("AbstractCommandReader should have a createMovementCommand(int, int) method");
		}
	}

	@Test
	public void processLineMovement() {
		assertMovementCommandWorks("left", -1, 0);
		assertMovementCommandWorks("right", 1, 0);
		assertMovementCommandWorks("down", 0, 1);
		assertMovementCommandWorks("up", 0, -1);
	}

	@Test
	public void processLineExit() {
		AbstractCommandReader reader = createReaderSpy();
		assertTrue(reader.processLine("exit"), "exit should have the loop stop");
		verify(reader, times(0).description("exit should not create any commands"))
			.createMovementCommand(anyInt(), anyInt());
	}

	@Test
	public void processLineNotRecognized() {
		AbstractCommandReader reader = createReaderSpy();

		PrintStream oldErr = System.err;
		try {
			PrintStream mockErr = mock(PrintStream.class);
			System.setErr(mockErr);

			assertFalse(reader.processLine("boo"),
				"Unrecognized value should not have the loop stop");

			verify(mockErr, description(
				"An unrecognized value should make the reader complain "
				+ "to System.err")
			).println(anyString());

			verify(reader, times(0).description(
				"An unrecognized value should not create any commands"
			)).createMovementCommand(anyInt(), anyInt());
		} finally {
			System.setErr(oldErr);
		}
	}

	protected void assertMovementCommandWorks(String line, int expectedDX, int expectedDY) {
		AbstractCommandReader reader = createReaderSpy();
		assertFalse(reader.processLine(line), line + " should let the loop continue");
		verify(
			reader,
			description(String.format("%s should create a movement command with (%d, %d)", line, expectedDX, expectedDY))
		).createMovementCommand(expectedDX, expectedDY);
	}

	protected AbstractCommandReader createReaderSpy() {
		return spy(new DummyCommandReader());
	}

}
