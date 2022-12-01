package uk.ac.aston.oop.jcf.generics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;

public class BestOfTest {

	private BestOf<Integer> bo;

	@BeforeEach
	public void setup() {
		bo = new BestOf<>(5); 
	}
	
	@Test
	public void cannotDirectlyModify() {
		assertThrows(UnsupportedOperationException.class, () -> {
			bo.getItems().add(23);
		}, "bo.getItems() should return an unmodifiable view of the items list");
	}

	@Test
	public void addItems() {
		bo.add(1);
		assertEquals(Arrays.asList(1), bo.getItems(),
			"From an empty list, adding 1 should result in [1]");

		bo.add(0);
		assertEquals(Arrays.asList(0, 1), bo.getItems(),
			"From an empty list, adding 1 then 0 should result in [0, 1]");

		bo.add(20);
		bo.add(40);
		bo.add(60);
		bo.add(80);
		assertEquals(Arrays.asList(1, 20, 40, 60, 80), bo.getItems(),
			"From an empty list, adding 1, 0, 20, 40, 60, and 80 to a top 5 "
			+ "should result in [1, 20, 40, 60, 80]");
	}

	@Test
	public void typeParameters() throws FileNotFoundException {
		ClassOrInterfaceDeclaration klass = parseCode();

		String sKlassTypeParameter = assertBestOfClassTypeParameters(klass);
		assertHasGenericList(klass, sKlassTypeParameter);

		// Check the return type for getItems()
		MethodDeclaration mGetItems = klass.getMethodsByName("getItems").get(0);
		assertEquals("List<" + sKlassTypeParameter + ">", mGetItems.getTypeAsString(),
			"getItems() should return a generic List");

		// Check that add takes an argument with the same type as the type parameter
		MethodDeclaration mAdd = klass.getMethodsByName("add").get(0);
		assertEquals(sKlassTypeParameter, mAdd.getParameter(0).getTypeAsString(),
			"The only parameter of add() should be of type " + sKlassTypeParameter);
	}

	protected void assertHasGenericList(ClassOrInterfaceDeclaration klass, String sKlassTypeParameter) {
		boolean bHasGenericList = false;
		for (FieldDeclaration f : klass.getFields()) {
			for (VariableDeclarator v : f.getVariables()) {
				if (v.getTypeAsString().equals("List<" + sKlassTypeParameter + ">")) {
					bHasGenericList = true;
				}
			}
		}
		assertTrue(bHasGenericList,
			"Expected to see a List<" + sKlassTypeParameter + "> field in the class");
	}

	protected ClassOrInterfaceDeclaration parseCode() throws FileNotFoundException {
		CompilationUnit cu = StaticJavaParser.parse(new File(
			"src/uk/ac/aston/oop/jcf/generics/BestOf.java"
		));
		ClassOrInterfaceDeclaration klass = cu.getClassByName("BestOf").get();
		return klass;
	}

	protected String assertBestOfClassTypeParameters(ClassOrInterfaceDeclaration klass) {
		assertEquals(1, klass.getTypeParameters().size(),
			"BestOf should have one type parameter");
		final TypeParameter klassTypeParameter = klass.getTypeParameter(0);
		assertEquals(1, klassTypeParameter.getTypeBound().size(),
			"The BestOf type parameter should use extends");

		ClassOrInterfaceType bestOfTypeBound = klassTypeParameter.getTypeBound().get(0);
		assertEquals("Comparable",
			bestOfTypeBound.getNameAsString(),
			"The BestOf type parameter should use extends on a Comparable");
		assertTrue(bestOfTypeBound.getTypeArguments().isPresent(),
			"The BestOf type parameter should use extends on a Comparable<something>");
		assertEquals(1, bestOfTypeBound.getTypeArguments().get().size(),
			"The BestOf type parameter should use extends on a Comparable<something>");

		Type bestOfComparableTypeBound = bestOfTypeBound.getTypeArguments().get().get(0);
		String sKlassTypeParameter = klassTypeParameter.getNameAsString();
		assertEquals("? super " + sKlassTypeParameter,
			bestOfComparableTypeBound.toString(),
			"The BestOf type parameter should use extends on a Comparable "
			+ "that can compare itself against a "
			+ sKlassTypeParameter
			+ " or any superclass");
		return sKlassTypeParameter;
	}
}
