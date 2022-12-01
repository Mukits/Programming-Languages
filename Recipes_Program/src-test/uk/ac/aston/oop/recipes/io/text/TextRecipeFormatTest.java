package uk.ac.aston.oop.recipes.io.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import org.junit.jupiter.api.Test;

import uk.ac.aston.oop.recipes.io.AbstractFormatTest;
import uk.ac.aston.oop.recipes.io.exceptions.RecipeLoadingException;
import uk.ac.aston.oop.recipes.model.Recipe;

public class TextRecipeFormatTest extends AbstractFormatTest {

	public TextRecipeFormatTest() {
		super(TextRecipeFormat::new, new File("sample-files/potato-salad.txt"));
	}

	@Test
	public void badNoInstructions() {
		assertThrows(RecipeLoadingException.class, () -> {
			new TextRecipeFormat().load(new File("sample-files/bad-no-instructions.txt"));
		}, "Loading a file with no instructions header should throw an exception");
	}

	@Test
	public void badNoIngredients() {
		assertThrows(RecipeLoadingException.class, () -> {
			new TextRecipeFormat().load(new File("sample-files/bad-no-ingredients.txt"));
		}, "Loading a file with no ingredients should throw an exception");
	}

	@Test
	public void badNoName() {
		assertThrows(RecipeLoadingException.class, () -> {
			new TextRecipeFormat().load(new File("sample-files/bad-no-name.txt"));
		}, "Loading a file with no recipe name should throw an exception");
	}

	@Test
	public void potatoSaladEmptyInstructions() throws Exception {
		File f = new File("sample-files/potato-salad-empty-instructions.txt");
		Recipe recipe = new TextRecipeFormat().load(f);

		assertEquals(SAMPLE_RECIPE_NAME, recipe.nameProperty().get(),
			f.getName() + " should be loaded without errors: checking name");
		assertEquals("", recipe.instructionsProperty().get(),
			f.getName() + " should be read as having empty instructions");
	}
	
}
