module src {
	// We need these modules from JavaFX
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;

	// We have to export our own packages so JavaFX can access them
	exports uk.ac.aston.oop.javafx.prep;
	exports uk.ac.aston.oop.javafx.prep.model;

	opens uk.ac.aston.oop.javafx.prep;
}