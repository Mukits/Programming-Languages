package uk.ac.aston.oop.uml.diagrams;

import uk.ac.aston.oop.uml.diagrams.PlantUMLTest.Aggregation;

class UMLAssociationEnd {

	private final Aggregation aggregation;
	private final String multiplicity, typeName;

	public UMLAssociationEnd(Aggregation aggregation, String multiplicity, String typeName) {
		this.aggregation = aggregation;
		this.multiplicity = multiplicity;
		this.typeName = typeName;
	}

	public Aggregation getAggregation() {
		return aggregation;
	}

	public String getMultiplicity() {
		return multiplicity;
	}

	public String getTypeName() {
		return typeName;
	}

}
