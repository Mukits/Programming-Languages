package uk.ac.aston.oop.uml.media;

import java.util.ArrayList;
import java.util.List;

public class Database {

	private final List<Item> items = new ArrayList<>();

	public void print() {
		for (Item i : items) {
			System.out.println(i.toString());
		}
	}

	public void addItem(Item item) {
		items.add(item);
	}

}
