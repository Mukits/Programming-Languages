package uk.ac.aston.oop.dpatterns.afactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import uk.ac.aston.oop.dpatterns.afactory.javafx.JFXDrawing;

public class JFXDrawingTest {

	@Test
	public void implementsDrawing() {
		Group g = new Group();
		JFXDrawing drawing = new JFXDrawing(g, 100, 200);
		assertTrue(drawing instanceof Drawing, "JFXDrawing should implement Drawing");
	}

	@Test
	public void javaFXRectangleIsCreated() {
		final int width = 50;
		final int height = 100;
		final Group g = new Group();
		new JFXDrawing(g, width, height);

		assertEquals(1, g.getChildren().size(),
			"When creating a JFXDrawing with an empty Group, an object should have been added to the Group");
		Node node = g.getChildren().get(0);
		assertTrue(node instanceof javafx.scene.shape.Rectangle,
			"When creating a JFXDrawing with an empty Group, the only child in the group should be a javafx.scene.shape.Rectangle");

		javafx.scene.shape.Rectangle rawCircle = (javafx.scene.shape.Rectangle) node;
		assertEquals(width, rawCircle.getWidth(), String.format(
			"When creating a new JFXCircle(g, %d, %d), the width should be %d",
			width, height, width));
		assertEquals(height, rawCircle.getHeight(), String.format(
				"When creating a new JFXCircle(g, %d, %d), the height should be %d",
				width, height, height));
	}

	@Test
	public void fill() {
		final int width = 50;
		final int height = 100;
		final Group g = new Group();
		JFXDrawing d = new JFXDrawing(g, width, height);

		Node node = g.getChildren().get(0);
		javafx.scene.shape.Rectangle rawCircle = (javafx.scene.shape.Rectangle) node;

		d.setFill(10, 20, 30);
		assertEquals(Color.rgb(10, 20, 30), rawCircle.getFill());
	}

	
}
