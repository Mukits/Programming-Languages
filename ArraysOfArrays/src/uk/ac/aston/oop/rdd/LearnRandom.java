package uk.ac.aston.oop.rdd;

import java.util.Random;

public class LearnRandom {

	public static void useRandom(Random rnd) {
		for (int i = 0; i < 10; i++) {
			if (rnd.nextDouble() < 0.2) {
				System.out.println("The random event with probability of 20% took place");
			}
		}

		for (int i = 0; i < 10; i++) {
			int roll0 = rnd.nextInt(6);
			System.out.println("The result is: " + (roll0 + 1));
		}
	}

	public static void main(String[] args) {
		Random rnd = new Random();

		// Don't change the line below!
		LearnRandom.useRandom(rnd);
	}
}
