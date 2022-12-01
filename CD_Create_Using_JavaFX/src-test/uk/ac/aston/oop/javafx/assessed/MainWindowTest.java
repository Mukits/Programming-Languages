package uk.ac.aston.oop.javafx.assessed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import uk.ac.aston.oop.javafx.assessed.model.CD;
import uk.ac.aston.oop.javafx.assessed.model.Database;
import uk.ac.aston.oop.javafx.assessed.model.Item;
import uk.ac.aston.oop.javafx.assessed.model.Video;

@ExtendWith(ApplicationExtension.class)
public class MainWindowTest {

	protected static class FindWindowLabelPredicate implements Predicate<Node> {
		private final Window w;
		private final String substring;
		private final FxRobot robot;

		protected FindWindowLabelPredicate(Window w, String substring, FxRobot robot) {
			this.w = w;
			this.substring = substring;
			this.robot = robot;
		}

		@Override
		public boolean test(Node m) {
			return m instanceof Labeled
				&& robot.window(m) == w
				&& (((Labeled)m).getText() + "").toLowerCase().contains(substring.toLowerCase());
		}

		@Override
		public String toString() {
			return String.format(
				"Predicate[find element in window '%s' with text '%s' ignoring case]",
				((Stage)w).getTitle(), substring);
		}
	}

	private static class VerticalOrderComparator implements Comparator<Node> {
		@Override
		public int compare(Node a, Node b) {
			Bounds aBounds = a.localToScene(a.getBoundsInLocal());
			Bounds bBounds = b.localToScene(b.getBoundsInLocal());
			return Double.compare(aBounds.getMinY(), bBounds.getMinY());
		}
	}

	private static class PositionChainItem {
		public final Node node;
		public final String description;

		public PositionChainItem(Node node, String description) {
			this.node = node;
			this.description = description;
		}

		public Bounds getBounds() {
			return node.localToScene(node.getBoundsInLocal());
		}
	}

	private static PositionChainItem pc(Node n, String desc) {
		return new PositionChainItem(n, desc);
	}
	
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
	void addCancel(FxRobot robot) throws TimeoutException {
		openAddDialog(robot);
		robot.clickOn(hasText("Cancel"));
		assertEquals(1, robot.listWindows().size(),
			"After clicking on Cancel, the Create CD window should be hidden");

		final int oldSize = database.itemsProperty().size();
		assertEquals(oldSize, database.itemsProperty().size(),
			"Cancelling the dialog should result in no items being added");
	}

	@Test
	void addButtonShowsCreateCDDialog(FxRobot robot) throws TimeoutException {
		openAddDialog(robot);
		Optional<Stage> stCreateCd = getStageByTitle(robot, "Create CD");
		assertTrue(stCreateCd.isPresent(),
			"Clicking on Add should open a window with 'Create CD' for its title");
	}

	private Optional<Stage> getStageByTitle(FxRobot robot, String title) {
		for (Window w : robot.listWindows()) {
			Stage st = (Stage) w;
			if (st.getTitle().toLowerCase().contains(title.toLowerCase())) {
				return Optional.of(st);
			}
		}
		return Optional.empty();
	}

