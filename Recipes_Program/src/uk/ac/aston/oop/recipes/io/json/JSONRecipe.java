package uk.ac.aston.oop.recipes.io.json;

import uk.ac.aston.oop.recipes.model.Recipe;

/**
 * Stores only the information we want to store about the
 * recipe in JSON format.
 * 
 * The properties in the normal {@link Recipe} object do not
 * serialise well to JSON with the automated mappings in the
 * Gson library.
 */
public class JSONRecipe {

	private final String name, ingredients, instructions;
	private final int cookingMinutes;

	public JSONRecipe(Recipe r) {
		name = r.nameProperty().get();
		ingredients = r.ingredientsProperty().get();
		instructions = r.instructionsProperty().get();
		cookingMinutes = r.cookingMinutesProperty().get();
	}

	/**
	 * Creates a new {@link Recipe} based on the information
	 * read from the JSON file.
	 */
	public Recipe buildRecipe() {
		Recipe r = new Recipe();

		r.cookingMinutesProperty().set(cookingMinutes);
		r.nameProperty().set(name);
		r.ingredientsProperty().set(ingredients);
		r.instructionsProperty().set(instructions);

		return r;
	}
}
