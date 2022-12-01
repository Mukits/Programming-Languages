package uk.ac.aston.oop.dpatterns.fmethod;

import org.mockito.ArgumentMatcher;

class MovementMessageMatcher implements ArgumentMatcher<String> {
	private final int dx, dy;

	public MovementMessageMatcher(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public boolean matches(String argument) {
		return argument.contains(dx + "")
			&& argument.contains(dy + "");
	}

	@Override
	public String toString() {
		return String.format("(any string containing %d and %d)", dx, dy);
	}
}