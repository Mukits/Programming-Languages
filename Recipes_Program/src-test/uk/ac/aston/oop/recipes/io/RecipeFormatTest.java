package uk.ac.aston.oop.recipes.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import uk.ac.aston.oop.recipes.model.Recipe;

public class RecipeFormatTest {

	@Test
	public void loadThrowsException() throws Exception {
		Method mLoad = RecipeFormat.class.getMethod("load", File.class);
		assertMethodThrows(mLoad, "RecipeLoadingException");
	}

	@Test
	public void saveThrowsException() throws Exception {
		Method mLoad = RecipeFormat.class.getMethod("save", Recipe.class, File.class);
		assertMethodThrows(mLoad, "RecipeSavingException");
	}
	
	protected void assertMethodThrows(Method mLoad, String exceptionName) {
		Class<?>[] exTypes = mLoad.getExceptionTypes();
		assertEquals(1, exTypes.length, mLoad.getName() + " should throw one kind of exception");
		assertEquals("uk.ac.aston.oop.recipes.io.exceptions." + exceptionName,
			exTypes[0].getName(),
			mLoad.getName() + " should throw the right exception");
	}

}
