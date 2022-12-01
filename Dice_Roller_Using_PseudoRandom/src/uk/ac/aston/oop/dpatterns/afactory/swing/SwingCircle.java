package uk.ac.aston.oop.dpatterns.afactory.swing;

import java.awt.Graphics;

import uk.ac.aston.oop.dpatterns.afactory.Circle;

public class SwingCircle implements Circle {

	private int fillRed = 0, fillGreen = 0, fillBlue = 0;
	private final int cx, cy, radius;

	public SwingCircle(int cx, int cy, int radius) {
		this.cx = cx;
		this.cy = cy;
		this.radius = radius;
	}

	public void draw(Graphics g) {
		g.setColor(new java.awt.Color(fillRed, fillGreen, fillBlue));
		g.fillOval(cx - radius, cy - radius, radius * 2, radius * 2);
	}

	@Override
	public void setFill(int r, int g, int b) {
		fillRed = r;
		fillGreen = g;
		fillBlue = b;
	}

}
