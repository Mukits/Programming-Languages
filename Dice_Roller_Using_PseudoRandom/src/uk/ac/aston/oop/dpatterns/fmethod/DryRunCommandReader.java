package uk.ac.aston.oop.dpatterns.fmethod;

public class DryRunCommandReader extends AbstractCommandReader {

	@Override
	protected Runnable createMovementCommand(int dx, int dy) {
		return new DryRunCommand(String.format(
			"Move %d horizontally and %d vertically",
			dx, dy
		));
	}

}
