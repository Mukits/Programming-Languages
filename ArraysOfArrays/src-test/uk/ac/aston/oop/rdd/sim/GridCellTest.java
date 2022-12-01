package uk.ac.aston.oop.rdd.sim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class GridCellTest {

	@Test
	public void gridCellKnowsLocation() {
		Grid g = new Grid(4, 3);
		GridCell cell = g.get(1, 2);
		assertEquals(1, cell.getRow(),
				"When getting the cell at row=1 and column=2, the cell should known it is in row 1");
		assertEquals(2, cell.getColumn(),
				"When getting the cell at row=1 and column=2, the cell should known it is in column 2");
		assertSame(g, cell.getGrid(), "Cells know the Grid they belong to");
	}

	@Test
	public void gridCellAdjacentInner() {
		Grid g = new Grid(4, 3);
		List<GridCell> adj = g.get(1, 1).getAdjacent();
		assertEquals(8, adj.size(), "In a grid with 4 rows and 3 columns, (1, 1) should have 8 adjacent cells");
		assertEquals(0, adj.get(0).getRow(),
				"getAdjacent should return the adjacent cells in row order: top-left should be first");
		assertEquals(0, adj.get(0).getColumn(),
				"getAdjacent should return the adjacent cells in row order: top-left should be first");
	}

	@Test
	public void gridCellAdjacentRightEdge() {
		Grid g = new Grid(4, 3);
		List<GridCell> adj = g.get(1, 2).getAdjacent();
		assertEquals(5, adj.size(),
				"In a grid with 4 rows and 3 columns, (1, 2) should have 5 adjacent cells: returned "
						+ formatGridCells(adj));
	}

	@Test
	public void gridCellAdjacentLeftEdge() {
		Grid g = new Grid(4, 3);
		List<GridCell> adj = g.get(1, 0).getAdjacent();
		assertEquals(5, adj.size(),
				"In a grid with 4 rows and 3 columns, (1, 0) should have 5 adjacent cells: returned "
						+ formatGridCells(adj));
	}

	@Test
	public void gridCellAdjacentTopEdge() {
		Grid g = new Grid(4, 3);
		List<GridCell> adj = g.get(0, 1).getAdjacent();
		assertEquals(5, adj.size(),
				"In a grid with 4 rows and 3 columns, (0, 1) should have 5 adjacent cells: returned "
						+ formatGridCells(adj));
	}

	@Test
	public void gridCellAdjacentBottomEdge() {
		Grid g = new Grid(4, 3);
		List<GridCell> adj = g.get(3, 1).getAdjacent();
		assertEquals(5, adj.size(),
				"In a grid with 4 rows and 3 columns, (3, 1) should have 5 adjacent cells: returned "
						+ formatGridCells(adj));
	}

	@Test
	public void gridCellAdjacentTopLeftCorner() {
		Grid g = new Grid(4, 3);
		List<GridCell> adj = g.get(0, 0).getAdjacent();
		assertEquals(3, adj.size(),
				"In a grid with 4 rows and 3 columns, (0, 0) should have 3 adjacent cells: returned "
						+ formatGridCells(adj));
	}

	@Test
	public void gridCellAdjacentTopRightCorner() {
		Grid g = new Grid(4, 3);
		List<GridCell> adj = g.get(0, g.getWidth() - 1).getAdjacent();
		assertEquals(3, adj.size(),
				"In a grid with 4 rows and 3 columns, (0, 2) should have 3 adjacent cells: returned "
						+ formatGridCells(adj));
	}

	@Test
	public void gridCellAdjacentBottomRightCorner() {
		Grid g = new Grid(4, 3);
		List<GridCell> adj = g.get(g.getHeight() - 1, g.getWidth() - 1).getAdjacent();
		assertEquals(3, adj.size(),
				"In a grid with 4 rows and 3 columns, (3, 2) should have 3 adjacent cells: returned "
						+ formatGridCells(adj));
	}

	@Test
	public void gridCellAdjacentBottomLeftCorner() {
		Grid g = new Grid(4, 3);
		List<GridCell> adj = g.get(g.getHeight() - 1, 0).getAdjacent();
		assertEquals(3, adj.size(),
				"In a grid with 4 rows and 3 columns, (3, 0) should have 3 adjacent cells: returned "
						+ formatGridCells(adj));
	}

	@Test
	public void gridCellFreeAdjacent() {
		Grid g = new Grid(4, 3);
		g.get(1, 1).getContents().add(new Fox());

		List<GridCell> freeAdj = g.get(2, 1).getFreeAdjacent();
		assertEquals(7, freeAdj.size(),
				"After placing a fox in (1, 1), (2, 1) should not list (1, 1) as a free adjacent position");
		for (GridCell c : freeAdj) {
			assertTrue(c.getRow() != 1 || c.getColumn() != 1,
					"After placing a fox in (1, 1), (2, 1) should not list (1, 1) as a free adjacent position");
		}
	}

	@Test
	public void gridCellRandomFreeAdjacent() {
		Grid g = new Grid(4, 3);
		g.get(1, 1).getContents().add(new Fox());

		Random mockRandom = mock(Random.class);
		when(mockRandom.nextInt(7)).thenReturn(1);

		GridCell cell = g.get(2, 1).getRandomFreeAdjacent(mockRandom);
		verify(mockRandom, description("getRandomFreeAdjacent should call the nextInt method of Random")).nextInt(7);
		assertEquals(1, cell.getRow());
		assertEquals(2, cell.getColumn());
	}

	private String formatGridCells(List<GridCell> l) {
		var strings = l.stream().map(c -> String.format("(%d, %d)", c.getRow(), c.getColumn()))
				.collect(Collectors.toList());

		return "(" + String.join(", ", strings) + ")";
	}
}
