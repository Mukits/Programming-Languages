package uk.ac.aston.oop.javafx.assessed;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.aston.oop.javafx.assessed.model.CD;
import uk.ac.aston.oop.javafx.assessed.model.Database;
import uk.ac.aston.oop.javafx.assessed.model.Video;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
	    	final Database d = createDatabase();

			final FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ListScene.fxml"));
			loader.setController(new ListController(d));
			final Parent root = loader.load();

			final Scene scene = new Scene(root, 400, 300);
			primaryStage.setTitle("List database");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private Database createDatabase() {
		final Database d = new Database();

		final CD cd1 = new CD("Vivaldi concertos", "Giuliano Carmignola",	12, 60);
		cd1.setComment("My favourite Baroque violinist.");
		cd1.setOwn(true);
		d.addItem(cd1);

		final CD cd2 = new CD("Schubert sonata in A", "Radu Lupu", 4, 35);
		d.addItem(cd2);

		final Video v1 = new Video("Seven Samurai", "Akira Kurosawa", 206);
		v1.setComment("The classic epic.");
		d.addItem(v1);

		final Video v2 = new Video("La Belle et la Bete", "Jean Cocteau", 120);
		d.addItem(v2);

		return d;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
