package uk.ac.aston.oop.javafx.assessed;

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

import javafx.event.ActionEvent;
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
			Method method;
			try {
				method = klass.getMethod(methodName);
			} catch (NoSuchMethodException e) {
				method = klass.getMethod(methodName, ActionEvent.class);
			}

			FXML annFXML = method.getAnnotation(FXML.class);
			assertNotNull(annFXML, String.format(
				"%s in %s should be annotated with @FXML", methodName, klass.getSimpleName()
			));
		} catch (NoSuchMethodException e) {
			fail(klass.getSimpleName() + " should have a " + methodName + " method taking either no parameters or an ActionEvent");
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
