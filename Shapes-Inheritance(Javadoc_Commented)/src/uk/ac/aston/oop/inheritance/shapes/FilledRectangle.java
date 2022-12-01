package uk.ac.aston.oop.inheritance.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uk.ac.aston.oop.inheritance.util.GraphicsContextWrapper;

/**
 * Variant of {@link Rectangle} which is filled with a certain {@link Color}. 
 */
public class FilledRectangle extends Rectangle {

	private final Color fill;

	public FilledRectangle(Color fill, double ulX, double ulY, double width, double height) {
		super(ulX, ulY, width, height);
		this.fill = fill;
	}

	public Color getFill() { return fill; }

    @Override
	public void draw(GraphicsContextWrapper gc) {
    	super.draw(gc);
    	gc.fill(fill);
    	gc.fillRect(getX(), getY(), getWidth(), getHeight());
	}
    
}
