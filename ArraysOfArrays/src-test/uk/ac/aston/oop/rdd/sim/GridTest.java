package uk.ac.aston.oop.rdd.sim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class GridTest {

	@Test
	public void widthHeight() {
		Grid g = new Grid(4, 3);
		assertEquals(3, g.getWidth(),
			"The width of a grid with 4 rows and 3 columns should be 3");
		assertEquals(4, g.getHeight(),
			"The height of a grid with 4 rows and 3 columns should be 4");
	}

	@Test
	public void allCellsAreSetUp() {
		Grid g = new Grid(5, 3);
		for (int iRow = 0; iRow < g.getHeight(); ++iRow) {
			for (int iCol = 0; iCol < g.getWidth(); ++iCol) {
				assertNotNull(g.get(iRow, iCol), "All cells of the grid have GridCells");
			}
		}
	}
}
