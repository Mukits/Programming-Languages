package uk.ac.aston.oop.inheritance.shapes;

import uk.ac.aston.oop.inheritance.util.GraphicsContextWrapper;

/**
 * Represents a geometric shape, defined by the position of its
 * upper left corner, its width and its height.
 */
public class Shape {

	private final double upperLeftX, upperLeftY;
	private final double width, height;

	/**
	 * Creates a new instance.
	 *
	 * @param ulX X coordinate of the upper left corner.
	 * @param ulY Y coordinate of the upper left corner.
	 * @param width Width of the shape.
	 * @param height Height of the shape.
	 */
	public Shape(double ulX, double ulY, double width, double height) {
		this.upperLeftX = ulX;
		this.upperLeftY = ulY;
		this.width  = width;
		this.height = height;
	}

	/**
	 * Returns the X coordinate of the upper left corner.
	 *
	 * @return X coordinate of upper left corner.
	 */
	public double getX() { return upperLeftX; }

	/**
	 * Returns the Y coordinate of the upper left corner.
	 *
	 * @return Y coordinate of upper left corner.
	 */
	public double getY() { return upperLeftY; }

	/**
	 * Returns the width of the shape.
	 *
	 * @return Width of the shape.
	 */
	public double getWidth()  { return width;  }

	/**
	 * Returns the height of the shape.
	 *
	 * @return Height of the shape.
	 */
	public double getHeight() { return height; }

	/**
	 * Draws the shape on the provided graphics context.
	 *
	 * @param gc Context to draw on.
	 */
	public void draw(GraphicsContextWrapper gc) {
		System.out.println("You forgot to write the draw method in " + getClass().getName());
	}

}
