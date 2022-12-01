package uk.ac.aston.oop.recipes.io.json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import uk.ac.aston.oop.recipes.io.RecipeFormat;
import uk.ac.aston.oop.recipes.io.exceptions.RecipeLoadingException;
import uk.ac.aston.oop.recipes.io.exceptions.RecipeSavingException;
import uk.ac.aston.oop.recipes.model.Recipe;

public class JSONRecipeFormat implements RecipeFormat {

	@Override
	public String getExtension() {
		return "json";
	}

	@Override
	public String getDescription() {
		return "JSON Files (*.json)";
	}

	@Override
	public Recipe load(File f) throws RecipeLoadingException {
		try (
			FileReader fr = new FileReader(f, StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(fr)
		) {
			Gson gson = new Gson();
			JSONRecipe jsonRecipe = gson.fromJson(br, JSONRecipe.class);
			if (jsonRecipe == null) {
				throw new RecipeLoadingException("Could not read recipe from " + f.getPath());
			}
			return jsonRecipe.buildRecipe();
		} catch (IOException e) {
			throw new RecipeLoadingException(
				"Error while loading recipe file", e);
		}
	}

	@Override
	public void save(Recipe r, File f) throws RecipeSavingException {
		try (
			FileWriter fw = new FileWriter(f, StandardCharsets.UTF_8);
			BufferedWriter bw = new BufferedWriter(fw)
		) {
			Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
			JSONRecipe jsonRecipe = new JSONRecipe(r);
			gson.toJson(jsonRecipe, bw);
		} catch (IOException e) {
			throw new RecipeSavingException(
				"Error while writing recipe file", e);
		}
	}

}
