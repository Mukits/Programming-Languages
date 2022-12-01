package uk.ac.aston.oop.dpatterns.fmethod;

public class DryRunCommand implements Runnable {

	private final String message;

	public DryRunCommand(String message) {
		this.message = message;
	}

	@Override
	public void run() {
		System.out.println(message);
	}

}
