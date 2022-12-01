package uk.ac.aston.oop.recipes.io;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import uk.ac.aston.oop.recipes.io.json.JSONRecipeFormat;
import uk.ac.aston.oop.recipes.io.text.TextRecipeFormat;

/**
 * Singleton that holds a reference to all the kinds of {@link RecipeFormat} available.
 */
public class RecipeFormatRegistry {

	private static final RecipeFormatRegistry INSTANCE = new RecipeFormatRegistry();
	
	private final Map<String, RecipeFormat> loadersByExtension = new HashMap<>();

	private RecipeFormatRegistry() {
		addLoader(new JSONRecipeFormat());
		addLoader(new TextRecipeFormat());
	}

	public static RecipeFormatRegistry getInstance() {
		return INSTANCE;
	}

	/**
	 * Returns the {@link RecipeFormat} that matches the extension of the given file.
	 *
	 * @param f File to be loaded.
	 * @return RecipeFormat to be used, if there is a match.
	 */
	public Optional<RecipeFormat> getLoaderByFile(File f) {
		int idxDot = f.getName().lastIndexOf('.');
		if (idxDot >= 0) {
			String ext = f.getName().substring(idxDot + 1).toLowerCase();
			RecipeFormat loader = loadersByExtension.get(ext);
			if (loader != null) {
				return Optional.of(loader);
			}
		}
		return Optional.empty();
	}

	public Collection<RecipeFormat> getLoaders() {
		return loadersByExtension.values();
	}

	public void addLoader(RecipeFormat loader) {
		loadersByExtension.put(loader.getExtension().toLowerCase(), loader);
	}

}
