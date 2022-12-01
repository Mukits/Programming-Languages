package uk.ac.aston.oop.rdd.sim;

import java.util.Random;

public class Fox extends Animal implements CountAwareActor {

	public static final double DEFAULT_HUNT_PROBABILITY = 1;
	private double huntProbability = DEFAULT_HUNT_PROBABILITY;

	@Override
	public int getLifespan() {
		return 30;
	}

	@Override
	public int getMaxLitterSize() {
		return 3;
	}

	@Override
	public double getBreedingProbability() {
		return 0.02;
	}

	@Override
	protected Animal createChild() {
		return new Fox();
	}

	@Override
	public void act(Random rnd) {
		super.act(rnd);
		hunt(rnd);
	}

	protected void hunt(Random rnd) {
		if (isAlive()) {
			// Tries to hunt
			Rabbit rabbit = findAdjacentRabbit();
			if (rnd.nextDouble() < huntProbability && rabbit != null) {
				moveTo(rabbit.getCell());
				rabbit.setAlive(false);
			} else {
				moveToRandomFreeAdjacentCell(rnd);
			}
		}
	}

	private Rabbit findAdjacentRabbit() {
		for (GridCell adj : getCell().getAdjacent()) {
			for (Actor a : adj.getContents()) {
				if (a instanceof Rabbit) {
					return (Rabbit) a;
				}
			}
		}
		return null;
	}

	@Override
	public void setActorCount(Class actorClass, int count) {
		if (Rabbit.class == actorClass) {
			if (count <= 2) {
				huntProbability = 0;
			} else if (count <= 20) {
				huntProbability = count / 100.0;
			} else {
				huntProbability = DEFAULT_HUNT_PROBABILITY;
			}
		}
	}
	
}
