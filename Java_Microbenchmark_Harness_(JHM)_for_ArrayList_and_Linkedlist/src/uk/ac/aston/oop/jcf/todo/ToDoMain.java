package uk.ac.aston.oop.jcf.todo;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Text-based application to keep a to-do list. 
 */
public class ToDoMain {

	private final ToDoList todo = new ToDoList();
	private Random rnd = new Random();
	private Scanner sc = new Scanner(System.in);

	public void run() throws Exception {
		boolean done = false;
		while (!done) {
			printList();
			MenuOption option = pickOption();
			done = option.getCommand().call();
		}
	}

	private void printList() {
		int i = 1;
		if (todo.isEmpty()) {
			System.out.println("No tasks in your TODO list");
		} else {
			System.out.println();
			System.out.println("Your TODO list:");
			for (ToDoItem item : todo) {
				System.out.println(String.format(
					"%d. %s (%s)", i++,
					item.getDescription(),
					item.isDone() ? "done" : "to do"));
			}
		}
		System.out.println();
	}

	private MenuOption pickOption() {
		MenuOption[] options = getOptions();

		// To make the program more user-friendly, option numbers start at 1.
		System.out.println("Available options:");
		for (int i = 0; i < options.length; i++) {
			System.out.println(String.format("%d. %s", i + 1, options[i].getDescription()));
		}

		final int selected = promptInteger("Enter an option number", 1, options.length);
		return options[selected - 1];
	}

	private MenuOption[] getOptions() {
		/*
		 * Note: "this::addItem" is a special Java notation
		 * which creates an object implementing Callable<Boolean>,
		 * where its call() method will run this.addItem(). 
		 */
		return new MenuOption[] {
			new MenuOption("Add item", this::addItem),
			new MenuOption("Mark as done", this::markDone),
			new MenuOption("Remove item by position", this::removeByPosition),
			new MenuOption("Remove by text", this::removeByText),
			new MenuOption("Remove all done items", this::removeDone),
			new MenuOption("Move to the top", this::moveToTop),
			new MenuOption("Shuffle", this::shuffle),
			new MenuOption("Quit", this::quit),
		};
	}

	private boolean addItem() {
		String desc = promptString("Enter the description of the new task: ");
		todo.add(new ToDoItem(desc));
		return false;
	}
	
	private boolean markDone() {
		if (!todo.isEmpty()) {
			int option = promptToDoItemPosition();
			todo.markDone(option - 1);
		}
		return false;
	}

	private boolean removeByPosition() {
		if (!todo.isEmpty()) {
			int option = promptToDoItemPosition();
			todo.remove(option - 1);
		}
		return false;
	}

	private boolean removeByText() {
		if (!todo.isEmpty()) {
			String substring = promptString("Enter the search string: ");
			todo.removeAllContaining(substring);
		}
		return false;
	}

	private boolean removeDone() {
		todo.removeAllDone();
		return false;
	}
		
	private boolean moveToTop() {
		if (!todo.isEmpty()) {
			int option = promptToDoItemPosition();
			todo.moveToTop(option - 1);
		}
		return false;
	}

	private boolean shuffle() {
		todo.shuffle(rnd);
		return false;
	}

	private boolean quit() {
		System.out.println("Goodbye!");
		return true;
	}

	private String promptString(String question) {
		System.out.print(question);
		return sc.nextLine();
	}

	/**
	 * Repeatedly asks the user for an integer in the range [min, max] (both
	 * included), until a valid integer in that range has been read.
	 * 
	 * @param question Question to ask to the user.
	 * @param int Minimum acceptable value that the user may input.
	 * @param int Maximum acceptable value that the user may input.
	 */
	private int promptInteger(String question, int min, int max) {
		do {
			System.out.print(String.format("%s (%d-%d): ", question, min, max));
			try {
				int selected = sc.nextInt();
				if (selected >= min && selected <= max) {
					// Discard the rest of the line
					sc.nextLine();
					return selected;
				}
			} catch (InputMismatchException ex) {
				System.err.println("Please enter a number.");

				// Discard the rest of the line
				sc.nextLine();
			}
		} while (true);
	}

	/**
	 * Convenience version of {@link #promptInteger(String, int, int)} for picking
	 * an item based on its position in the list (starting at 1).
	 */
	private int promptToDoItemPosition() {
		return promptInteger("Enter the number of the task", 1, todo.size());
	}

	public static void main(String[] args) {
		try {
			new ToDoMain().run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
