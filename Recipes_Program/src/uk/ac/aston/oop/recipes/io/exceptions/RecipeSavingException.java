package uk.ac.aston.oop.recipes.io.exceptions;

/**
 * There was a problem saving a recipe.
 */
public class RecipeSavingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecipeSavingException(String message) {
		super(message);
	}

	public RecipeSavingException(String message, Throwable t) {
		super(message, t);
	}

}
