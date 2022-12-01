package uk.ac.aston.oop.dpatterns.afactory.javafx;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import uk.ac.aston.oop.dpatterns.afactory.Circle;

public class JFXCircle implements Circle {

	private javafx.scene.shape.Circle circle;

	public JFXCircle(Group container, int cx, int cy, int radius) {
		circle = new javafx.scene.shape.Circle();
		circle.setCenterX(cx);
		circle.setCenterY(cy);
		circle.setRadius(radius);
		container.getChildren().add(circle);
	}
	
	@Override
	public void setFill(int r, int g, int b) {
		circle.setFill(Color.rgb(r, g, b));
	}

}
