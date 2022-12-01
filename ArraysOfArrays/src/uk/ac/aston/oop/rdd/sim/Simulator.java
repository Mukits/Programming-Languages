package uk.ac.aston.oop.rdd.sim;

import java.util.ArrayList;
import java.util.Random;

public class Simulator {

	private final Random rnd;
	private final Field field;
	private double foxProbability, rabbitProbability;
	private int step = 0;

	public Simulator(Random rnd, Field field) {
		this.rnd = rnd;
		this.field = field;
	}

	public double getFoxProbability() {
		return foxProbability;
	}

	public void setFoxProbability(double foxProbability) {
		this.foxProbability = foxProbability;
	}

	public double getRabbitProbability() {
		return rabbitProbability;
	}

	public void setRabbitProbability(double rabbitProbability) {
		this.rabbitProbability = rabbitProbability;
	}

	public Field getField() {
		return field;
	}

	public void act() {
		++step;

		for (Actor a : new ArrayList<>(field.getContents())) {
			if (a instanceof CountAwareActor) {
				CountAwareActor caActor = ((CountAwareActor)a);
				caActor.setActorCount(Rabbit.class, field.getCount(Rabbit.class));
			}

			a.act(rnd);
		}
	}

	public void populate() {
		for (int iRow = 0; iRow < field.getGrid().getHeight(); iRow++) {
			for (int iCol = 0; iCol < field.getGrid().getWidth(); iCol++) {
				/* Roulette method: note that the rabbit + fox
				 * probabilities must not add to more than 1. */
				double d = rnd.nextDouble();

				if (d < getRabbitProbability()) {
					field.add(new Rabbit(), iRow, iCol);					
				} else if (d < getRabbitProbability() + getFoxProbability()) {
					field.add(new Fox(), iRow, iCol);
				}
			}
		}
	}

	public int getStep() {
		return step;
	}

}
