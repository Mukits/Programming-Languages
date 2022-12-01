package uk.ac.aston.oop.recipes.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Recipe {

	public static final String DEFAULT_NAME = "Unnamed recipe";
	public static final String DEFAULT_INGREDIENTS = "No ingredients yet";
	public static final String DEFAULT_INSTRUCTIONS = "No instructions yet";
	public static final int DEFAULT_COOK_TIME = 20;

	private final StringProperty name = new SimpleStringProperty(DEFAULT_NAME);
	private final IntegerProperty cookingMinutes = new SimpleIntegerProperty(DEFAULT_COOK_TIME);
	private final StringProperty ingredients = new SimpleStringProperty(DEFAULT_INGREDIENTS);
	private final StringProperty instructions = new SimpleStringProperty(DEFAULT_INSTRUCTIONS);

	public StringProperty nameProperty() {
		return name;
	}

	public IntegerProperty cookingMinutesProperty() {
		return cookingMinutes;
	}

	public StringProperty ingredientsProperty() {
		return ingredients;
	}

	public StringProperty instructionsProperty() {
		return instructions;
	}

	@Override
	public String toString() {
		return String.format("%s (%d min)", name.get(), cookingMinutes.get());
	}
}
