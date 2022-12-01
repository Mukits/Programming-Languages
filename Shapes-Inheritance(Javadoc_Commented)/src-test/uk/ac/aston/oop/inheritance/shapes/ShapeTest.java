package uk.ac.aston.oop.inheritance.shapes;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import uk.ac.aston.oop.inheritance.util.GraphicsContextWrapper;

/**
 * Tests for the {@link Shape} class.
 */
public class ShapeTest {

	@Test
	public void shapeExists() {
		getShapeClass(); 
	}

	@Test
	public void shapeHasConstructor() throws Exception {
		Class<?> klass = getShapeClass();
		try {
			klass.getConstructor(
				double.class, double.class, double.class, double.class);
		} catch (NoSuchMethodException ex) {
			fail("Shape should have a constructor taking four doubles");
		}
	}

	@Test
	public void shapeHasAccessors() throws Exception {
		Class<?> klass = getShapeClass();
		assertClassHasAccessor(klass, "getX", double.class);
		assertClassHasAccessor(klass, "getY", double.class);
		assertClassHasAccessor(klass, "getWidth", double.class);
		assertClassHasAccessor(klass, "getHeight", double.class);
	}

	@Test
	public void shapeHasDraw() throws Exception {
		Class<?> klass = getShapeClass();
		try {
			Method m = klass.getMethod("draw", GraphicsContextWrapper.class);
			assertEquals(void.class, m.getReturnType());
		} catch (NoSuchMethodException ex) {
			fail("Shape should have a draw(GraphicsContext) method");
		}
	}

	@Test
	public void accessorsWork() throws Exception {
		Constructor<?> ctor = getShapeClass().getConstructors()[0];
		Object oShape = ctor.newInstance(1.0d, 2.0d, 3.0d, 4.0d);
		assertEquals(1.0d, invokeDoubleGetter(oShape, "getX"),
			1e-4, "First constructor argument should be upper left X coordinate, and getX() should return it");
		assertEquals(2.0d, invokeDoubleGetter(oShape, "getY"),
			1e-4, "Second constructor argument should be upper left Y coordinate, and getY() should return it");
		assertEquals(3.0d, invokeDoubleGetter(oShape, "getWidth"),
				1e-4, "Third constructor argument should be width, and getWidth() should return it");
		assertEquals(4.0d, invokeDoubleGetter(oShape, "getHeight"),
				1e-4, "Fourth constructor argument should be height, and getHeight() should return it");
	}

	@Test
	public void drawPrintsMessageToSystemOut() throws Exception {
		final PrintStream oldOut = System.out;
		try {
			PrintStream mockOut = Mockito.mock(PrintStream.class);
			System.setOut(mockOut);

			Constructor<?> ctor = getShapeClass().getConstructors()[0];
			Object oShape = ctor.newInstance(1.0d, 2.0d, 3.0d, 4.0d);
			Method mDraw = oShape.getClass().getMethod("draw", GraphicsContextWrapper.class);
			GraphicsContextWrapper mockGC = Mockito.mock(GraphicsContextWrapper.class);

			mDraw.invoke(oShape, mockGC);
			verify(mockOut).println(anyString());
			verifyNoInteractions(mockGC);
		} finally {
			System.setOut(oldOut);
		}
	}

	private double invokeDoubleGetter(Object oShape, String methodName) throws Exception {
		return (double) oShape.getClass().getMethod(methodName).invoke(oShape);
	}
	
	private Class<?> getShapeClass() {
		try {
			return Class.forName("uk.ac.aston.oop.inheritance.shapes.Shape");
		} catch (ClassNotFoundException ex) {
			fail("A Shape class should exist");
			return null;
		}
	}

	private void assertClassHasAccessor(Class<?> klass, String name, Class<?> returnType) {
		try {
			Method m = klass.getMethod(name);
			assertEquals(returnType, m.getReturnType());
		} catch (NoSuchMethodException ex) {
			fail(String.format("%s should have a '%s' method returning a '%s'",
				klass.getSimpleName(), name, returnType.getSimpleName()));
		}
	}
}
