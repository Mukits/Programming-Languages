package uk.ac.aston.oop.dpatterns.afactory;

import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import uk.ac.aston.oop.dpatterns.afactory.javafx.JFXDrawingFactory;
import uk.ac.aston.oop.dpatterns.afactory.swing.SwingDrawingFactory;

public class AFactoryMain extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		List<String> parameters = getParameters().getRaw();
		DrawingFactory factory = createFactory(stage, parameters);

		Drawing drawing = factory.createDrawing(300, 300);
		Circle c1 = factory.createCircle(150, 100, 50);
		Circle c2 = factory.createCircle(100, 150, 50);
		Circle c3 = factory.createCircle(200, 150, 50);

		drawing.setFill(255, 255, 255);
		c1.setFill(255, 0, 0);
		c2.setFill(0, 255, 0);
		c3.setFill(0, 0, 255);
	}

	protected DrawingFactory createFactory(Stage stage, List<String> parameters) {
		if (parameters.contains("swing")) {
			return new SwingDrawingFactory();
		} else {
			return new JFXDrawingFactory(stage);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
