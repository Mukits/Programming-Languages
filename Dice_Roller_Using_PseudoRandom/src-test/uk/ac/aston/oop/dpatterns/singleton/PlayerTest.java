package uk.ac.aston.oop.dpatterns.singleton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PlayerTest {

	@Test
	public void create() {
		Player p = new Player(5);
		assertEquals(5, p.getDieFaces(),
			"A player created with a 5-face die should report the die has 5 faces");
	}

	@Test
	public void roll() {
		int nFaces = 6;
		int nRolls = 10;
		Player p = new Player(nFaces);

		for (int i = 0; i < nRolls; i++) {
			int r = p.roll();
			String assertionText = String.format(
				"With new Player(%d), Player.roll should roll between 1 and %d: rolled %d",
				nFaces, nFaces, r);
			assertTrue(r >= 1 && r <= nFaces, assertionText);
		}
	}
}
