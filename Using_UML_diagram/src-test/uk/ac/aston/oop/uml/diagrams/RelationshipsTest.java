package uk.ac.aston.oop.uml.diagrams;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class RelationshipsTest extends PlantUMLTest {

	public RelationshipsTest() {
		super("03-relationships");
	}

	@Test
	public void classes() throws Exception {
		for (String s : Arrays.asList("Lecturer", "Window", "Room", "Student", "Frame", "Wall")) {
			assertTypeHas(s);
		}
	}

	@Test
	public void associations() throws Exception {
		assertAssociationHas("teaches",
			associationEnd("1..*", "Lecturer"),
			associationEnd("*", "Student")
		);

		assertAssociationHas("has",
			associationEnd(Aggregation.COMPOSITE, "Window"),
			associationEnd("Frame")
		);

		assertAssociationHas("is formed of",
			associationEnd(Aggregation.AGGREGATE, "*", "Room"),
			associationEnd("3..*", "Wall"));
	}

}
