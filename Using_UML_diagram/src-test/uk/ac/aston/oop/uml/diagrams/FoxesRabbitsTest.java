package uk.ac.aston.oop.uml.diagrams;

import org.junit.jupiter.api.Test;

public class FoxesRabbitsTest extends PlantUMLTest {

	public FoxesRabbitsTest() {
		super("04-foxes-rabbits");
	}

	@Test
	public void simulator() {
		assertTypeHas("Simulator",
			operation("act"),
			operation("getField", "Field"),
			operation("getFoxProbability", "double"),
			operation("getRabbitProbability", "double"),
			operation("getStep", "int"),
			operation("populate"),
			operation("setFoxProbability", parameter("p", "double")),
			operation("setRabbitProbability", parameter("p", "double"))
		);
	}

	@Test
	public void field() {
		assertTypeHas("Field",
			operation("getGrid", "Grid"),
			operation("getContents", "List<Animal>"),
			operation("getCount", "int", parameter("c", "Class")));
	}

	@Test
	public void grid() {
		assertTypeHas("Grid",
			operation("getWidth", "int"),
			operation("getHeight", "int"),
			operation("get", "GridCell", parameter("i", "int"), parameter("j", "int")));
	}

	@Test
	public void gridCell() {
		assertTypeHas("GridCell",
			operation("getContents", "List<Animal>"),
			operation("getRow", "int"),
			operation("getColumn", "int"),
			operation("getGrid", "Grid"),
			operation("getAdjacent", "List<GridCell>"),
			operation("getFreeAdjacent", "List<GridCell>"),
			operation("getRandomFreeAdjacent", "GridCell", parameter("r", "Random")));
	}

	@Test
	public void animal() {
		// Note: PlantUML XMI export does not mention abstract methods
		assertTypeHas("Animal",
			operation("act", parameter("r", "Random")),
			operation(Visibility.PROTECTED, "breed", parameter("r", "Random")),
			operation("getAge", "int"),
			operation("getLifespan", "int"),
			operation("getMaxLitterSize", "int"),
			operation("isAlive", "boolean"),
			operation("setAlive", parameter("alive", "boolean")));
	}

	@Test
	public void fox() {
		assertTypeHas("Fox", operation("act", parameter("r", "Random")));
		assertClassExtends("Fox", "Animal");
	}

	@Test
	public void rabbit() {
		assertTypeHas("Rabbit", operation("act", parameter("r", "Random")));
		assertClassExtends("Rabbit", "Animal");
	}

	@Test
	public void relationships() throws Exception {
		assertAssociationHas("simulates",
			associationEnd(Aggregation.COMPOSITE, "Simulator"),
			associationEnd("Field")
		);

		assertAssociationHas("grid",
			associationEnd(Aggregation.COMPOSITE, "Field"),
			associationEnd("Grid")
		);

		assertAssociationHas("cells",
			associationEnd(Aggregation.COMPOSITE, "Grid"),
			associationEnd("1..*", "GridCell")
		);

		assertAssociationHas("contents",
			associationEnd(Aggregation.AGGREGATE, "GridCell"),
			associationEnd("*", "Animal"));
	}

}
