package uk.ac.aston.oop.acint;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import uk.ac.aston.oop.acint.shapes.Circle;
import uk.ac.aston.oop.acint.shapes.Cross;
import uk.ac.aston.oop.acint.shapes.Drawable;
import uk.ac.aston.oop.acint.shapes.Ellipse;
import uk.ac.aston.oop.acint.shapes.Rectangle;
import uk.ac.aston.oop.acint.util.GraphicsContextWrapper;

/**
 * Main class for the Shapes lab.
 */
public class Launcher extends Application {

	private Drawable[] drawables;
	private Canvas canvas;
	private GraphicsContext graphics;

	private int iFrame = 0;

	/**
	 * Starts the application, which has only an empty canvas where we can draw
	 * once per second. Don't worry too much about the details: we will explain
	 * JavaFX in more detail in a later lecture.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		VBox root = new VBox();
		canvas = new Canvas(500, 500);
		root.getChildren().add(canvas);
		graphics = canvas.getGraphicsContext2D();

		stage.setResizable(false);
		stage.setScene(new Scene(root));
		stage.show();

		drawables = new Drawable[4];
		drawables[0] = new Rectangle(10 * iFrame, 10 * iFrame, 50, 50);
		drawables[1] = new Circle(200, 300 - iFrame*5, 100);
		drawables[2] = new Ellipse(150, 300, 90 + iFrame, 160);
		drawables[3] = new Cross(60, 60);
		
		// Asks drawScene() to be run 30 times per second, Processing-style
		draw();
		Timeline drawEverySecond = new Timeline(
			new KeyFrame(Duration.millis(1000/30), (event) -> {
				draw();
			})
		);
		drawEverySecond.setCycleCount(Timeline.INDEFINITE);
		drawEverySecond.play();
	}

	private void draw() {
		graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		GraphicsContextWrapper gcw = new GraphicsContextWrapper(graphics);

		// A while loop should work (or a do-while or 3-part for loop)
		for (Drawable s : drawables) {
			s.draw(gcw);
			s.move(gcw, 1, 1);
			if (s instanceof Circle) {
				Circle c = (Circle) s;
				c.scale(0.99);
			}
		}

		iFrame = (iFrame + 1) % 30;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