	@Test
	void createCDHasGoodLayout(FxRobot robot) throws TimeoutException {
		openAddDialog(robot);
		Stage stCreateCD = getStageByTitle(robot, "Create CD").get();
		
		Labeled lblTitle = robot.lookup(hasText("Title:")).queryLabeled();
		assertNotNull(lblTitle, "There should be a 'Title:' Label");

		Labeled lblArtist = robot.lookup(hasText("Artist:")).queryLabeled();
		assertNotNull(lblArtist, "There should be an 'Artist:' Label");

		Labeled chkOwn = robot.lookup(hasText("Own")).queryLabeled();
		assertNotNull(chkOwn, "There should be an 'Own' CheckBox");

		Labeled lblPlayingTime = getWindowLabel(robot, stCreateCD, "Playing time");
		Labeled lblTracks = getWindowLabel(robot, stCreateCD, "Number of tracks");

		List<TextField> textFields = getElementsByTypeVertically(robot, TextField.class);
		assertEquals(2, textFields.size(), "The Create CD dialog should have two TextFields");

		List<Slider> sliders = getElementsByTypeVertically(robot, Slider.class);
		assertEquals(2, sliders.size(), "The Create CD dialog should have two Sliders");

		List<CheckBox> cboxes = getElementsByTypeVertically(robot, CheckBox.class);
		assertEquals(1, cboxes.size(), "The Create CD dialog should have one CheckBox");

		List<Button> buttons = new ArrayList<>(robot.lookup(m ->
			m instanceof Button
			&& robot.window(m) == stCreateCD).queryAll())	;
		assertEquals(2, buttons.size(), "The Create CD dialog should have two Button");

		// OK, we located everything needed - let's check their relative positions now
		assertHorizontallyAfter(pc(lblTitle, "Title Label"), pc(textFields.get(0), "Title TextField"));
		assertHorizontallyAfter(pc(lblArtist, "Artist Label"), pc(textFields.get(1), "Artist TextField"));
		assertVerticallyAfter(
			pc(lblTitle, "Title Label"),
			pc(lblArtist, "Artist Label"),
			pc(chkOwn, "Own CheckBox"),
			pc(lblPlayingTime, "Playing time Label"),
			pc(sliders.get(0), "Playing time Slider"),
			pc(lblTracks, "Number of tracks Label"),
			pc(sliders.get(1), "Number of tracks Slider"),
			pc(buttons.get(0), "Create/Cancel Buttons")
		);

		assertSameVerticalPosition(buttons.get(0), buttons.get(1),
			"The two Buttons in Create CD should be vertically aligned");
	}

	protected Labeled getWindowLabel(FxRobot robot, Window w, String substring) {
		Labeled lblTracks = robot.lookup(new FindWindowLabelPredicate(w, substring, robot)).queryLabeled();
		assertNotNull(lblTracks, "There should be a '" + substring + "' label");
		return lblTracks;
	}
	
	private void assertSameVerticalPosition(Node a, Node b, String message) {
		Bounds aBounds = a.localToScene(a.getBoundsInLocal());
		Bounds bBounds = b.localToScene(b.getBoundsInLocal());
		assertEquals(aBounds.getMinY(), bBounds.getMinY(), message);
	}

	private void assertHorizontallyAfter(PositionChainItem... items) {
		for (int i = 1; i < items.length; i++) {
			PositionChainItem leftNode = items[i - 1];
			PositionChainItem rightNode = items[i];
			Bounds leftBounds = leftNode.getBounds();
			Bounds rightBounds = rightNode.getBounds();

			assertTrue(rightBounds.getMinX() > leftBounds.getMinX(),
				String.format("%s (minX = %f) should be to the right of %s (minX = %f)",
					rightNode.description,
					rightBounds.getMinX(),
					leftNode.description,
					leftBounds.getMinX()));
		}
	}
	
	private void assertVerticallyAfter(PositionChainItem... items) {
		for (int i = 1; i < items.length; i++) {
			PositionChainItem topNode = items[i - 1];
			PositionChainItem bottomNode = items[i];
			Bounds topBounds = topNode.getBounds();
			Bounds bottomBounds = bottomNode.getBounds();

			try {
				WaitForAsyncUtils.waitFor(3, TimeUnit.SECONDS, () -> {
					return bottomBounds.getMinY() > topBounds.getMinY();
				});
			} catch (TimeoutException ex) {
				fail(String.format("%s (minY = %f) should be below %s (minY = %f)",
						bottomNode.description,
						bottomBounds.getMinY(),
						topNode.description,
						topBounds.getMinY()));
			}
		}
	}

