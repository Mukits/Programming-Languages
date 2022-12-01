package uk.ac.aston.oop.rdd.sim;

import java.util.Random;

public interface Actor {

	public void setCell(GridCell cell);
	
	public GridCell getCell();
	
	public Field getField();
	
	public void setField(Field f);
	
	public void act(Random rnd);

}
