package uk.ac.aston.oop.dpatterns.afactory.javafx;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uk.ac.aston.oop.dpatterns.afactory.Drawing;

public class JFXDrawing implements Drawing {

	private Rectangle rectangle;

	public JFXDrawing(Group container, int width, int height) {
		rectangle = new Rectangle();
		rectangle.setWidth(width);
		rectangle.setHeight(height);
		container.getChildren().add(rectangle);
	}

	@Override
	public void setFill(int r, int g, int b) {
		rectangle.setFill(Color.rgb(r, g, b));
	}

}
