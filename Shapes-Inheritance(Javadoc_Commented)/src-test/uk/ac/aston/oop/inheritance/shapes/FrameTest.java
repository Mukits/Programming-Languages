package uk.ac.aston.oop.inheritance.shapes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import uk.ac.aston.oop.inheritance.util.GraphicsContextWrapper;

public class FrameTest extends AbstractShapeSubclassTest {

	public FrameTest() throws ClassNotFoundException {
		super(getFrameClass());
	}

	private static Class<?> getFrameClass() throws ClassNotFoundException {
		return Class.forName("uk.ac.aston.oop.inheritance.shapes.Frame");
	}

	@Test
	public void frameHasRectangleFields() throws Exception {
		Object f = getFrameClass().getConstructors()[0].newInstance(1, 2, 3, 4);
		for (String fieldName : new String[] { "outerRectangle", "innerRectangle" }) {
			try {
				Field expectedField = f.getClass().getDeclaredField(fieldName);
				assertEquals("Rectangle", expectedField.getType().getSimpleName(),
					"Field " + fieldName + " should be of type Rectangle");
				assertNotNull(expectedField.get(f),
					"Field " + fieldName + " should have been set to an instance of Rectangle by the constructor");
			} catch (NoSuchFieldException ex) {
				fail("Expected Frame to have a Rectangle field called " + fieldName);
			} catch (IllegalAccessException e) {
				fail("Field " + fieldName + " should be protected (accessible from subclasses and same package)");
			}
		}
	}

	@Test
	public void frameDrawsTwoRectangles() throws Exception {
		Object f = getFrameClass().getConstructors()[0].newInstance(100, 200, 50, 60);
		GraphicsContextWrapper mockGC = mock(GraphicsContextWrapper.class);
		f.getClass().getMethod("draw", GraphicsContextWrapper.class).invoke(f, mockGC);
		verify(mockGC).rect(100, 200, 50, 60);
		verify(mockGC).rect(110, 210, 30, 40);
	}

	@Test
	public void frameOnlyReusesRectangle() throws Exception {
		Object f = getFrameClass().getConstructors()[0].newInstance(1, 2, 3, 4);

		Field fldInner = f.getClass().getDeclaredField("innerRectangle");
		Field fldOuter = f.getClass().getDeclaredField("outerRectangle");

		Rectangle mockInner = mock(Rectangle.class);
		Rectangle mockOuter = mock(Rectangle.class);
		GraphicsContextWrapper mockGC = mock(GraphicsContextWrapper.class);

		fldInner.set(f, mockInner);
		fldOuter.set(f, mockOuter);
		f.getClass().getMethod("draw", GraphicsContextWrapper.class).invoke(f, mockGC);

		verifyNoInteractions(mockGC);
		verify(mockInner).draw(mockGC);
		verify(mockOuter).draw(mockGC);
	}
}
