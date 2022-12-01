package uk.ac.aston.oop.dpatterns.fmethod;

public class ExecutingCommand implements Runnable {

	private final ExecutingCommandReader reader;
	private final int dx, dy;

	public ExecutingCommand(ExecutingCommandReader reader, int dx, int dy) {
		this.reader = reader;
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public void run() {
		reader.setX(reader.getX() + dx);
		reader.setY(reader.getY() + dy);
		System.out.println(String.format(
			"Currently at (%d, %d)",
			reader.getX(), reader.getY()));
	}

}
