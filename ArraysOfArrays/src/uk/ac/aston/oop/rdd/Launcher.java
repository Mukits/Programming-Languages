package uk.ac.aston.oop.rdd;

import java.util.List;
import java.util.Random;

import uk.ac.aston.oop.rdd.sim.Actor;
import uk.ac.aston.oop.rdd.sim.Field;
import uk.ac.aston.oop.rdd.sim.Fox;
import uk.ac.aston.oop.rdd.sim.Grid;
import uk.ac.aston.oop.rdd.sim.Rabbit;
import uk.ac.aston.oop.rdd.sim.Simulator;

public class Launcher {

	public void run(Simulator s, int nSteps) {
		for (int i = 0; i < nSteps; i++) {
			show(s);
			s.act();
		}
	}

	public void show(Simulator s) {
		System.out.println("Step: " + s.getStep());

		Field field = s.getField();
		System.out.println("Foxes: " + field.getCount(Fox.class));
		System.out.println("Rabbits: " + field.getCount(Rabbit.class));
		System.out.println();

		Grid grid = field.getGrid();
		for (int iRow = 0; iRow < grid.getHeight(); iRow++) {
			for (int iCol = 0; iCol < grid.getWidth(); iCol++) {
				List<Actor> Actors = grid.get(iRow, iCol).getContents();
				if (Actors.isEmpty()) {
					System.out.print(".");
				} else if (Actors.get(0) instanceof Rabbit) {
					System.out.print("r");
				} else if (Actors.get(0) instanceof Fox) {
					System.out.print("F");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Simulator s = new Simulator(new Random(), new Field(30, 30));
		s.setFoxProbability(0.10);
		s.setRabbitProbability(0.30);
		s.populate();

		final int nSteps = 50;
		new Launcher().run(s, nSteps);
	}
}
