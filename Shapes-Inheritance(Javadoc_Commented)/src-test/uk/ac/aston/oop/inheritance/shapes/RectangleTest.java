package uk.ac.aston.oop.inheritance.shapes;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import uk.ac.aston.oop.inheritance.util.GraphicsContextWrapper;

/**
 * Tests for the {@link Rectangle} class.
 */
public class RectangleTest extends AbstractShapeSubclassTest {

	public RectangleTest() throws ClassNotFoundException {
		super(Rectangle.class);
	}

	@Test
	public void rectangleDrawUsesRect() throws Exception {
		PrintStream oldOut = System.out;
		try {
			PrintStream mockOut = mock(PrintStream.class);
			System.setOut(mockOut);
		
			Rectangle r = new Rectangle(1, 2, 3, 4);
			GraphicsContextWrapper gc = mock(GraphicsContextWrapper.class);
			r.draw(gc);

			verifyNoInteractions(mockOut);
			verify(gc).rect(1, 2, 3, 4);
		} finally {
			System.setOut(oldOut);
		}
	}

}
