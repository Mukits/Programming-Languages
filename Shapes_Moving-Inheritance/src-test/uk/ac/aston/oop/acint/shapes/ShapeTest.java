package uk.ac.aston.oop.acint.shapes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import uk.ac.aston.oop.acint.util.GraphicsContextWrapper;

public class ShapeTest {

	private GraphicsContextWrapper gcw;

	@Before
	public void setup() {
		gcw = mock(GraphicsContextWrapper.class);
		when(gcw.width()).thenReturn(100d);
		when(gcw.height()).thenReturn(200d);
	}

	@Test
	public void moveNoClip() {
		Rectangle r = new Rectangle(10, 20, 50, 50);
		r.move(gcw, 1, 2);
		assertEquals(11, r.getX(),
			"Starting at x=10 with rectangleWidth=50 and canvasWidth=100, "
			+ "move(gcw, 1, 2) should move upper left corner X coordinate "
			+ "1 to the right");
		assertEquals(22, r.getY(),
			"Starting at y=20 with rectangleHeight=50 and canvasHeight=200, "
			+ "move(gcw, 1, 2) should move upper left corner Y coordinate 2 "
			+ "down");
	}

	@Test
	public void moveClipX() {
		Rectangle r = new Rectangle(10, 20, 50, 50);
		r.move(gcw, 100, 0);
		assertEquals(gcw.width() - 50, r.getX(),
			"Starting at x=10 with rectangleWidth=50 and canvasWidth=100, "
			+ "move(gcw, 100, 0) should move upper left corner to the right, "
			+ "past the edge of the screen, and get clipped");
		assertEquals(20, r.getY(),
			"Starting at x=20, move(gcw, 100, 0) should not move upper "
			+ "left corner vertically");
	}

	@Test
	public void moveClipY() {
		Rectangle r = new Rectangle(10, 20, 50, 60);
		r.move(gcw, 0, 200);
		assertEquals(10, r.getX(),
			"Starting at x=10, move(gcw, 0, 200) should not move the "
			+ "upper left corner horizontally");
		assertEquals(gcw.height() - 60, r.getY(),
			"Starting at y=20 with height=200, move(gcw, 0, 200) should "
			+ "move upper left corner up past the edge of the screen, and "
			+ "get clipped");
	}

	@Test
	public void shapeDrawShouldBeAbstract() throws Exception {
		Method mDraw = Shape.class.getMethod("draw", GraphicsContextWrapper.class);
		assertTrue(Modifier.isAbstract(mDraw.getModifiers()),
			"The draw method in Shape should be abstract");
	}

	@Test
	public void shapeDrawShouldBeInheritedFromDrawable() throws Exception {
		CompilationUnit parsed = StaticJavaParser.parse(
			Paths.get("src", "uk", "ac", "aston", "oop", "acint", "shapes", "Shape.java").toFile());

		ClassOrInterfaceDeclaration cShape = parsed.getClassByName("Shape").get();
		assertTrue(cShape.getMethodsByName("draw").isEmpty(),
			"Shape.java should not list the draw method: it should be inherited from Drawable");
	}

	@Test
	public void shapeShouldImplementDrawable() throws Exception {
		Class<?>[] interfaces = Shape.class.getInterfaces();
		assertEquals(1, interfaces.length, "Shape should implement one interface");
		assertEquals("Drawable", interfaces[0].getSimpleName(), "Shape should implement Drawable");
	}
}
