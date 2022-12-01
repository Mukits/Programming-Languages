package uk.ac.aston.oop.acint.shapes;

import uk.ac.aston.oop.acint.util.GraphicsContextWrapper;

public class Rectangle extends Shape {

	public Rectangle(double ulX, double ulY, double width, double height) {
		super(ulX, ulY, width, height);
	}

    @Override
	public void draw(GraphicsContextWrapper gc) {
    	gc.lineWidth(5);
		gc.rect(getX(), getY(), getWidth(), getHeight());
	}

}
