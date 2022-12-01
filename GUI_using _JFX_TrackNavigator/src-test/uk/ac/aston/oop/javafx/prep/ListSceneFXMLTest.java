package uk.ac.aston.oop.javafx.prep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.QName;
import org.junit.jupiter.api.Test;

public class ListSceneFXMLTest extends AbstractFXMLTest {

	public ListSceneFXMLTest() {
		super(new File("resources/uk/ac/aston/oop/javafx/prep/ListScene.fxml"));
	}

	@Test
	public void hasExpectedStructure() {
		Node nVBox = doc.selectSingleNode("//*[local-name()='VBox']");
		assertNotNull(nVBox, "The FXML has a VBox at the root");
		Node nListView = nVBox.selectSingleNode("./*[local-name()='children']/*[local-name()='ListView']");
		assertNotNull(nListView, "The VBox has a ListView inside it");
		Node nHBox = nVBox.selectSingleNode("./*[local-name()='children']/*[local-name()='HBox']");
		assertNotNull(nHBox, "The VBox has an HBox inside it");
		List<Node> nButton = nHBox.selectNodes("./*[local-name()='children']/*[local-name()='Button']");
		assertEquals(3, nButton.size(), "The HBox has 3 Buttons inside it");
	}

	@Test
	public void vboxPadding() {
		Element eInsets = (Element) doc.selectSingleNode("//*[local-name()='VBox']/*[local-name()='padding']/*[local-name()='Insets']");
		assertPaddingEqualTo(eInsets, "VBox", "5.0");
	}

	@Test
	public void vboxSpacing() {
		Element eVBox = (Element) doc.selectSingleNode("//*[local-name()='VBox']");
		assertEquals("5.0", eVBox.attributeValue("spacing"), "Spacing for the VBox should be 5.0");
	}

	@Test
	public void hboxPadding() {
		Element eInsets = (Element) doc.selectSingleNode("//*[local-name()='HBox']/*[local-name()='padding']/*[local-name()='Insets']");
		assertPaddingEqualTo(eInsets, "HBox", "5.0");
	}

	@Test
	public void hboxSpacing() {
		Element eHBox = (Element) doc.selectSingleNode("//*[local-name()='HBox']");
		assertEquals("5.0", eHBox.attributeValue("spacing"), "Spacing for the HBox should be 5.0");
	}

	@Test
	public void hboxUsesComputedSize() {
		Element eHBox = (Element) doc.selectSingleNode("//*[local-name()='HBox']");
		assertNull(eHBox.attribute("prefHeight"), "HBox should use the computed height");
		assertNull(eHBox.attribute("prefWidth"), "HBox should use the computed width");
	}

	@Test
	public void hboxIsCentered() {
		Element eHBox = (Element) doc.selectSingleNode("//*[local-name()='HBox']");
		assertEquals("CENTER", eHBox.attributeValue("alignment"), "The HBox should be horizontally centered");
	}

	@Test
	public void listViewVerticalGrow() {
		Element eLV = (Element) doc.selectSingleNode("//*[local-name()='ListView']");
		assertEquals("ALWAYS", eLV.attributeValue("VBox.vgrow"),
			"The ListView should take up all remaining vertical space");
	}

	@Test
	public void listViewHasFXID() {
		Element eLV = (Element) doc.selectSingleNode("//*[local-name()='ListView']");
		assertEquals("listItems", eLV.attributeValue(QName.get("id", PREFIX_FXML, NS_FXML)),
			"The ListView should have the expected fx:id");
	}

	@Test
	public void buttonActions() {
		Map<String, String> expectedOnActions = new HashMap<>();
		expectedOnActions.put("Shuffle", "#shufflePressed");
		expectedOnActions.put("Remove", "#removePressed");
		expectedOnActions.put("Quit", "#quitPressed");

		Set<String> foundButtonNames = new HashSet<>();

		List<Node> buttons = doc.selectNodes("//*[local-name()='Button']");
		for (Node nButton : buttons) {
			Element eButton = (Element) nButton;

			String buttonText = eButton.attributeValue("text");
			String expectedOnAction = expectedOnActions.get(buttonText);
			assertNotNull(expectedOnAction, String.format(
				"Button '%s' should be among the expected names %s",
				buttonText, expectedOnActions.keySet()));

			if (!foundButtonNames.add(buttonText)) {
				fail(String.format("There is more than one button with the text '%s'", buttonText));
			}
		}

		Set<String> missingButtonNames = new HashSet<>(expectedOnActions.keySet());
		missingButtonNames.removeAll(foundButtonNames);
		assertTrue(missingButtonNames.isEmpty(),
			"Could not find buttons with this text(s): " + missingButtonNames);
	}

}
