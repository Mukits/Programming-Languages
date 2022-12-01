package uk.ac.aston.oop.uml.diagrams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import net.sourceforge.plantuml.FileFormat;

/**
 * Base class for any PlantUML tests, which parse the file
 * and convert it to XMI format for easier checking.
 */
public abstract class PlantUMLTest {

	private static final Pattern REGEX_PARAMETERS = Pattern.compile("\\w+\\(([^)]*)\\)");

	enum Visibility {
		PRIVATE, PROTECTED, PUBLIC;
	}

	enum Aggregation {
		NONE, AGGREGATE, COMPOSITE;
	}
	
	private final String basename;
	protected Document doc;

	public PlantUMLTest(String basename) {
		this.basename = basename;
	}

	@BeforeEach
	public void parseFile(@TempDir Path tempDirectory) throws Exception {
		File sourceFile = new File(new File("plantuml"), basename + ".txt");
		if (!sourceFile.canRead()) {
			throw new FileNotFoundException(sourceFile.getPath() + " is missing");
		}

		DiagramGenerator gen = new DiagramGenerator(tempDirectory.toFile());
		gen.processFile(sourceFile, FileFormat.XMI_STAR);

		File xmiFile = tempDirectory.resolve(basename + ".xmi").toFile();
		SAXReader reader = new SAXReader();
		doc = reader.read(xmiFile);
	}

	protected void assertTypeHas(String className, ElementMatcher... matchers) {
		final Node n = doc.selectSingleNode(String.format("//UML:Class[@name='%s']/UML:Classifier.feature", className));

		if (n instanceof Element) {
			Element e = (Element) n;

			matchersLoop: for (ElementMatcher matcher : matchers) {
				for (Element child : e.elements()) {
					if (matcher.matches(child)) {
						continue matchersLoop;
					}
				}

				fail(className + " is incomplete: " + matcher.getExpectation());
			}
		} else {
			fail("Could not find " + className);
		}
	}

	protected void assertTypeIsInterface(String className) {
		Node n = getClassNode(className);
		assertNotNull(n, "Type " + className + " should exist");

		Element e = (Element) n;
		boolean isInterface = "true".equals(e.attributeValue("isInterface"));
		assertTrue(isInterface, "Type " + className + " should be an interface");
	}
	
	protected void assertClassIsAbstract(String className) {
		Node n = getClassNode(className);
		assertNotNull(n, "Class " + className + " should exist");

		Element e = (Element) n;
		boolean isAbstract = "true".equals(e.attributeValue("isAbstract"));
		assertTrue(isAbstract, "Class " + className + " should be abstract");
	}

	protected void assertClassExtends(String subclass, String superclass) {
		final String idSubclass = (String) getClassXMIId(subclass);
		final String idSuperclass = (String) getClassXMIId(superclass);
		Node n = doc.selectSingleNode(String.format(
			"//UML:Generalization[@child='%s' and @parent='%s']",
			idSubclass, idSuperclass));

		assertNotNull(n, "Class " + subclass + " should extend class " + superclass);
	}

	protected void assertAssociationHas(String name, UMLAssociationEnd endA, UMLAssociationEnd endB) {
		final String xmiIdA = getClassXMIId(endA.getTypeName()).toString();
		final String xmiIdB = getClassXMIId(endB.getTypeName()).toString();
		
		Element e = (Element) doc.selectSingleNode(String.format("//UML:Association[@name='%s']", name));
		assertNotNull(e, "Could not find relationship " + name);

		checkAssociationEnd(name, e, endA, xmiIdA);
		checkAssociationEnd(name, e, endB, xmiIdB);
	}

	protected void checkAssociationEnd(String name, Element e, UMLAssociationEnd endA, final String xmiIdA) {
		Element eA = (Element) e.selectSingleNode(String.format(".//UML:AssociationEnd[@type='%s']", xmiIdA));
		assertNotNull(eA, "Could not find relationship end mentioning " + endA.getTypeName() + " in relationship " + name);

		final String endDescription = "In relationship " + name	+ ", the end for " + endA.getTypeName();
		
		String aAggregation = eA.attributeValue("aggregation");
		switch (endA.getAggregation()) {
		case NONE:
			assertNull(aAggregation, endDescription + " should not be a composition nor an aggregation");
			break;
		case AGGREGATE:
			assertEquals("aggregate", aAggregation, endDescription + " should be an aggregation");
			break;
		case COMPOSITE:
			assertEquals("composite", aAggregation, endDescription + " should be a composition");
			break;
		}

		String aMultiplicity = eA.attributeValue("name");
		if (endA.getMultiplicity() == null) {
			assertNull(aMultiplicity, endDescription + " should not mention a multiplicity");
		} else {
			assertEquals(endA.getMultiplicity(), aMultiplicity, endDescription + " should have the expected multiplicity");
		}
	}

