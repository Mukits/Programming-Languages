package uk.ac.aston.oop.inheritance.shapes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public abstract class AbstractShapeSubclassTest {

	private Class<?> shapeSubclass;
	private Class<?> expectedDirectSuperclass;

	public AbstractShapeSubclassTest(Class<?> shapeSubclass) throws ClassNotFoundException {
		this(shapeSubclass, Class.forName("uk.ac.aston.oop.inheritance.shapes.Shape"));
	}

	public AbstractShapeSubclassTest(Class<?> shapeSubclass, Class<?> expectedDirectSuperclass) {
		this.shapeSubclass = shapeSubclass;
		this.expectedDirectSuperclass = expectedDirectSuperclass;
	}

	@Test
	public void subclassExtendsCorrectClass() {
		Class<?> superClass = shapeSubclass.getSuperclass();
		assertEquals(expectedDirectSuperclass.getSimpleName(), superClass.getSimpleName(),
			shapeSubclass.getSimpleName() + " should extend " + expectedDirectSuperclass.getSimpleName());
	}

	@Test
	public void subclassInheritsGetters() throws Exception {
		for (String mName : new String[] {"getX", "getY", "getWidth", "getHeight"}) {
			assertGetterIsInherited(mName);
		}
	}

	private void assertGetterIsInherited(String mName) throws NoSuchMethodException {
		assertEquals("Shape",
			shapeSubclass.getMethod(mName).getDeclaringClass().getSimpleName(),
			String.format(
				"%s should inherit %s instead of repeating it",
				shapeSubclass.getSimpleName(), mName)
		);
	}

	@Test
	public void subclassDoesNotHaveUnnecessaryVariables() throws Exception {
		for (String fieldName : new String[] { "upperLeftX", "upperLeftY", "width", "height" }) {
			assertThrows(NoSuchFieldException.class, () -> {
				shapeSubclass.getDeclaredField(fieldName);
			}, fieldName + " is no longer needed as it should be inherited from Shape");
		}
	}

}