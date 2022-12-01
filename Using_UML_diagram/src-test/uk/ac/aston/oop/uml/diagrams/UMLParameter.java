package uk.ac.aston.oop.uml.diagrams;

class UMLParameter {
	private final String name, type;

	public UMLParameter(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "UMLParameter [name=" + name + ", type=" + type + "]";
	}
}