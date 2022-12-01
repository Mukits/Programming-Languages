package uk.ac.aston.oop.dpatterns.singleton;

import java.util.Random;

public class DiceRoller {

	private static DiceRoller instance = null;
	private static Long seed;
	private final Random random;

	private DiceRoller() {
		if (seed != null) {
			this.random = new Random(seed);
		} else {
			this.random = new Random();
		}
	}

	public static DiceRoller getInstance() {
		if (instance == null) {
			instance = new DiceRoller();
		}
		return instance;
	}

	public static void setSeed(long seed) {
		if (instance == null) {
			DiceRoller.seed = seed;
		} else {
			System.err.println("Seed has already been set");
		}
	}

	public int roll(int nFaces) {
		return 1 + random.nextInt(nFaces);
	}

}
