package uk.ac.aston.oop.dpatterns.singleton;

public class Player {

	private int dieFaces;

	public Player(int dieFaceCount) {
		this.dieFaces = dieFaceCount;
	}

	public int roll() {
		return DiceRoller.getInstance().roll(dieFaces);
	}

	public int getDieFaces() {
		return dieFaces;
	}

}
