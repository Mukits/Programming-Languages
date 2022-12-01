module uk.ac.aston.oop.dpatterns {
	requires transitive java.desktop;

	requires javafx.controls;
	requires transitive javafx.graphics;

	exports uk.ac.aston.oop.dpatterns.afactory;
	exports uk.ac.aston.oop.dpatterns.afactory.javafx;
	exports uk.ac.aston.oop.dpatterns.afactory.swing;
	exports uk.ac.aston.oop.dpatterns.fmethod;
	exports uk.ac.aston.oop.dpatterns.singleton;
}