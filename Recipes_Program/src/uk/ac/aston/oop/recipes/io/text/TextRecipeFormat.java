package uk.ac.aston.oop.recipes.io.text;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import uk.ac.aston.oop.recipes.io.RecipeFormat;
import uk.ac.aston.oop.recipes.io.exceptions.RecipeLoadingException;
import uk.ac.aston.oop.recipes.io.exceptions.RecipeSavingException;
import uk.ac.aston.oop.recipes.model.Recipe;

public class TextRecipeFormat implements RecipeFormat {

	public static final String RECIPE_HEADER = "OOPRecipe 1.0";
	public static final String SEPARATOR_INSTRUCTIONS = "__INSTRUCTIONS__";

	@Override
	public String getExtension() {
		return "txt";
	}

	@Override
	public String getDescription() {
		return "Raw Text Files (*.txt)";
	}

	@Override
	public Recipe load(File f) throws RecipeLoadingException {
		try (
			FileReader fr = new FileReader(f, StandardCharsets.UTF_8);
			LineNumberReader lnr = new LineNumberReader(fr)
		) {
			// Line numbers start at 0 by default - make them start at 1
			lnr.setLineNumber(1);
			checkHeader(lnr, f);

			Recipe r = new Recipe();
			readRecipeName(lnr, r);
			readIngredients(lnr, r);
			readInstructions(lnr, r);

			return r;
		} catch (IOException e) {
			throw new RecipeLoadingException("Error while loading recipe file", e);
		}
	}

	private void readInstructions(LineNumberReader lnr, Recipe r) throws IOException {
		List<String> lines = new ArrayList<>();
		String line = lnr.readLine();

		while (line != null) {
			lines.add(line);
			line = lnr.readLine();
		}
		String instructions = String.join(System.lineSeparator(), lines);
		r.instructionsProperty().set(instructions);
	}

	private void readIngredients(LineNumberReader lnr, Recipe r) throws IOException, RecipeLoadingException {
		List<String> lines = new ArrayList<>();
		String line = lnr.readLine();
		while (line != null && !SEPARATOR_INSTRUCTIONS.equals(line)) {
			lines.add(line);
			line = lnr.readLine();
		}
		if (!SEPARATOR_INSTRUCTIONS.equals(line)) {
			throw new RecipeLoadingException("Error on line "
				+ lnr.getLineNumber() + " - expected " + SEPARATOR_INSTRUCTIONS);
		}
		
		String ingredients = String.join(System.lineSeparator(), lines);
		r.ingredientsProperty().set(ingredients);
	}

	private void readRecipeName(LineNumberReader lnr, Recipe r) throws IOException, RecipeLoadingException {
		String line = lnr.readLine();
		if (line == null) {
			throw new RecipeLoadingException("File ended before a recipe name could be read");
		}
		String name = line.strip();
		r.nameProperty().set(name);
	}

	private void checkHeader(LineNumberReader lnr, File f) throws IOException, RecipeLoadingException {
		String line = lnr.readLine();
		if (!RECIPE_HEADER.equals(line)) {
			throw new RecipeLoadingException(
				"Error on line " + lnr.getLineNumber() + " - unexpected header: " + f.getPath() + " is not a recipe file");
		}
	}

	@Override
	public void save(Recipe r, File f) throws RecipeSavingException {
		if (r.ingredientsProperty().get().contains(SEPARATOR_INSTRUCTIONS)) {
			throw new RecipeSavingException(
				"Cannot save a recipe with '" + SEPARATOR_INSTRUCTIONS + "' in the ingredients in text format");
		}

		try (
			FileWriter fw = new FileWriter(f, StandardCharsets.UTF_8);
			PrintWriter pw = new PrintWriter(fw)
		) {
			pw.println(RECIPE_HEADER);
			pw.println(r.nameProperty().get());
			pw.println(r.ingredientsProperty().get());
			pw.println(SEPARATOR_INSTRUCTIONS);
			pw.println(r.instructionsProperty().get());
		}
		catch (IOException e) {
			throw new RecipeSavingException("Error while writing recipe file", e);
		}
	}

}
