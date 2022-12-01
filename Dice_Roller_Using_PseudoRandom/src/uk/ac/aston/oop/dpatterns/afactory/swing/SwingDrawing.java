package uk.ac.aston.oop.dpatterns.afactory.swing;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import uk.ac.aston.oop.dpatterns.afactory.Drawing;

public class SwingDrawing extends JPanel implements Drawing {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final List<SwingCircle> circles = new ArrayList<>();

	private int fillRed = 255, fillGreen = 255, fillBlue = 255;

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(new java.awt.Color(fillRed, fillGreen, fillBlue));
		g.fillRect(0, 0, getWidth(), getHeight());

		for (SwingCircle c : circles) {
			c.draw(g);
		}
	}

	public List<SwingCircle> getCircles() {
		return circles;
	}

	@Override
	public void setFill(int r, int g, int b) {
		fillRed = r;
		fillGreen = g;
		fillBlue = b;
	}

}
