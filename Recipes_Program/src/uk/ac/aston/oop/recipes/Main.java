package uk.ac.aston.oop.recipes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/MainWindow.fxml"));
		loader.setController(new RecipesController());
		Parent root = loader.load();

		Scene scene = new Scene(root, 650, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Recipe Book");
		primaryStage.setMinWidth(650);
		primaryStage.setMinHeight(450);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