	private Object getClassXMIId(String subclass) {
		Element e = (Element) getClassNode(subclass);
		assertNotNull("Could not find " + subclass);

		Object xmiId = e.attributeValue("xmi.id");
		assertNotNull("Could not find XMI ID for " + subclass);

		return xmiId;
	}

	protected Node getClassNode(String subclass) {
		return doc.selectSingleNode(String.format("//UML:Class[@name='%s']", subclass));
	}
	
	protected UMLParameter parameter(String name, String type) {
		return new UMLParameter(name, type);
	}

	protected UMLAssociationEnd associationEnd(Aggregation aggregation, String multiplicity, String typeName) {
		return new UMLAssociationEnd(aggregation, multiplicity, typeName);
	}

	protected UMLAssociationEnd associationEnd(String typeName) {
		return associationEnd(Aggregation.NONE, null, typeName);
	}

	protected UMLAssociationEnd associationEnd(Aggregation aggregation, String typeName) {
		return associationEnd(aggregation, null, typeName);
	}

	protected UMLAssociationEnd associationEnd(String multiplicity, String typeName) {
		return associationEnd(Aggregation.NONE, multiplicity, typeName);
	}

	protected ElementMatcher operation(Visibility viz, String name, String returnType, UMLParameter... parameters) {
		String expectation;
		if (returnType == null) {
			expectation = String.format("Should have a %s method '%s'", viz, name);
		} else {
			expectation = String.format("Should have a %s method '%s' with return type '%s'", viz, name, returnType);
		}

		return new ElementMatcher(expectation) {
			@Override
			boolean matches(Element e) {
				String aName = attr(e, "name");

				if ("Operation".equals(e.getName()) && containsWord(name, aName)) {
					if (returnType != null) {
						assertTrue(containsTypeAtEnd(returnType, aName),
							"The method " + name + " should have return type " + returnType);
					}
					assertEqualVisibility(viz, e,
						"The method " + name + " should be " + viz.toString().toLowerCase());

					Matcher matcherParams = REGEX_PARAMETERS.matcher(aName);
					assertTrue(matcherParams.find(), "The method " + name + " should have a parameter list");

					String sParams = matcherParams.group(1).strip();
					String[] params = sParams.length() > 0 ? sParams.split("\\s*,\\s*") : new String[0];
					assertEquals(parameters.length, params.length,
						"The parameter list for method " + name + " should have the right number of parameters");

					for (int i = 0; i < parameters.length; i++) {
						String[] parts = params[i].split(":\\s+");
						assertEquals(2, parts.length,
							String.format("Parameter %d of method %s should follow the pattern 'name: type'", i, name));

						/*
						 * NOTE: we intentionally ignore the name of the parameter in tests (anything
						 * goes as long as it's specified).
						 */
						assertEquals(parameters[i].getType(), parts[1], String.format(
							"Parameter %d of method %s should have the expected type",
							i, name));
					}

					return true;
				} else {
					return false;
				}
			}
		};
	}

	protected ElementMatcher operation(Visibility viz, String name, UMLParameter... parameters) {
		return operation(viz, name, null, parameters);
	}

	protected ElementMatcher operation(String name, UMLParameter... parameters) {
		return operation(Visibility.PUBLIC, name, null, parameters);
	}
	
	protected ElementMatcher operation(String name, String returnType, UMLParameter... parameters) {
		return operation(Visibility.PUBLIC, name, returnType, parameters);
	}

	protected ElementMatcher attribute(Visibility viz, String name, String type) {
		String expectation = String.format("Should have a %s attribute '%s' of type '%s'", viz, name, type);
		return new ElementMatcher(expectation) {
			@Override
			boolean matches(Element e) {
				String aName = attr(e, "name");
				if ("Attribute".equals(e.getName()) && containsWord(name, aName)) {
					assertTrue(containsTypeAtEnd(type, aName),
						"The field " + name + " should have return type " + type);
					assertEqualVisibility(viz, e,
						"The field " + name + " should be " + viz.toString().toLowerCase());
					return true;
				} else {
					return false;
				}
			}
		};
	}

}