package uk.ac.aston.oop.uml.diagrams;

import org.junit.jupiter.api.Test;

public class IterableCollectionTest extends PlantUMLTest {

	public IterableCollectionTest() {
		super("02-interfaces");
	}

	@Test
	public void iterable() throws Exception {
		assertTypeHas("Iterable",
			operation(Visibility.PUBLIC, "iterator", "Iterator"));
	}

	@Test
	public void collection() throws Exception {
		assertTypeHas("Collection", operation("clear"));
		assertTypeIsInterface("Collection");

		// The XMI export in PlantUML cannot tell the difference
		// between realisation and generalisation
		assertClassExtends("Collection", "Iterable");
	}

	@Test
	public void abstractList() throws Exception {
		assertTypeHas("AbstractList",
			attribute(Visibility.PRIVATE, "size", "int"),
			operation(Visibility.PUBLIC, "size", "int"));

		assertClassIsAbstract("AbstractList");
		assertClassExtends("AbstractList", "Collection");
	}

}
