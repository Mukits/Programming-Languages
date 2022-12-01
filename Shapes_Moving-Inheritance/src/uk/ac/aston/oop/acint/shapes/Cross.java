package uk.ac.aston.oop.acint.shapes;


import uk.ac.aston.oop.acint.util.GraphicsContextWrapper;

public class Cross implements Drawable {

	protected static final int SIZE = 20;
	private double centerX;
	private double centerY;

	public Cross(double centerX, double centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
	}

	@Override
	public void draw(GraphicsContextWrapper gcw) {
		gcw.line(centerX - SIZE/2, centerY - SIZE/2, centerX + SIZE/2, centerY + SIZE/2);
		gcw.line(centerX + SIZE/2, centerY - SIZE/2, centerX - SIZE/2, centerY + SIZE/2);
	}

	@Override
	public void move(GraphicsContextWrapper gcw, double dx, double dy) {
		centerX += dx;
		centerY += dy;
		if (centerX + SIZE/2 > gcw.width()) {
			centerX = gcw.width() - SIZE/2;
		}
		if (centerY + SIZE/2 > gcw.height()) {
			centerY = gcw.height() - SIZE/2;
		}
	}

}
