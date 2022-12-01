package uk.ac.aston.oop.recipes.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.lang.reflect.Constructor;

import org.junit.jupiter.api.Test;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import uk.ac.aston.oop.recipes.io.exceptions.RecipeLoadingException;
import uk.ac.aston.oop.recipes.io.exceptions.RecipeSavingException;

public class RecipeExceptionTest {

	private static class HasMatchingSuperCallVisitor extends VoidVisitorAdapter<Void> {
		private ConstructorDeclaration constructor;
		private boolean found;

		public HasMatchingSuperCallVisitor(ConstructorDeclaration ctor) {
			this.constructor = ctor;
		}

		@Override
		public void visit(ExplicitConstructorInvocationStmt n, Void arg) {
			assertFalse(n.isThis(), "The explicit constructor invocation " + n + " should be a super call");
			assertEquals(constructor.getParameters().size(), n.getArguments().size(),
				"The explicit constructor invocation " + n + " should pass on all the arguments the constructor received");

			found = true;
		}

		public boolean isFound() {
			return found;
		}
	}


	@Test
	public void loading() throws Exception {
		assertExceptionClassIsValid(RecipeLoadingException.class);
	}

	@Test
	public void loadingUsesSuper() throws Exception {
		CompilationUnit cu = StaticJavaParser.parse(new File(
			"src/uk/ac/aston/oop/recipes/io/exceptions/RecipeLoadingException.java"));
		ClassOrInterfaceDeclaration klass = cu.getClassByName("RecipeLoadingException").get();
		assertAllConstructorsUseSuper(klass);
	}

	@Test
	public void saving() throws Exception {
		assertExceptionClassIsValid(RecipeSavingException.class);
	}

	@Test
	public void savingUsesSuper() throws Exception {
		CompilationUnit cu = StaticJavaParser.parse(new File(
			"src/uk/ac/aston/oop/recipes/io/exceptions/RecipeSavingException.java"));
		ClassOrInterfaceDeclaration klass = cu.getClassByName("RecipeSavingException").get();
		assertAllConstructorsUseSuper(klass);
	}

	protected void assertAllConstructorsUseSuper(ClassOrInterfaceDeclaration klass) {
		for (ConstructorDeclaration ctor : klass.getConstructors()) {
			HasMatchingSuperCallVisitor visitor = new HasMatchingSuperCallVisitor(ctor);
			ctor.getBody().accept(visitor, null);
			assertTrue(visitor.isFound(), "There should be a super call inside " + ctor);
		}
	}

	protected void assertExceptionClassIsValid(Class<?> klass) throws NoSuchMethodException {
		assertSame(Exception.class, klass.getSuperclass(),
			klass.getSimpleName() + " should extend directly Exception");

		boolean bHasStringCtor = false, bHasStringThrowableCtor = false;
		for (Constructor<?> ctor : klass.getConstructors()) {
			Class<?>[] pt = ctor.getParameterTypes();
			if (pt.length == 1 && pt[0] == String.class) {
				bHasStringCtor = true;
			} else if (pt.length == 2 && pt[0] == String.class && pt[1] == Throwable.class) {
				bHasStringThrowableCtor = true;
			}
		}

		assertTrue(bHasStringCtor,
			klass.getSimpleName() + " should have a constructor taking (String)");
		assertTrue(bHasStringThrowableCtor,
			klass.getSimpleName() + " should have a constructor taking (String, Throwable)");
	}
}
