package uk.ac.aston.oop.jcf.todo;

import java.util.concurrent.Callable;

public class MenuOption {
	private final String description;
	private final Callable<Boolean> command;

	public MenuOption(String description, Callable<Boolean> command) {
		this.description = description;
		this.command = command;
	}

	public String getDescription() {
		return description;
	}

	public Callable<Boolean> getCommand() {
		return command;
	}
}