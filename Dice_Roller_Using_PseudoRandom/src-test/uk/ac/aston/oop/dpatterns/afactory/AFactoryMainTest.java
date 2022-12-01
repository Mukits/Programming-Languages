package uk.ac.aston.oop.dpatterns.afactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import uk.ac.aston.oop.dpatterns.afactory.javafx.JFXDrawingFactory;
import uk.ac.aston.oop.dpatterns.afactory.swing.SwingDrawingFactory;

public class AFactoryMainTest {

	@Test
	public void createsSwing() {
		AFactoryMain main = new AFactoryMain();
		DrawingFactory factory = main.createFactory(null, Arrays.asList("swing"));
		assertTrue(factory instanceof SwingDrawingFactory,
			"If the list of parameters includes 'swing', createFactory should create a SwingDrawingFactory");
	}

	@Test
	public void createsJavaFX() {
		AFactoryMain main = new AFactoryMain();
		DrawingFactory factory = main.createFactory(null, Arrays.asList("javafx"));
		assertTrue(factory instanceof JFXDrawingFactory,
			"If the list of parameters does not include 'swing', createFactory should create a JFXDrawingFactory");
	}

}
