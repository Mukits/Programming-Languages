package uk.ac.aston.oop.javafx.assessed;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;

import uk.ac.aston.oop.javafx.assessed.model.Item;

public class RemoveControllerTest extends AbstractControllerTest {

	@Test
	public void constructor() {
		try {
			RemoveController.class.getConstructor(Item.class);
		} catch (NoSuchMethodException e) {
			fail("RemoveController should have a constructor that takes an Item");
		}
	}

	@Test
	public void hasInitialize() {
		assertHasFXMLInitialize(RemoveController.class);
	}

	@Test
	public void hasComponentField() throws FileNotFoundException {
		CompilationUnit cu = StaticJavaParser.parse(new File("src/uk/ac/aston/oop/javafx/assessed/RemoveController.java"));

		ClassOrInterfaceDeclaration klass = cu.getClassByName("RemoveController").get();
		FieldDeclaration fListItems = assertHasFXMLComponentField(klass, "lblItem");
		String sFieldType = fListItems.getVariable(0).getTypeAsString();
		assertTrue(
			"Label".equals(sFieldType) || "javafx.scene.control.Label".equals(sFieldType),
			"lblItem in RemoveController should be a Label");
	}

	@Test
	public void hasEventHandlerMethods() {
		assertHasEventHandlerMethod(RemoveController.class, "confirmPressed");
		assertHasEventHandlerMethod(RemoveController.class, "cancelPressed");
	}
}
