package uk.ac.aston.oop.rdd.sim;

import java.util.Random;

public class Rabbit extends Animal {

	@Override
	public int getLifespan() {
		return 60;
	}

	@Override
	public int getMaxLitterSize() {
		return 8;
	}

	@Override
	public double getBreedingProbability() {
		return 0.07;
	}

	@Override
	protected Animal createChild() {
		return new Rabbit();
	}

	@Override
	public void act(Random rnd) {
		super.act(rnd);
		if (isAlive()) {
			moveToRandomFreeAdjacentCell(rnd);
		}
	}
	
}
