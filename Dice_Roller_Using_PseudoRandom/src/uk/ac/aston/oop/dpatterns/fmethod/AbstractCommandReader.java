package uk.ac.aston.oop.dpatterns.fmethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class AbstractCommandReader {

	protected abstract Runnable createMovementCommand(int dx, int dy);

	public void run() throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"))) {
			System.out.println("Type one of 'left', 'right', 'up', 'down' or 'exit'.");

			System.out.print("> ");
			String line;
			boolean done = false;
			while (!done && (line = br.readLine()) != null) {
				line = line.trim().toLowerCase();
				done = processLine(line);
				System.out.print("> ");
			}
		}
	}

	protected boolean processLine(String line) {
		switch (line) {
		case "left":
			createMovementCommand(-1, 0).run();
			return false;
		case "right":
			createMovementCommand(1, 0).run();
			return false;
		case "down":
			createMovementCommand(0, 1).run();
			return false;
		case "up":
			createMovementCommand(0, -1).run();
			return false;
		case "exit":
			return true;
		default:
			System.err.println("Unrecognized command " + line);
			return false;
		}
	}

}
