package uk.ac.aston.oop.inheritance.shapes;

import uk.ac.aston.oop.inheritance.util.GraphicsContextWrapper;

/**
 * Ellipse, specified as upper left corner + width and height. 
 */
public class Ellipse extends Shape {

	public Ellipse(double upperLeftX, double upperLeftY, double width, double height) {
		super(upperLeftX, upperLeftY, width, height);
	}

    @Override
	public void draw(GraphicsContextWrapper gc) {
		gc.oval(getX(), getY(), getWidth(), getHeight());
	}

}
