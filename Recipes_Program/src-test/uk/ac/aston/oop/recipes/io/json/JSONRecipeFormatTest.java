package uk.ac.aston.oop.recipes.io.json;

import java.io.File;

import uk.ac.aston.oop.recipes.io.AbstractFormatTest;

public class JSONRecipeFormatTest extends AbstractFormatTest {

	public JSONRecipeFormatTest() {
		super(JSONRecipeFormat::new, new File("sample-files/potato-salad.json"));
	}

}
