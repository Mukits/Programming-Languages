package uk.ac.aston.oop.recipes;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.NodeQueryUtils;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.Node;
import javafx.stage.Stage;
import uk.ac.aston.oop.recipes.io.json.JSONRecipeFormat;
import uk.ac.aston.oop.recipes.io.text.TextRecipeFormat;
import uk.ac.aston.oop.recipes.model.Recipe;

@ExtendWith(ApplicationExtension.class)
public class RecipesControllerTest {

	private RecipesController controller;

	@Start
	private void start(Stage primaryStage) throws Exception {
		controller = new RecipesController();
	}

	@AfterEach
	public void teardown() throws TimeoutException {
		FxToolkit.cleanupStages();
	}

	@Test
	public void doLoad(FxRobot robot) {
		WaitForAsyncUtils.waitForAsyncFx(5000, () -> {
			controller.doLoad(new JSONRecipeFormat(),
				new File("sample-files/potato-salad.json"));
		});
	}

	@Test
	public void doSave(FxRobot robot) throws Exception {
		File f = File.createTempFile("tmp", ".json");
		f.deleteOnExit();
		Recipe r = new Recipe();

		WaitForAsyncUtils.waitForAsyncFx(5000, () -> {
			controller.doSave(r, new JSONRecipeFormat(), f);
		});
	}

	@Test
	public void doLoadMissing(FxRobot robot) throws InterruptedException, ExecutionException {
		Future<Void> doLoadFuture = WaitForAsyncUtils.asyncFx(() -> {
			controller.doLoad(
				new JSONRecipeFormat(), new File("missing"));
		});

		waitThenClickOnOK(robot, doLoadFuture,
			"Timed out after 5s waiting for an Alert about the missing file to be loaded");
	}

	@Test
	public void doSaveWithSeparators(FxRobot robot) throws InterruptedException, ExecutionException {
		Future<Void> doLoadFuture = WaitForAsyncUtils.asyncFx(() -> {
			Recipe r = new Recipe();
			r.ingredientsProperty().set(TextRecipeFormat.SEPARATOR_INSTRUCTIONS);
			controller.doSave(r, new TextRecipeFormat(), new File("missing.txt"));
		});

		waitThenClickOnOK(robot, doLoadFuture,
			"Timed out after 5s waiting for an Alert about the text file "
				+ "not being saved because the instructions have "
				+ TextRecipeFormat.SEPARATOR_INSTRUCTIONS);
	}

	protected void waitThenClickOnOK(FxRobot robot, Future<Void> doLoadFuture, String msg) {
		try {
			WaitForAsyncUtils.waitFor(5, TimeUnit.SECONDS, () -> {
				Optional<Node> query = robot.lookup(".button").tryQuery();
				try {
					if (query.isPresent() && NodeQueryUtils.isVisible().test(query.get())) {
						Node n = query.get();
						robot.clickOn(n);
						return true;
					}
				} catch (NullPointerException ex) {
					// Workaround for PrismTextLayout sometimes producing an NPE
				}
				return false;
			});
		} catch (TimeoutException ex) {
			fail(msg);
		}

		// Wait for the window to be fully closed and removed from the robot
		try {
			doLoadFuture.get(5, TimeUnit.SECONDS);
			WaitForAsyncUtils.waitFor(5, TimeUnit.SECONDS, () -> {
				return robot.listWindows().size() == 0;
			});
		} catch (Exception e) {
			fail("Timed out after waiting for the Alert to close after clicking OK");
		}
	}
	
}
