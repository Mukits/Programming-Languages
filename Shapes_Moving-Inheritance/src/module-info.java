module oop.inheritance {
	// We need these two modules from JavaFX
	requires javafx.controls;
	requires transitive javafx.graphics;

	// We have to export our own package so JavaFX can access it
	exports uk.ac.aston.oop.acint;
	exports uk.ac.aston.oop.acint.shapes;
	exports uk.ac.aston.oop.acint.util;
}