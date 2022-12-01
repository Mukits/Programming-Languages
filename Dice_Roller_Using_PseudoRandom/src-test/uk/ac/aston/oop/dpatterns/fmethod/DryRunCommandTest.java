package uk.ac.aston.oop.dpatterns.fmethod;

import static org.mockito.Mockito.description;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;

import org.junit.jupiter.api.Test;

public class DryRunCommandTest {

	@Test
	public void messageIsPrinted() {
		PrintStream oldOut = System.out;
		try {
			PrintStream mockOut = mock(PrintStream.class);
			System.setOut(mockOut);

			DryRunCommand cmd = new DryRunCommand("test");
			cmd.run();
			verify(mockOut, description(
				"A DryRunCommand should just print the message it was given "
				+ "to System.out")
			).println("test");
		} finally {
			System.setOut(oldOut);
		}
	}

}
