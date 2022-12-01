package uk.ac.aston.oop.jcf.bench;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Runs microbenchmarks for adding a number of elements at
 * the beginning and end of a list, for the ArrayList and
 * LinkedList implementations of the JCF List interfaces.
 */
public class ListBenchmarks {

	private static final int ELEMENTS = 100;
	
	@Benchmark
	public void addToEndArrayList() {
		addToEnd(new ArrayList<>(), ELEMENTS);
	}

	@Benchmark
	public void addToEndLinkedList() {
		addToEnd(new LinkedList<>(), ELEMENTS);
	}

	@Benchmark
	public void addToBeginningArrayList() {
		addToBeginning(new ArrayList<>(), ELEMENTS);
	}

	@Benchmark
	public void addToBeginningLinkedList() {
		addToBeginning(new LinkedList<>(), ELEMENTS);
	}

	protected void addToEnd(List<Integer> l, int n) {
		for (int i = 0; i < n; i++) {
			l.add(i);
		}
	}

	protected void addToBeginning(List<Integer> l, int n) {
		for (int i = 0; i < n; i++) {
			l.add(0, i);
		}
	}

	public static void main(String[] args) throws RunnerException {
		 Options options = new OptionsBuilder().include(
			ListBenchmarks.class.getSimpleName()
		).forks(1).build();
		new Runner(options).run();
	}

}
