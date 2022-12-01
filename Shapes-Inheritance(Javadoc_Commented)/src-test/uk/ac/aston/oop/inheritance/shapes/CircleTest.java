package uk.ac.aston.oop.inheritance.shapes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.io.PrintStream;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import uk.ac.aston.oop.inheritance.util.GraphicsContextWrapper;

public class CircleTest extends AbstractShapeSubclassTest {

	public CircleTest() {
		super(Circle.class, Ellipse.class);
	}

	@Test
	public void circleReusesEllipseDraw() throws Exception {
		Method mDraw = Circle.class.getMethod("draw", GraphicsContextWrapper.class);
		assertEquals("Ellipse", mDraw.getDeclaringClass().getSimpleName(),
			"Circle should not define its own draw method, but instead reuse the one in Ellipse");
	}

	@Test
	public void circleDoesNotHaveUselessFields() throws Exception {
		for (final String fieldName : new String[] { "centerX", "centerY", "radius" }) {
			assertThrows(NoSuchFieldException.class, () -> {
				Circle.class.getDeclaredField(fieldName);
			}, "Circle does not need the field " + fieldName + " anymore");
		}
	}

	@Test
	public void circleDrawUsesOval() throws Exception {
		PrintStream oldOut = System.out;
		try {
			PrintStream mockOut = mock(PrintStream.class);
			System.setOut(mockOut);

			Circle r = new Circle(10, 20, 5);
			GraphicsContextWrapper gc = mock(GraphicsContextWrapper.class);
			r.draw(gc);

			assertEquals(5, r.getX(), "The upper left X coordinate of a circle centered at (10, 20) with radius 5 should be 5");
			assertEquals(15, r.getY(), "The upper left Y coordinate of a circle centered at (10, 20) with radius 5 should be 15");
			assertEquals(10, r.getWidth(), "The width of a circle with radius 5 should be 10");
			assertEquals(10, r.getHeight(), "The height of a circle with radius 5 should be 10");
			assertEquals(5, r.getRadius(), "If a Circle was constructed with radius 5, getRadius() should return 5");

			verifyNoInteractions(mockOut);
			verify(gc).oval(5, 15, 10, 10);
		} finally {
			System.setOut(oldOut);
		}
	}
	

}
