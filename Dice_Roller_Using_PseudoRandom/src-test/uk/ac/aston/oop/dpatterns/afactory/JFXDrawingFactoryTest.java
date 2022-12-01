package uk.ac.aston.oop.dpatterns.afactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import uk.ac.aston.oop.dpatterns.afactory.javafx.JFXCircle;
import uk.ac.aston.oop.dpatterns.afactory.javafx.JFXDrawing;
import uk.ac.aston.oop.dpatterns.afactory.javafx.JFXDrawingFactory;

public class JFXDrawingFactoryTest {

	/**
	 * Variant of JFXDrawingFactory which does *not* set up a Scene,
	 * which hopefully should mean we can run this in a headless
	 * environment (no graphics adapters), as in AutoFeedback.
	 */
	private static class HeadlessJFXDrawingFactory extends JFXDrawingFactory {

		public HeadlessJFXDrawingFactory() {
			super(null);
		}

		@Override
		protected void createScene(int w, int h) {
			// do nothing!
		}
	}

	@Test
	public void createCircle() {
		HeadlessJFXDrawingFactory f = new HeadlessJFXDrawingFactory();
		f.createDrawing(50, 100);
		Circle circle = f.createCircle(10, 20, 30);
		assertTrue(circle instanceof JFXCircle,
			"JFXDrawingFactory.createCircle should return a new JFXCircle");
	}

	@Test
	public void createDrawing() {
		HeadlessJFXDrawingFactory f = new HeadlessJFXDrawingFactory();
		Drawing circle = f.createDrawing(100, 200);
		assertTrue(circle instanceof JFXDrawing,
			"JFXDrawingFactory.createDrawing should return a new JFXDrawing");
	}

}
