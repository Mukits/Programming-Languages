package uk.ac.aston.oop.dpatterns.afactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import uk.ac.aston.oop.dpatterns.afactory.javafx.JFXCircle;

public class JFXCircleTest {

	@Test
	public void implementsCircle() {
		Group g = new Group();
		JFXCircle c = new JFXCircle(g, 1, 2, 3);
		assertTrue(c instanceof Circle, "JFXCircle should implement Circle");
	}

	@Test
	public void javaFXCircleIsCreated() {
		Group g = new Group();
		final int cx = 1;
		final int cy = 2;
		final int radius = 3;
		new JFXCircle(g, cx, cy, radius);

		assertEquals(1, g.getChildren().size(),
			"When creating a JFXCircle with an empty Group, an object should have been added to the Group");
		Node node = g.getChildren().get(0);
		assertTrue(node instanceof javafx.scene.shape.Circle,
			"When creating a JFXCircle with an empty Group, the only child in the group should be a javafx.scene.shape.Circle");

		javafx.scene.shape.Circle rawCircle = (javafx.scene.shape.Circle) node;
		assertEquals(cx, rawCircle.getCenterX(), String.format(
			"When creating a new JFXCircle(g, %d, %d, %d), the center X should be %d",
			cx, cy, radius, cx));
		assertEquals(cy, rawCircle.getCenterY(), String.format(
				"When creating a new JFXCircle(g, %d, %d, %d), the center Y should be %d",
				cx, cy, radius, cy));
		assertEquals(radius, rawCircle.getRadius(), String.format(
				"When creating a new JFXCircle(g, %d, %d, %d), the radius should be %d",
				cx, cy, radius, radius));
	}

	@Test
	public void fill() {
		Group g = new Group();
		JFXCircle c = new JFXCircle(g, 1, 2, 3);

		Node node = g.getChildren().get(0);
		javafx.scene.shape.Circle rawCircle = (javafx.scene.shape.Circle) node;

		c.setFill(10, 20, 30);
		assertEquals(Color.rgb(10, 20, 30), rawCircle.getFill());
	}
	
}