	@Test
	void createCDResizesHorizontallyCorrectly(FxRobot robot) throws TimeoutException {
		openAddDialog(robot);
		Stage stCreateCD = getStageByTitle(robot, "Create CD").get();
		
		// fetch old widths and positions
		
		List<TextField> textFields = getElementsByTypeVertically(robot, TextField.class);
		assertEquals(2, textFields.size(), "The Create CD dialog should have two TextFields");

		List<Slider> sliders = getElementsByTypeVertically(robot, Slider.class);
		assertEquals(2, sliders.size(), "The Create CD dialog should have two Sliders");

		double oldWindowWidth = stCreateCD.getWidth();
		List<Double> oldTextFieldWidths = textFields.stream()
			.map(f -> {return f.getWidth();})
			.collect(Collectors.toList());
		List<Double> oldSliderWidths = sliders.stream()
			.map(f -> {return f.getWidth();})
			.collect(Collectors.toList());

		List<Button> buttons = new ArrayList<>(robot.lookup(m ->
			m instanceof Button && robot.window(m) == stCreateCD
		).queryAll());
		List<Double> oldButtonMinX = buttons.stream()
			.map(b -> getMinX(b))
			.collect(Collectors.toList());

		// do resize
		
		robot.interact(() -> {
			stCreateCD.setWidth(oldWindowWidth * 2);
		});

		// text fields and slider should become wider
		
		for (int i = 0; i < textFields.size(); i++) {
			assertTrue(textFields.get(i).getWidth() > oldTextFieldWidths.get(i),
				"TextField #" + i + " from the top should get wider if the window becomes wider");
		}
		for (int i = 0; i < sliders.size(); i++) {
			assertTrue(sliders.get(i).getWidth() > oldSliderWidths.get(i),
				"Slider #" + i + " from the top should get wider if the window becomes wider");
		}

		// buttons should move to the right

		for (int i = 0; i < buttons.size(); i++) {
			Button b = buttons.get(i);
			assertTrue(getMinX(b) > oldButtonMinX.get(i),
				"Button '" + b.getText() + "' should have moved to the right as the window becomes wider");
		}

	}

	@Test
	void createCDResizesVerticallyCorrectly(FxRobot robot) throws TimeoutException {
		openAddDialog(robot);
		Stage stCreateCD = getStageByTitle(robot, "Create CD").get();
		
		// fetch old positions

		List<TextField> textFields = getElementsByTypeVertically(robot, TextField.class);
		assertEquals(2, textFields.size(), "The Create CD dialog should have two TextFields");

		List<Slider> sliders = getElementsByTypeVertically(robot, Slider.class);
		assertEquals(2, sliders.size(), "The Create CD dialog should have two Sliders");

		double oldWindowHeight = stCreateCD.getHeight();
		List<Double> oldTextFieldY = textFields.stream()
			.map(f -> getMinY(f))
			.collect(Collectors.toList());
		List<Double> oldSliderY = sliders.stream()
			.map(f -> getMinY(f))
			.collect(Collectors.toList());

		List<Button> buttons = new ArrayList<>(robot.lookup(m ->
			m instanceof Button && robot.window(m) == stCreateCD
		).queryAll());
		List<Double> oldButtonY = buttons.stream()
			.map(b -> getMinY(b))
			.collect(Collectors.toList());

		// do resize
		
		robot.interact(() -> {
			stCreateCD.setHeight(oldWindowHeight * 2);
		});

		// text fields and slider should have not moved

		for (int i = 0; i < textFields.size(); i++) {
			assertEquals(oldTextFieldY.get(i), getMinY(textFields.get(i)),
				"TextField #" + i + " from the top should not move even if the window becomes taller");
		}
		for (int i = 0; i < sliders.size(); i++) {
			assertEquals(oldSliderY.get(i), getMinY(sliders.get(i)),
				"Slider #" + i + " from the top should not move even if the window becomes taller ");
		}

		// buttons should move down

		for (int i = 0; i < buttons.size(); i++) {
			Button b = buttons.get(i);
			assertTrue(getMinY(b) > oldButtonY.get(i),
				"Button '" + b.getText() + "' should have moved down as the window becomes taller");
		}

	}

	private double getMinX(Node n) {
		return n.localToScene(n.getBoundsInLocal()).getMinX();
	}

