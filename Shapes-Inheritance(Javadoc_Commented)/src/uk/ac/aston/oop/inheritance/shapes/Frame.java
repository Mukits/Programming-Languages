package uk.ac.aston.oop.inheritance.shapes;

import javafx.scene.canvas.GraphicsContext;
import uk.ac.aston.oop.inheritance.util.GraphicsContextWrapper;

/**
 * Represents a hollow picture frame, with one rectangle inside another.
 */
public class Frame extends Shape {

	private static int FRAME_THICKNESS = 10;

	protected Rectangle outerRectangle;
	protected Rectangle innerRectangle;

	public Frame(double ulX, double ulY, double width, double height) {
		super(ulX, ulY, width, height);

		outerRectangle = new Rectangle(ulX, ulY, width, height);
		innerRectangle = new Rectangle(ulX + FRAME_THICKNESS,
			ulY + FRAME_THICKNESS,
			width - FRAME_THICKNESS * 2,
			height - FRAME_THICKNESS * 2
		);
	}

	@Override
	public void draw(GraphicsContextWrapper gc) {
		outerRectangle.draw(gc);
		innerRectangle.draw(gc);
	}

}
