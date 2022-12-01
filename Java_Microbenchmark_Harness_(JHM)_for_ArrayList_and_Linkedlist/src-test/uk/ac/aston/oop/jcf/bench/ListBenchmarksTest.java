package uk.ac.aston.oop.jcf.bench;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ListBenchmarksTest {

	@Test
	public void addEnd() {
		final List<Integer> l = new ArrayList<>();
		final int n = 10;

		/*
		 * We give the test 10 seconds to run this loop (timeout the test rather than
		 * the whole build).
		 */
		ListBenchmarks lb = new ListBenchmarks();
		assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
			lb.addToEnd(l, n);
		});
		assertEquals(n, l.size(), String.format(
			"addToEnd(l, %d) should produce a list with %d elements", n, n));

		for (int i = 0; i < n; i++) {
			assertEquals(i, l.get(i), String.format(
				"The list from addToEnd(l, %d) should have the right value at index %d", n, i));
		}
	}

	@Test
	public void addBeginning() {
		final List<Integer> l = new ArrayList<>();
		final int n = 15;

		ListBenchmarks lb = new ListBenchmarks();
		assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
			lb.addToBeginning(l, n);
		});
		assertEquals(n, l.size(), String.format(
			"addToBeginning(l, %d) should produce a list with %d elements", n, n));

		for (int i = 0; i < n; i++) {
			assertEquals(n - i - 1, l.get(i), String.format(
				"The list from addToBeginning(l, %d) should have the right value at index %d", n, i));
		}
	}

	@Test
	public void hasExpectedBenchmarks() {
		final Set<String> benchmarkMethodNames = new TreeSet<>();
		for (Method m : ListBenchmarks.class.getMethods()) {
			if (m.getAnnotation(Benchmark.class) != null) {
				assertEquals(0, m.getParameterCount(),
					"@Benchmark methods should not take any parameters");
				assertEquals(void.class, m.getReturnType(),
					"@Benchmark methods should not return anything");
				assertNotEquals(0, m.getModifiers() & Modifier.PUBLIC,
					"@Benchmark methods should be public");
				assertEquals(0, m.getModifiers() & Modifier.STATIC,
					"@Benchmark methods should not be static");
				
				benchmarkMethodNames.add(m.getName());
			}
		}

		final Set<String> expectedBenchmarkMethodNames = new TreeSet<>(Arrays.asList(
			"addToEndArrayList",
			"addToEndLinkedList",
			"addToBeginningArrayList",
			"addToBeginningLinkedList"
		));
		assertTrue(benchmarkMethodNames.containsAll(expectedBenchmarkMethodNames),
			"Expected ListBenchmarks to have the indicated @Benchmark methods");
	}

	@Test
	public void addToEndMethods() throws Exception {
		ClassOrInterfaceDeclaration klass = parseSource();
		for (MethodDeclaration m : klass.getMethods()) {
			String sName = m.getNameAsString();
			if (sName.startsWith("addToEnd") && sName.endsWith("List")) {
				assertCallsMethod("addToEnd", m);
			}
		}
	}

	@Test
	public void addToBeginningMethods() throws Exception {
		ClassOrInterfaceDeclaration klass = parseSource();
		for (MethodDeclaration m : klass.getMethods()) {
			String sName = m.getNameAsString();
			if (sName.startsWith("addToBeginning") && sName.endsWith("List")) {
				assertCallsMethod("addToBeginning", m);
			}
		}
	}

	protected ClassOrInterfaceDeclaration parseSource() throws FileNotFoundException {
		CompilationUnit cu = StaticJavaParser.parse(new File(
			"src/uk/ac/aston/oop/jcf/bench/ListBenchmarks.java"));
		ClassOrInterfaceDeclaration klass = cu.getClassByName("ListBenchmarks").get();
		return klass;
	}

	private void assertCallsMethod(String methodName, MethodDeclaration n) {
		class MethodCallVisitor extends VoidVisitorAdapter<Void> {
			boolean bCallsMethod = false;

			@Override
			public void visit(MethodCallExpr n, Void arg) {
				super.visit(n, arg);
				if (n.getNameAsString().equals(methodName)) {
					bCallsMethod = true;
				}
			}
		}

		MethodCallVisitor v = new MethodCallVisitor();
		n.accept(v, null);
		assertTrue(v.bCallsMethod, String.format(
			"Method %s should call %s in its body",
			n.getNameAsString(), methodName));
	}
	
}
