package uk.ac.aston.oop.jcf.generics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GenericDuplicateShuffle {

	/*
	 * This can be based on the shuffle from ToDoList (just needs
	 * adding the type parameter T, and a List<T> parameter called
	 * 'items'.
	 */
	public static <T> void shuffle(Random rnd, List<T> items) {
		for (int i = 0; i < items.size(); i++) {
			final T item = items.get(i);
			final int otherPosition = rnd.nextInt(items.size());
			items.set(i, items.get(otherPosition));
			items.set(otherPosition, item);
		}
	}

	/*
	 * Adds a certain number of copies of an element to a list
	 * of items.
	 */
	public static <T> void duplicate(T elem, int copies, List<? super T> items) {
		for (int i = 0; i < copies; i++) {
			items.add(elem);
		}
	}

	/*
	 * NOTE: this method should work as-is, without changes.
	 * The AutoFeedback tests will assume 
	 */
	public static void main(String[] args) {
		Random rnd = new Random();

		List<Object> l = new ArrayList<>();
		duplicate("A", 2, l);
		duplicate("B", 3, l);
		duplicate("C", 2, l);
		shuffle(rnd, l);
		System.out.println(l);

		List<Number> ln = new LinkedList<>();
		duplicate(1.0f, 3, ln);
		duplicate(1.5f, 5, ln);
		duplicate(5, 2, ln);
		shuffle(rnd, ln);
		System.out.println(ln);
	}
	
}
