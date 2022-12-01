package uk.ac.aston.oop.acint.shapes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class CircleTest {

	@Test
	public void ratioTest() {
		Circle c = new Circle(10, 20, 50);
		c.scale(0.5);
		assertEquals(25, c.getRadius(),
			"Calling scale(0.5) on a Circle of radius 50 should halve its radius");
	}
}
