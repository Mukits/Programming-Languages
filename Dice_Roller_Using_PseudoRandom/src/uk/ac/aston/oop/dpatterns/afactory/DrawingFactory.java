package uk.ac.aston.oop.dpatterns.afactory;

public interface DrawingFactory {

	Drawing createDrawing(int w, int h);

	Circle createCircle(int cx, int cy, int radius);

}
