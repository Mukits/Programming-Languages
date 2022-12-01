package uk.ac.aston.oop.rdd.sim;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GridCell {

	private final ArrayList<Actor> contents = new ArrayList<>();
	private final int row, column;
	private final Grid grid;

	public ArrayList<Actor> getContents() {
		return contents;
	}

	public GridCell(Grid grid, int row, int column) {
		this.grid = grid;
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public Grid getGrid() {
		return grid;
	}
	
	public ArrayList<GridCell> getAdjacent() {
		ArrayList<GridCell> results = new ArrayList<>();
		for (int iRow = row - 1; iRow <= row + 1; iRow++) {
			for (int iCol = column - 1; iCol <= column + 1; iCol++) {
				if (iRow == row && iCol == column) {
					// do nothing
				} else if (iRow < 0 || iCol < 0 || iRow >= grid.getHeight() || iCol >= grid.getWidth()) {
					// do nothing
				} else {
					results.add(grid.get(iRow, iCol));
				}
			}
		}
		return results;
	}

	public List<GridCell> getFreeAdjacent() {
		/*
		 * We could have used iterators to avoid using a second list, 
		 * but we have not covered them yet.
		 */
		List<GridCell> adjacent = getAdjacent();
		List<GridCell> free = new ArrayList<>();

		for (GridCell gc : adjacent) {
			if (gc.getContents().isEmpty()) {
				free.add(gc);
			}
		}

		return free;
	}

	public GridCell getRandomFreeAdjacent(Random rnd) {
		List<GridCell> freeAdj = getFreeAdjacent();
		if (freeAdj.size() > 0) {
			return freeAdj.get(rnd.nextInt(freeAdj.size()));
		} else {
			return null;
		}
	}
	
}
