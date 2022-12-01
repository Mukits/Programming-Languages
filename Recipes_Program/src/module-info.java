module recipes {
	// We need these modules from JavaFX
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires com.google.gson;

	// We have to export our own packages so JavaFX can access them
	exports uk.ac.aston.oop.recipes;
	exports uk.ac.aston.oop.recipes.io;
	exports uk.ac.aston.oop.recipes.model;

	opens uk.ac.aston.oop.recipes;
	opens uk.ac.aston.oop.recipes.io;
	opens uk.ac.aston.oop.recipes.io.json to com.google.gson;
}