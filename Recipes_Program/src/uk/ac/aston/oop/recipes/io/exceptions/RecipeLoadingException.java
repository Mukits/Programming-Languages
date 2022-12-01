package uk.ac.aston.oop.recipes.io.exceptions;

/**
 * There was a problem loading a recipe from a file.
 */
public class RecipeLoadingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecipeLoadingException(String message) {
		super(message);
	}

	public RecipeLoadingException(String message, Throwable t) {
		super(message, t);
	}

}
