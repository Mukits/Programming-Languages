package uk.ac.aston.oop.javafx.assessed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.dom4j.Element;
import org.junit.jupiter.api.Test;

public class CreateCDFXMLTest extends AbstractFXMLTest {

	public CreateCDFXMLTest() {
		super(new File("resources/uk/ac/aston/oop/javafx/assessed/CreateCD.fxml"));
	}

	@Test
	public void hasExpectedComponents() {
		assertComponentCount("Label", 4);
		assertComponentCount("CheckBox", 1);
		assertComponentCount("TextField", 2);
		assertComponentCount("Slider", 2);
		assertComponentCount("Button", 2);
	}

	@Test
	public void sliderPlaying() {
		Element eSlider = (Element) doc.selectSingleNode("//*[local-name()='Slider' and @max=360]");
		assertNotNull(eSlider, "There should be a Slider with a max value of 360 (the 'Playing time' slider)");

		assertEquals("60.0", eSlider.attributeValue("majorTickUnit"),
			"The 'Playing time' Slider should have 60 for its major tick unit");
		assertEquals("true", eSlider.attributeValue("showTickLabels"),
				"The 'Playing time' Slider should show tick labels");
		assertEquals("true", eSlider.attributeValue("showTickMarks"),
				"The 'Playing time' Slider should show tick marks");
	}

	@Test
	public void sliderTracks() {
		Element eSlider = (Element) doc.selectSingleNode("//*[local-name()='Slider' and @max=40]");
		assertNotNull(eSlider, "There should be a Slider with a max value of 40 (the 'Number of tracks' slider)");

		assertEquals("5.0", eSlider.attributeValue("majorTickUnit"),
			"The 'Number of tracks' Slider should have 5 for its major tick unit");
		assertEquals("true", eSlider.attributeValue("showTickLabels"),
				"The 'Number of tracks' Slider should show tick labels");
		assertEquals("true", eSlider.attributeValue("showTickMarks"),
				"The 'Number of tracks' Slider should show tick marks");
	}
	
	
    // <Slider fx:id="sliderTracks" majorTickUnit="5.0" max="40.0" showTickLabels="true" showTickMarks="true" snapToTicks="true" />

	
	protected void assertComponentCount(final String componentType, int expectedCount) {
		int nComponents = ((Number) doc.selectObject("count(//*[local-name()='" + componentType + "'])")).intValue();
		assertEquals(expectedCount, nComponents, String.format(
			"There should be %d %s%s in the 'Create CD' dialog",
			expectedCount, componentType, expectedCount > 0 ? "s" : ""
		));
	}

}
