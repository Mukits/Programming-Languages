package uk.ac.aston.oop.uml.diagrams;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.regex.Pattern;

import org.dom4j.Element;

import uk.ac.aston.oop.uml.diagrams.PlantUMLTest.Visibility;

abstract class ElementMatcher {
	private String expectation;

	public ElementMatcher(String exp) {
		this.expectation = exp;
	}

	public String getExpectation() {
		return expectation;
	}

	abstract boolean matches(Element e);

	protected String attr(Element e, String attr) {
		String value = e.attributeValue(attr);
		if (value == null) {
			return "";
		} else {
			// Remove leading/trailing whitespace and normalize inner whitespace
			return value.strip().replaceAll("\\s+", " ");
		}
	}

	protected boolean containsTypeAtEnd(String type, String aName) {
		return Pattern.compile(":\\s*" + type + "$").matcher(aName).find();
	}

	protected boolean containsWord(String name, String aName) {
		return Pattern.compile("\\b" + name + "\\b").matcher(aName).find();
	}

	protected void assertEqualVisibility(Visibility viz, Element e, String msg) {
		assertEquals(viz.toString().toLowerCase(), attr(e, "visibility"), msg);
	}
	
	
}