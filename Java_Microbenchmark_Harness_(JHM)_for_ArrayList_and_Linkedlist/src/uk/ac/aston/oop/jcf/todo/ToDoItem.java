package uk.ac.aston.oop.jcf.todo;

import java.util.Objects;

public class ToDoItem {

	private final String description;
	private boolean done = false;

	public ToDoItem(String description) {
		this.description = description;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, done);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ToDoItem other = (ToDoItem) obj;
		return Objects.equals(description, other.description) && done == other.done;
	}

	@Override
	public String toString() {
		return "ToDoItem [description=" + description + ", done=" + done + "]";
	}
	
}
