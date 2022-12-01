package uk.ac.aston.oop.acint.shapes;

import uk.ac.aston.oop.acint.util.GraphicsContextWrapper;

public class Ellipse extends Shape {

	public Ellipse(double upperLeftX, double upperLeftY, double width, double height) {
		super(upperLeftX, upperLeftY, width, height);
	}

    @Override
	public void draw(GraphicsContextWrapper gc) {
		gc.oval(getX(), getY(), getWidth(), getHeight());
	}

}
