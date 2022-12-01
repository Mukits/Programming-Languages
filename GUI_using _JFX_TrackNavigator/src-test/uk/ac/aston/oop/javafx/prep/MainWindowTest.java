package uk.ac.aston.oop.javafx.prep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import uk.ac.aston.oop.javafx.prep.model.CD;
import uk.ac.aston.oop.javafx.prep.model.Database;
import uk.ac.aston.oop.javafx.prep.model.Item;
import uk.ac.aston.oop.javafx.prep.model.Video;

@ExtendWith(ApplicationExtension.class)
public class MainWindowTest {

	private Database database;
	private Stage stage;

	@Start
	private void start(Stage stage) throws IOException {
		this.stage = stage;
		this.database = new Database();
		database.addItem(new CD("CDTitle", "CDArtist", 10, 60));
		database.addItem(new Video("VTitle", "VDirector", 120));

		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ListScene.fxml"));
		loader.setController(new ListController(database));
		final Parent root = loader.load();

		final Scene scene = new Scene(root, 400, 300);
		stage.setTitle("List database");
		stage.setScene(scene);
		stage.show();
	}

	@AfterEach
	public void teardown() throws TimeoutException {
		FxToolkit.cleanupStages();
	}
	
	@Test
	void listShowsItems(FxRobot robot) {
		ListView<Object> lv = robot.lookup("#listItems").queryListView();
		assertSame(database.itemsProperty(), lv.getItems(),
			"The ListView should use the items of the Database");
	}
	
	@Test
	void removeFirstConfirmed(FxRobot robot) throws TimeoutException {
		// select first item
		robot.clickOn("#listItems").type(KeyCode.HOME);

		// Click on Remove, wait for the dialog to come up then click on Confirm
		robot.clickOn(hasText("Remove"));
		WaitForAsyncUtils.waitFor(5L, TimeUnit.SECONDS,
			() -> !robot.lookup(hasText("Confirm")).tryQuery().isEmpty());
		
		robot.clickOn(hasText("Confirm"));

		assertEquals(1, database.itemsProperty().size(),
			"If the Remove dialog was confirmed, one item should have been removed");
		assertTrue(database.itemsProperty().get(0) instanceof Video,
			"If removing the first item in a list with [CD, Video], the CD should have been removed");
	}
	
	@Test
	void removeFirstCancelled(FxRobot robot) throws TimeoutException {
		// select first item
		robot.clickOn("#listItems").type(KeyCode.HOME);

		// Click on Remove, wait for the dialog to come up then click on Confirm
		robot.clickOn(hasText("Remove"));
		WaitForAsyncUtils.waitFor(5L, TimeUnit.SECONDS,
			() -> !robot.lookup(hasText("Cancel")).tryQuery().isEmpty());
		
		robot.clickOn(hasText("Cancel"));

		assertEquals(2, database.itemsProperty().size(),
			"If the Remove dialog was cancelled, no items should have been removed");
	}
	
	@Test
	void clickOnShuffle(FxRobot robot) {
		class ShuffleChangeListener implements ListChangeListener<Item> {
			int timesChanged = 0;
			
			@Override
			public void onChanged(Change<? extends Item> c) {
				timesChanged++;
			}
		}

		ShuffleChangeListener scl = new ShuffleChangeListener();
		database.itemsProperty().addListener(scl);
		robot.clickOn(hasText("Shuffle"));

		assertEquals(1, scl.timesChanged,
			"Clicking on Shuffle should produce just one change notification "
			+ "(FXCollections.shuffle should have been used)");
	}
	
	@Test
	void clickOnQuit(FxRobot robot) {
		robot.clickOn(hasText("Quit"));
		assertFalse(stage.isShowing(),
			"After clicking on Quit, the main window should not be showing anymore");
	}
}
