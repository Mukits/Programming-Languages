package uk.ac.aston.oop.javafx.prep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;

import javafx.fxml.FXML;

public abstract class AbstractControllerTest {

	protected void assertHasFXMLInitialize(Class<?> klass) {
		try {
			Method mInitialize = klass.getMethod("initialize");
			FXML annFXML = mInitialize.getAnnotation(FXML.class);
	
			assertEquals(0, mInitialize.getParameterCount(),
				"initialize should take no parameters");
			assertNotEquals(0, mInitialize.getModifiers() & Modifier.PUBLIC,
				"initialize should be public");
			assertNotNull(annFXML, "initialize should have the @FXML annotation");
		} catch (NoSuchMethodException e) {
			fail(klass.getName() + " should have an initialize method");
		}
	}

	protected void assertHasEventHandlerMethod(Class<?> klass, String methodName) {
		try {
			Method method = klass.getMethod(methodName);
			FXML annFXML = method.getAnnotation(FXML.class);
			assertEquals(0, method.getParameterCount(), String.format(
				"%s in %s should take no parameters", klass.getSimpleName(), methodName
			));
			assertNotNull(annFXML, String.format(
				"%s in %s should be annotated with @FXML", klass.getSimpleName(), methodName
			));
			
		} catch (NoSuchMethodException e) {
			fail(klass.getSimpleName() + " should have a " + methodName + " method");
		}
	}

	protected FieldDeclaration assertHasFXMLComponentField(ClassOrInterfaceDeclaration klass, String fieldName) {
		Optional<FieldDeclaration> ofListItems = klass.getFieldByName(fieldName);
		assertTrue(ofListItems.isPresent(),
				String.format("%s should have a %s field", klass.getNameAsString(), fieldName));
		FieldDeclaration fListItems = ofListItems.get();
		assertTrue(fListItems.getAnnotationByClass(FXML.class).isPresent(),
				String.format("%s in %s should have the @FXML annotation", fieldName, klass.getNameAsString()));
		return fListItems;
	}

}
