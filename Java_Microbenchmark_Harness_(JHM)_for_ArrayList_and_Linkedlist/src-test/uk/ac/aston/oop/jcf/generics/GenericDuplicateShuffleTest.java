package uk.ac.aston.oop.jcf.generics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.TypeParameter;

public class GenericDuplicateShuffleTest {

	@Test
	public void shuffleRun() {
		List<String> l = new ArrayList<>();
		l.add("A");
		l.add("B");
		l.add("C");

		Random rnd = mock(Random.class);
		when(rnd.nextInt(3)).thenReturn(2).thenReturn(1).thenReturn(2);
		assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
			GenericDuplicateShuffle.shuffle(rnd, l);
		});
		verify(rnd, times(3).description(
			"nextInt(3) should have been called once for "
			+ "each element of the list"
		)).nextInt(3);
		
		assertEquals(Arrays.asList("C", "B", "A"), l,
			"Starting from (A, B, C), shuffle should honor the destination "
			+ "indexes returned from our fake Random (2, 1, 2)");
	}

	@Test
	public void shuffleTypeParameters() throws FileNotFoundException {
		ClassOrInterfaceDeclaration klass = parseSource();
		List<MethodDeclaration> lmShuffle = klass.getMethodsByName("shuffle");
		assertEquals(1, lmShuffle.size(), "There should be a 'shuffle' method");

		MethodDeclaration mShuffle = lmShuffle.get(0);
		NodeList<TypeParameter> typeParams = mShuffle.getTypeParameters();
		assertEquals(1, typeParams.size(), "'shuffle' should have one type parameter");
		String sTypeParam = typeParams.get(0).getNameAsString();

		String sParameterType = mShuffle.getParameter(1).getTypeAsString();
		if (!("java.util.List<" + sTypeParam + ">").equals(sParameterType)) {
			assertEquals("List<" + sTypeParam + ">", sParameterType,
				"The second parameter of shuffle should be of the expected type");
		}
	}
	
	@Test
	public void duplicateRun() {
		List<Integer> l = new LinkedList<>();
		l.add(1);
		l.add(23);
		assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
			GenericDuplicateShuffle.duplicate(42, 3, l);
		});

		assertEquals(Arrays.asList(1, 23, 42, 42, 42), l,
			"Starting from (1, 23), duplicate(42, 3, l) should add three "
			+ "copies of 42 at the end of the List l");
	}

	@Test
	public void duplicateTypeParameters() throws FileNotFoundException {
		ClassOrInterfaceDeclaration klass = parseSource();
		List<MethodDeclaration> lmShuffle = klass.getMethodsByName("duplicate");
		assertEquals(1, lmShuffle.size(), "There should be a 'duplicate' method");

		MethodDeclaration mShuffle = lmShuffle.get(0);
		NodeList<TypeParameter> typeParams = mShuffle.getTypeParameters();
		assertEquals(1, typeParams.size(), "'duplicate' should have one type parameter");
		String sTypeParam = typeParams.get(0).getNameAsString();

		assertEquals(sTypeParam, mShuffle.getParameter(0).getTypeAsString(),
			"The first parameter of duplicate should have the right type");

		String sParameterType = mShuffle.getParameter(2).getTypeAsString();
		if (!("java.util.List<? super " + sTypeParam + ">").equals(sParameterType)) {
			assertEquals("List<? super " + sTypeParam + ">", sParameterType,
				"The third parameter of duplicate should be of the expected type");
		}
	}


	protected ClassOrInterfaceDeclaration parseSource() throws FileNotFoundException {
		CompilationUnit cu = StaticJavaParser.parse(new File(
			"src/uk/ac/aston/oop/jcf/generics/GenericDuplicateShuffle.java"));
		ClassOrInterfaceDeclaration klass = cu.getClassByName("GenericDuplicateShuffle").get();
		return klass;
	}
	
}
