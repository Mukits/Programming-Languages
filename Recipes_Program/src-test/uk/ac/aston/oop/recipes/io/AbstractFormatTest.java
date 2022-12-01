package uk.ac.aston.oop.recipes.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import uk.ac.aston.oop.recipes.io.exceptions.RecipeLoadingException;
import uk.ac.aston.oop.recipes.io.exceptions.RecipeSavingException;
import uk.ac.aston.oop.recipes.model.Recipe;

/**
 * Common tests for all formats.
 */
public abstract class AbstractFormatTest {

	protected static final int SAMPLE_RECIPE_MINS = 20;
	protected static final String SAMPLE_RECIPE_INSTRUCTIONS
		= "Basically I\u0027m just making potato salad.\nI haven\u0027t decided what kind yet.\n";
	protected static final String SAMPLE_RECIPE_INGREDIENTS
		= "Good mayonnaise\nPotatoes\nTuna\nChives\nRoasted peppers";
	protected static final String SAMPLE_RECIPE_NAME
		= "Potato Salad";

	private Supplier<RecipeFormat> fmtSupplier;
	private File sampleFile;

	public AbstractFormatTest(Supplier<RecipeFormat> fmtSupplier, File sampleFile) {
		this.fmtSupplier = fmtSupplier;
		this.sampleFile = sampleFile;
	}

	@Test
	public void saveLoadEqual() throws Exception {
		Recipe r = createSimpleRecipe();

		File f = File.createTempFile("tmp", ".json");
		f.deleteOnExit();
		fmtSupplier.get().save(r, f);
		assertTrue(f.exists(), "Saving the recipe should create a new file");

		Recipe r2 = fmtSupplier.get().load(f);
		assertNotNull(r2, "Loading a recipe file should create a new Recipe");
		assertEquals(r.nameProperty().get(), r2.nameProperty().get(),
			"Name should be saved and loaded correctly");
		assertEquals(r.instructionsProperty().get(), r2.instructionsProperty().get(),
			"Instructions should be saved and loaded correctly");
		assertEquals(r.ingredientsProperty().get(), r2.ingredientsProperty().get(),
			"Ingredients should be saved and loaded correctly");
		assertEquals(r.cookingMinutesProperty().get(), r2.cookingMinutesProperty().get(),
			"Cooking minutes should be saved and loaded correctly");
	}

	@Test
	public void loadEmptyFile() throws Exception {
		File f = File.createTempFile("tmp", "file");
		f.deleteOnExit();
		assertThrows(RecipeLoadingException.class, () -> {
			fmtSupplier.get().load(f);
		}, "Trying to load an empty file should throw a RecipeLoadingException");
	}
	
	@Test
	public void loadMissingFile() {
		assertThrows(RecipeLoadingException.class, () -> {
			fmtSupplier.get().load(new File("i-do-not-exist"));
		}, "Trying to load a missing file should throw a RecipeLoadingException");
	}

	@Test
	public void saveIntoUnwritableFile() throws IOException {
		File f = File.createTempFile("tmp", "file");
		f.deleteOnExit();
		f.setWritable(false);

		Recipe r = createSimpleRecipe();
		assertThrows(RecipeSavingException.class, () -> {
			fmtSupplier.get().save(r, f);
		}, "Saving into an unwritable should throw a RecipeSavingException");
	}

	@Test
	public void loadSampleFile() throws RecipeLoadingException {
		Recipe recipe = fmtSupplier.get().load(sampleFile);
		assertNotNull(recipe, "load() should return a Recipe");

		assertEquals(SAMPLE_RECIPE_NAME, recipe.nameProperty().get(),
			"The name of the Recipe should be read correctly");
		assertEquals(SAMPLE_RECIPE_INGREDIENTS,
			recipe.ingredientsProperty().get(),
			"The ingredients of the Recipe should be read correctly");
		assertEquals(SAMPLE_RECIPE_INSTRUCTIONS,
			recipe.instructionsProperty().get(),
			"The instructions of the Recipe should be read correctly");
		assertEquals(SAMPLE_RECIPE_MINS, recipe.cookingMinutesProperty().get(),
			"The cooking time in minutes of the Recipe should be read correctly");
	}


	protected Recipe createSimpleRecipe() {
		Recipe r = new Recipe();
		r.nameProperty().set("A");
		r.instructionsProperty().set("step 1\nstep 2\nstep 3");
		r.ingredientsProperty().set("a\nb\nc\nd");
		r.cookingMinutesProperty().set(20);
		return r;
	}

}