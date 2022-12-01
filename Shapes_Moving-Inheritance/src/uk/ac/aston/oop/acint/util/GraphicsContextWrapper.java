package uk.ac.aston.oop.acint.util;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Thin wrapper over a JavaFX {@link GraphicsContextWrapper}.
 * Used for testing purposes in AutoFeedback: this is needed
 * because Mockito cannot mock final methods or classes unless
 * using opt-in plugins with lax security permissions.
 */
public class GraphicsContextWrapper {

	private GraphicsContext gc;

	public GraphicsContextWrapper(GraphicsContext gc) {
		this.gc = gc;
	}

	public void oval(double x, double y, double w, double h) {
		gc.strokeOval(x, y, w, h);
	}

	public void fill(Color fill) {
		gc.setFill(fill);
	}

	public void fillRect(double x, double y, double w, double h) {
		gc.fillRect(x, y, w, h);
	}

	public void lineWidth(int lw) {
		gc.setLineWidth(lw);
	}

	public void rect(double x, double y, double w, double h) {
		gc.strokeRect(x, y, w, h);
	}

	public void line(double fromX, double fromY, double toX, double toY) {
		gc.beginPath();
		gc.moveTo(fromX, fromY);
		gc.lineTo(toX, toY);
		gc.stroke();
	}

	public double width() {
		return gc.getCanvas().getWidth();
	}

	public double height() {
		return gc.getCanvas().getHeight();
	}
}
