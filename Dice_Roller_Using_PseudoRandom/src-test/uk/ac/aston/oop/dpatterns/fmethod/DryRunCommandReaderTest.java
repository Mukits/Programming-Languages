package uk.ac.aston.oop.dpatterns.fmethod;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;

import org.junit.jupiter.api.Test;

public class DryRunCommandReaderTest {

	@Test
	public void createCommand() {
		DryRunCommandReader reader = new DryRunCommandReader();
		PrintStream oldOut = System.out;
		try {
			PrintStream mockOut = mock(PrintStream.class);
			System.setOut(mockOut);

			// Use values that will stand out
			int dx = 13, dy = 42;

			reader.createMovementCommand(dx, dy).run();
			verify(mockOut, description(
				String.format("When doing reader.createMovementCommand(%d, %d), the printed message should mention both dx and dy", dx, dy)
			)).println(argThat(new MovementMessageMatcher(dx, dy)));
		} finally {
			System.setOut(oldOut);
		}
		
	}
}
