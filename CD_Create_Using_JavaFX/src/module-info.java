module src {
	// We need these modules from JavaFX
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;

	// We have to export our own packages so JavaFX can access them
	exports uk.ac.aston.oop.javafx.assessed;
	exports uk.ac.aston.oop.javafx.assessed.model;

	opens uk.ac.aston.oop.javafx.assessed;
}