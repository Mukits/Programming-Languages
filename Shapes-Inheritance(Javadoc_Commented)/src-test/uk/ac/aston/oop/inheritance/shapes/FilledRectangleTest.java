package uk.ac.aston.oop.inheritance.shapes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Constructor;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import javafx.scene.paint.Color;
import uk.ac.aston.oop.inheritance.util.GraphicsContextWrapper;

public class FilledRectangleTest extends AbstractShapeSubclassTest {

	public FilledRectangleTest() throws ClassNotFoundException {
		super(getFilledRectangleClass(), Rectangle.class);
	}

	private static Class<?> getFilledRectangleClass() throws ClassNotFoundException {
		return Class.forName("uk.ac.aston.oop.inheritance.shapes.FilledRectangle");
	}

	@Test
	public void constructorShouldTakeColorAsFirstArgument() throws Exception {
		assertDoesNotThrow(() -> {
			getFilledRectangleConstructor();
		}, "FilledRectangle should have a 5-arg constructor that takes a Color as first argument");
	}

	private Constructor<?> getFilledRectangleConstructor() throws Exception {
		return getFilledRectangleClass().getConstructor(Color.class, double.class, double.class, double.class, double.class);
	}

	@Test
	public void shouldDrawBorderAndFill() throws Exception {
		Object oRectangle = getFilledRectangleConstructor().newInstance(Color.RED, 1, 2, 3, 4);

		GraphicsContextWrapper mockGC = mock(GraphicsContextWrapper.class);
		oRectangle.getClass().getMethod("draw", GraphicsContextWrapper.class).invoke(oRectangle, mockGC);

		InOrder inOrder = inOrder(mockGC);
		inOrder.verify(mockGC).lineWidth(5);
		inOrder.verify(mockGC).rect(1, 2, 3, 4);
		inOrder.verify(mockGC).fill(Color.RED);
		inOrder.verify(mockGC).fillRect(1, 2, 3, 4);
	}
}
