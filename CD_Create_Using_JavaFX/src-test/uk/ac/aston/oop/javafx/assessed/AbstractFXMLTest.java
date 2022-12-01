package uk.ac.aston.oop.javafx.assessed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public abstract class AbstractFXMLTest {

	protected static final String PREFIX_FXML = "fx";
	protected static final String NS_FXML = "http://javafx.com/fxml/1";

	protected Document doc;
	private File fxmlFile;

	public AbstractFXMLTest(File fxmlFile) {
		this.fxmlFile = fxmlFile;
	}

	@BeforeEach
	public void loadDocument() throws DocumentException {
		SAXReader reader = new SAXReader();
		DocumentFactory df = new DocumentFactory();
		
		Map<String, String> nsMap = new HashMap<>();
		nsMap.put(PREFIX_FXML, NS_FXML);
		df.setXPathNamespaceURIs(nsMap);
		reader.setDocumentFactory(df);

		doc = reader.read(fxmlFile);
	}

	protected void assertAttributeEqualTo(Element e, String attribute, String expectedValue, String description) {
		assertEquals(expectedValue, e.attributeValue(attribute), String.format(
			"%s should have %s equal to %s",
			description, attribute, expectedValue));
	}

	protected void assertMarginsEqualTo(Element eInsets, String name, final String expectedMargin) {
		assertInsetsEqualTo(eInsets, name, "margins", expectedMargin);
	}

	protected void assertPaddingEqualTo(Element eInsets, String name, final String expectedPadding) {
		assertInsetsEqualTo(eInsets, name, "padding", expectedPadding);
	}
	
	protected void assertInsetsEqualTo(Element eInsets, String name, String insetType, final String expectedValue) {
		assertNotNull(eInsets, "The " + name + " has " + insetType);
		for (String attr : Arrays.asList("bottom", "left", "right", "top")) {
			assertEquals(expectedValue, eInsets.attributeValue(attr),
				String.format("The %s %s for the %s should be %s", attr, insetType, name, expectedValue));
		}
	}

	protected void assertStyleContains(Element e, String property, String value, String description) {
		String sStyle = e.attributeValue("style");
		assertNotNull(sStyle, String.format("%s should set its CSS style", description));

		assertTrue(sStyle.contains(property), String.format(
			"%s should set the CSS %s property in its style attribute",
			description, property
		));
		assertTrue(sStyle.contains(value), String.format(
			"%s should set the CSS %s property to %s",
			description, property, value
		));
	}

}