	private double getMinY(Node n) {
		return n.localToScene(n.getBoundsInLocal()).getMinY();
	}

	@Test
	void createCDSlidersUpdate(FxRobot robot) throws TimeoutException, InterruptedException {
		openAddDialog(robot);
		Stage stCreateCD = getStageByTitle(robot, "Create CD").get();

		List<Slider> sliders = getElementsByTypeVertically(robot, Slider.class);
		Slider sliderPlayingTime = null;
		Slider sliderTracks = null;
		for (Slider s : sliders) {
			if (s.getMax() == 360) {
				sliderPlayingTime = s;
			} else if (s.getMax() == 40) {
				sliderTracks = s;
			}
		}
		assertNotNull(sliderPlayingTime,
			"Create CD should have a Slider with max = 360 (the playing time one)");
		assertNotNull(sliderTracks,
			"Create CD should have a Slider with max = 40 (the tracks one)");

		final int expectedPlayingTime = 49;
		final int expectedTracks = 17;
		Labeled lblPlayingTime = getWindowLabel(robot, stCreateCD, "Playing time");
		assertNotNull(lblPlayingTime, "Create CD should have a 'Playing time' label");
		Labeled lblTracks = getWindowLabel(robot, stCreateCD, "Number of tracks");
		assertNotNull(lblTracks, "Create CD should have a 'Number of tracks' label");

		class LabelListenerValues {
			boolean bPlayingTimeUpdated = false;
			boolean bTracksUpdated = false;
		}
		LabelListenerValues values = new LabelListenerValues();
		lblPlayingTime.textProperty().addListener((prop, old, newV) -> {
			values.bPlayingTimeUpdated = newV.contains(expectedPlayingTime + "");
		});
		lblTracks.textProperty().addListener((prop, old, newV) -> {
			values.bTracksUpdated = newV.contains(expectedTracks + "");
		});
		
		final Slider fSliderPlayingTime = sliderPlayingTime;
		final Slider fSliderTracks = sliderTracks;
		robot.interact(() -> {
			fSliderPlayingTime.setValue(expectedPlayingTime);
			fSliderTracks.setValue(expectedTracks);
		});

		assertTrue(values.bPlayingTimeUpdated, String.format(
			"After setting the 'Playing time' slider to %d, the 'Playing time' label should have been updated to reflect it",
			expectedPlayingTime
		));
		assertTrue(values.bTracksUpdated, String.format(
			"After setting the 'Number of tracks' slider to %d, the 'Number of tracks' should have been updated to reflect it",
			expectedTracks
		));
	}

	@Test
	void createCDIsModal(FxRobot robot) throws TimeoutException {
		openAddDialog(robot);
		Stage stCreateCD = getStageByTitle(robot, "Create CD").get();
		assertEquals(Modality.APPLICATION_MODAL, stCreateCD.getModality(),
			"The 'Create CD' dialog should be application modal");
	}
	
	@Test
	void addConfirmNotOwn(FxRobot robot) throws TimeoutException {
		openAddDialog(robot);
		
		String expectedTitle = "MyTitle";
		String expectedArtist = "YourArtist";
		int expectedPlayingTime = 180;
		int expectedTracks = 15;
		boolean expectedOwn = false;

		assertCreateFormCreatesCD(robot,
			expectedTitle, expectedArtist,
			expectedPlayingTime, expectedTracks, expectedOwn);
	}

	@Test
	void addConfirmOwn(FxRobot robot) throws TimeoutException {
		openAddDialog(robot);

		String expectedTitle = "MyOtherTitle";
		String expectedArtist = "YourAlternateArtist";
		int expectedPlayingTime = 140;
		int expectedTracks = 25;
		boolean expectedOwn = true;

		assertCreateFormCreatesCD(robot,
			expectedTitle, expectedArtist,
			expectedPlayingTime, expectedTracks, expectedOwn);
	}

	protected void openAddDialog(FxRobot robot) throws TimeoutException {
		robot.clickOn(hasText("Add"));
		waitForButton(robot, "Cancel");
	}
	
