package uk.ac.aston.oop.javafx.assessed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.QName;
import org.junit.jupiter.api.Test;

public class RemoveItemFXMLTest extends AbstractFXMLTest {

	public RemoveItemFXMLTest() {
		super(new File("resources/uk/ac/aston/oop/javafx/assessed/RemoveItem.fxml"));
	}

	@Test
	public void hasExpectedStructure() {
		Element eVBox = (Element) doc.selectSingleNode("//*[local-name()='VBox']");
		assertNotNull(eVBox, "The FXML has a VBox at the root");
		Element eVBoxChildren = eVBox.elements("children").get(0);
		assertEquals(3, eVBoxChildren.elements().size(),
			"The VBox should have 3 children: AnchorPane, Label, and HBox");

		Element eAnchorPane = eVBoxChildren.elements().get(0);
		assertEquals("AnchorPane", eAnchorPane.getName(),
			"The first child of the VBox should be an AnchorPane");
		assertEquals("Label", eVBoxChildren.elements().get(1).getName(),
			"The second child of the VBox should be a Label");
		Element eHBox = eVBoxChildren.elements().get(2);
		assertEquals("HBox", eHBox.getName(),
			"The third child of the VBox should be an HBox");

		Node nAnchorLabel = eAnchorPane.selectSingleNode("./*[local-name()='children']/*[local-name()='Label']");
		assertNotNull(nAnchorLabel, "The AnchorPane should have a Label inside it");

		List<Node> hBoxButtons = eHBox.selectNodes("./*[local-name()='children']/*[local-name()='Button']");
		assertEquals(2, hBoxButtons.size(), "The HBox should have two buttons");

		assertEquals("Confirm", ((Element) hBoxButtons.get(0)).attributeValue("text"),
			"The first Button should have the text 'Confirm'");
		assertEquals("Cancel", ((Element) hBoxButtons.get(1)).attributeValue("text"),
			"The second Button should have the text 'Cancel'");
	}

	@Test
	public void labelAnchorPane() {
		Element eLabel = (Element) doc.selectSingleNode("//*[local-name()='Label']");
		for (String attr : Arrays.asList("bottom", "left", "right", "top")) {
			String name = "AnchorPane." + attr + "Anchor";
			String expectedDistance = "15.0";
			assertEquals(expectedDistance, eLabel.attributeValue(name), String.format(
				"The %s edge of the Label in the AnchorPane should be %s units away from the %s AnchorPane",
				attr, expectedDistance, attr
			));
		}
	}

	@Test
	public void anchorLabelStyle() {
		Element e = (Element) doc.selectSingleNode("(//*[local-name()='Label'])[1]");
		assertStyleContains(e, "-fx-text-alignment", "center", "Label in AnchorPane");
	}

	@Test
	public void anchorPaneStyle() {
		Element e = (Element) doc.selectSingleNode("//*[local-name()='AnchorPane']");
		assertStyleContains(e, "-fx-background-color", "lightgrey", "AnchorPane");
	}

	@Test
	public void middleLabel() {
		Element e = (Element) doc.selectSingleNode("(//*[local-name()='Label'])[2]");
		assertEquals("CENTER", e.attributeValue("alignment"),
			"The Label in the middle should have its alignment set to CENTER");

		assertEquals("ALWAYS", e.attributeValue("VBox.vgrow"),
			"The Label in the middle should take up all remaining vertical space");

		Element eMargin = e.element("VBox.margin");
		assertNotNull(eMargin, "The Label in the middle should have margins defined");
		Element insets = eMargin.element("Insets");
		assertNotNull(insets, "The Label in the middle should have margins defined");
		assertMarginsEqualTo(insets, "The Label in the middle", "5.0");

		assertNull(e.attribute("prefHeight"),
			"The Label in the middle should use its computed height");
		assertNull(e.attribute("prefWidth"),
			"The Label in the middle should use its computed width");

		assertEquals(Double.MAX_VALUE + "", e.attributeValue("maxHeight"),
			"The Label in the middle should have MAX_VALUE as its maximum height");
		assertEquals(Double.MAX_VALUE + "", e.attributeValue("maxWidth"),
			"The Label in the middle should have MAX_VALUE as its maximum width");

		assertEquals("true", e.attributeValue("wrapText"),
			"The Label in the middle should have 'Wrap Text' ticked");
	}

	@Test
	public void hbox() {
		Element e = (Element) doc.selectSingleNode("//*[local-name()='HBox']");

		final String desc = "The HBox";
		assertAttributeEqualTo(e, "alignment", "BOTTOM_CENTER", desc);
		assertAttributeEqualTo(e, "spacing", "5.0", desc);
		assertAttributeEqualTo(e, "VBox.vgrow", "NEVER", desc);

		Element ePadding = e.element("padding");
		assertNotNull(ePadding, "The HBox should have padding");
		Element eInsets = ePadding.element("Insets");
		assertMarginsEqualTo(eInsets, desc, "5.0");
	}

	@Test
	public void middleLabelFXID() {
		Element e = (Element) doc.selectSingleNode("(//*[local-name()='Label'])[2]");
		assertEquals("lblItem", e.attributeValue(QName.get("id", PREFIX_FXML, NS_FXML)),
			"The Label in the middle should have the correct fx:id set");
	}

	@Test
	public void buttonActions() {
		Node nButtonConfirm = doc.selectSingleNode("//*[local-name()='Button'][@onAction='#confirmPressed']");
		assertNotNull(nButtonConfirm, "There is a Button calling the 'confirmPressed' controller method");

		Node nButtonCancel = doc.selectSingleNode("//*[local-name()='Button'][@onAction='#cancelPressed']");
		assertNotNull(nButtonCancel, "There is a Button calling the 'cancelPressed' controller method");
	}
}
