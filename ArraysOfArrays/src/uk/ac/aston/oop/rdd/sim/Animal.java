package uk.ac.aston.oop.rdd.sim;

import java.util.Random;

public abstract class Animal implements Actor {

	private GridCell cell;
	private Field field;
	private int age;
	private boolean alive = true;

	public abstract int getLifespan();

	public abstract int getMaxLitterSize();

	public abstract double getBreedingProbability();

	protected abstract Animal createChild();

	@Override
	public void setCell(GridCell cell) {
		this.cell = cell;
	}

	@Override
	public GridCell getCell() {
		return cell;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
		if (age > getLifespan()) {
			setAlive(false);
		}
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
		if (!alive) {
			field.remove(this);
		}
	}

	@Override
	public Field getField() {
		return field;
	}

	@Override
	public void setField(Field f) {
		this.field = f;
	}
	
	@Override
	public void act(Random rnd) {
		setAge(age + 1);
		breed(rnd);
	}
	
	protected void breed(Random rnd) {
		if (isAlive() && rnd.nextDouble() < getBreedingProbability()) {
			int litterSize = rnd.nextInt(getMaxLitterSize());
			for (int i = 0; i < litterSize; i++) {
				GridCell c = cell.getRandomFreeAdjacent(rnd);
				if (c != null) {
					Animal a = createChild();
					field.add(a, c.getRow(), c.getColumn());
				}
			}
		}
	}

	protected void moveTo(GridCell target) {
		cell.getContents().remove(this);
		cell = target;
		cell.getContents().add(this);
	}

	protected void moveToRandomFreeAdjacentCell(Random rnd) {
		GridCell adj = getCell().getRandomFreeAdjacent(rnd);
		if (adj != null) {
			moveTo(adj);
		}
	}

}
