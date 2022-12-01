package uk.ac.aston.oop.dpatterns.afactory.javafx;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.aston.oop.dpatterns.afactory.Circle;
import uk.ac.aston.oop.dpatterns.afactory.Drawing;
import uk.ac.aston.oop.dpatterns.afactory.DrawingFactory;

/**
 * This is the factory that creates JavaFX variants for
 * Drawing and Circle. You only need to implement the lines
 * right after the TODO methods: do not make any other changes.
 */
public class JFXDrawingFactory implements DrawingFactory {

	private final Stage stage;
	private Group container;

	public JFXDrawingFactory(Stage s) {
		this.stage = s;
	}

	@Override
	public Drawing createDrawing(int w, int h) {
		if (container == null) {
			this.container = new Group();
			createScene(w, h);
		}

		return new JFXDrawing(container, w, h);
	}

	@Override
	public Circle createCircle(int cx, int cy, int radius) {
		return new JFXCircle(container, cx, cy, radius);
	}

	protected void createScene(int w, int h) {
		Scene sc = new Scene(container, w, h);
		stage.setScene(sc);
		stage.setResizable(false);
		stage.show();
	}

}