	protected void assertCreateFormCreatesCD(FxRobot robot, String expectedTitle, String expectedArtist,
			int expectedPlayingTime, int expectedTracks, boolean expectedOwn) {
		List<TextField> textFields = getElementsByTypeVertically(robot, TextField.class);
		assertEquals(2, textFields.size(), "The Create CD dialog should have two TextFields");
		List<Slider> sliders = getElementsByTypeVertically(robot, Slider.class);
		assertEquals(2, sliders.size(), "The Create CD dialog should have two Sliders");
		List<CheckBox> cboxes = getElementsByTypeVertically(robot, CheckBox.class);
		assertEquals(1, cboxes.size(), "The Create CD dialog should have one CheckBox");

		robot.interact(() -> {
			textFields.get(0).setText(expectedTitle);
			textFields.get(1).setText(expectedArtist);
		
			sliders.get(0).setValue(expectedPlayingTime);
			sliders.get(1).setValue(expectedTracks);

			if (cboxes.get(0).isSelected() != expectedOwn) {
				robot.clickOn(cboxes.get(0));
			}
		});

		robot.clickOn(hasText("Create"));
		assertEquals(1, robot.listWindows().size(),
				"After clicking on Create, the Create CD window should be hidden");

		assertEquals(3, database.itemsProperty().size(),
			"After clicking on Create, a new item should have been added");
		assertTrue(database.itemsProperty().get(2) instanceof CD,
			"After clicking on Create, a new CD should have been added at the end of the list");

		final CD newCD = (CD) database.itemsProperty().get(2);
		assertTrue(newCD.toString().contains("title: " + expectedTitle), String.format(
			"After typing '%s' into the top TextField before clicking Create, the title should be '%s'",
			expectedTitle, expectedTitle));

		assertEquals(expectedArtist, newCD.getArtist(), String.format(
			"After typing '%s' into the bottom TextField before clicking Create, the artist should be '%s'",
			expectedArtist, expectedArtist));

		assertEquals(expectedPlayingTime, newCD.getPlayingTime(), String.format(
			"After moving the top slider to %d before clicking Create, the playing time should be %d",
			expectedPlayingTime, expectedPlayingTime
		));
		assertEquals(expectedTracks, newCD.getNumberOfTracks(), String.format(
			"After moving the bottom slider to %d before clicking Create, the number of tracks should be %d",
			expectedTracks, expectedTracks
		));
		assertEquals(expectedOwn, newCD.getOwn(), String.format(
			"After %sticking the 'Own' checkbox before clicking Create, the CD should be created with the 'owned' flag set to %s",
			expectedOwn ? "" : "un",
			expectedOwn
		));
	}

	@SuppressWarnings("unchecked")
	protected <T extends Node> List<T> getElementsByTypeVertically(FxRobot robot, Class<T> klass) {
		Set<Node> fields = robot.lookup(m -> klass.isAssignableFrom(m.getClass())).queryAll();
		List<T> textFields = new ArrayList<>();
		for (Node n : fields) {
			textFields.add((T) n);
		}
		Collections.sort(textFields, new VerticalOrderComparator());
		return textFields;
	}

	@Test
	void removeFirstConfirmed(FxRobot robot) throws TimeoutException {
		// select first item
		robot.clickOn("#listItems").type(KeyCode.HOME);

		// Click on Remove, wait for the dialog to come up then click on Confirm
		robot.clickOn(hasText("Remove"));
		waitForButton(robot, "Confirm");
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
		waitForButton(robot, "Cancel");
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

	protected void waitForButton(FxRobot robot, String buttonText) {
		try {
			WaitForAsyncUtils.waitFor(5L, TimeUnit.SECONDS, () -> {
				return !robot.lookup(hasText(buttonText)).tryQuery().isEmpty();
			});
		} catch (TimeoutException tex) {
			fail(String.format("Timed out while waiting for a button with the text '%s' to appear", buttonText));
		}
	}
	
}
