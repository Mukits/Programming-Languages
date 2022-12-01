package uk.ac.aston.oop.dpatterns.fmethod;

import java.io.IOException;

public class FMethodMain {

	public static void main(String[] args) {
		AbstractCommandReader cmdReader = null;

		if (args.length > 0 && "dryrun".equals(args[0].strip().toLowerCase())) {
		  cmdReader = new DryRunCommandReader();
		} else {
		  cmdReader = new ExecutingCommandReader();
		}

		try {
			cmdReader.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
