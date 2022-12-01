package uk.ac.aston.oop.dpatterns.singleton;

public class SingletonMain {

	private static final int ROUNDS = 100;

	public static void main(String[] args) {
		DiceRoller.setSeed(10L);

		Player p6 = new Player(6);
		Player p10 = new Player(10);

		int p6won = 0, p10won = 0, ties = 0;
		for (int i = 0; i < ROUNDS; ++i) {
			final int p6roll = p6.roll();
			final int p10roll = p10.roll();

			if (p6roll > p10roll) {
				++p6won;
			} else if (p10roll > p6roll) {
				++p10won;
			} else {
				++ties;
			}
		}

		System.out.println(String.format(
			"6-sided die won %d times, 10-sided die won %d times, tied %d times",
			p6won, p10won, ties));
	}
}
