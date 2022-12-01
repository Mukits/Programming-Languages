package uk.ac.aston.oop.javafx.prep;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;

import uk.ac.aston.oop.javafx.prep.model.Database;

public class ListControllerTest extends AbstractControllerTest {

	@Test
	public void constructor() {
		try {
			ListController.class.getConstructor(Database.class);
		} catch (NoSuchMethodException e) {
			fail("ListController should have a constructor that takes a Database");
		}
	}

	@Test
	public void hasInitialize() {
		assertHasFXMLInitialize(ListController.class);
	}

	@Test
	public void hasComponentField() throws FileNotFoundException {
		CompilationUnit cu = StaticJavaParser.parse(new File("src/uk/ac/aston/oop/javafx/prep/ListController.java"));

		ClassOrInterfaceDeclaration klass = cu.getClassByName("ListController").get();
		FieldDeclaration fListItems = assertHasFXMLComponentField(klass, "listItems");
		String sListItemsType = fListItems.getVariable(0).getTypeAsString();
		assertTrue(sListItemsType.contains("ListView"), "listItems in ListController should be a ListView");
		assertTrue(sListItemsType.contains("Item>"), "listItems in ListController should be a ListView of Item");
	}

	@Test
	public void hasEventHandlerMethods() {
		assertHasEventHandlerMethod(ListController.class, "shufflePressed");
		assertHasEventHandlerMethod(ListController.class, "removePressed");
		assertHasEventHandlerMethod(ListController.class, "quitPressed");
	}
}
