package uk.ac.aston.oop.inheritance.shapes;

import javafx.scene.canvas.GraphicsContext;
import uk.ac.aston.oop.inheritance.util.GraphicsContextWrapper;

/**
 * Rectangle, specified as upper left corner + width and height. 
 */
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
