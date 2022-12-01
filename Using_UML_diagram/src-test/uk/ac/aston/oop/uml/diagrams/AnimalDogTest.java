package uk.ac.aston.oop.uml.diagrams;

import org.junit.jupiter.api.Test;

public class AnimalDogTest extends PlantUMLTest {

	public AnimalDogTest() {
		super("01-animal-dog");
	}

	@Test
	public void animal() throws Exception {
		assertTypeHas("Animal",
			attribute(Visibility.PRIVATE, "name", "String"),
			operation(Visibility.PUBLIC, "getName", "String"));
	}

	@Test
	public void dog() throws Exception {
		assertTypeHas("Dog",
			attribute(Visibility.PRIVATE, "hasBone", "Boolean"),
			operation(Visibility.PUBLIC, "giveBone", parameter("b", "Bone")));

		assertClassExtends("Dog", "Animal");
	}

	@Test
	public void bone() throws Exception {
		assertTypeHas("Bone");
	}
}
