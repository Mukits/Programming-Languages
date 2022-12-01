package uk.ac.aston.oop.acint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.InstanceOfExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.declarations.ResolvedValueDeclaration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import uk.ac.aston.oop.acint.shapes.Circle;
import uk.ac.aston.oop.acint.shapes.Drawable;

/**
 * Tests for the {@link Launcher} class. Since we need to
 * look at the code and we cannot reliable mock JavaFX, we
 * use JavaParser to parse and analyse the code.
 */
public class LauncherTest {

	@Test
	public void launcherHasDrawableArray() throws Exception {
		ClassOrInterfaceDeclaration launcherClass = parseLauncher();

		Optional<FieldDeclaration> oShapes = launcherClass.getFieldByName("drawables");
		assertTrue(oShapes.isPresent(), "The Launcher class should have a 'drawables' field");
		FieldDeclaration fShapes = oShapes.get();
		assertTrue(fShapes.isPrivate(), "The 'drawables' field should be private");		
		assertTrue(fShapes.getCommonType().isArrayType(), "The 'drawables' field should be an array");
		ArrayType atShapes = fShapes.getCommonType().asArrayType();
		assertEquals("Drawable", atShapes.getElementType().asClassOrInterfaceType().getName().toString(),
			"The 'drawables' field should be an array of Drawable objects");
	}

	@Test
	public void launcherStartSetsUpArray() throws Exception {
		MethodDeclaration mStart = getLauncherMethod("start");

		Set<String> expectedCreations = new HashSet<>(
			Arrays.asList("Circle", "Rectangle", "Ellipse", "Cross"));

		class TestVisitor extends VoidVisitorAdapter<Void> {
			boolean hasCalledDraw = false;
			boolean bSetsArray = false;

			@Override
			public void visit(AssignExpr n, Void arg) {
				if (n.getTarget().isNameExpr() && "drawables".equals(n.getTarget().asNameExpr().getName().toString())) {
					assertFalse(hasCalledDraw,
						"In Launcher.start, the drawables array should be set up before calling draw");
					bSetsArray = true;
				}
				super.visit(n, arg);
			}

			@Override
			public void visit(MethodCallExpr n, Void arg) {
				if ("draw".equals(n.getNameAsString())) {
					hasCalledDraw = true;
				}
			}

			@Override
			public void visit(ObjectCreationExpr n, Void arg) {
				String typeName = n.getType().getName().toString();
				expectedCreations.remove(typeName);
			}
		}

		TestVisitor tv = new TestVisitor();
		mStart.accept(tv, null);
		assertTrue(tv.bSetsArray, "Launcher.start should create and populate the drawables array");
		assertEquals(0, expectedCreations.size(),
			"Launcher.start is missing the creation of some types of shapes: " + expectedCreations);
	}

	@Test
	public void launcherDrawDoesNotUseShapeSubclasses() throws Exception {
		MethodDeclaration mDraw = getLauncherMethod("draw");

		final Set<String> forbiddenNames = new HashSet<>(Arrays.asList("Rectangle", "Ellipse", "Circle"));
		mDraw.accept(new VoidVisitorAdapter<>() {
			@Override
			public void visit(ClassOrInterfaceType n, Object arg) {
				Node parentNode = n; 
				do {
					parentNode = parentNode.getParentNode().get();
					if (parentNode instanceof IfStmt
						&& parentNode.findFirst(InstanceOfExpr.class).isPresent()) {
						// Using Circle is OK inside the instanceof block
						return;
					}
				} while (parentNode.getParentNode().isPresent());

				if (forbiddenNames.contains(n.getName().toString())) {
					fail("The 'draw' method should not mention any of these types, except for downcasting: "
							+ forbiddenNames + ", found: " + n.getName().toString());
				}
			}
		}, null);
	}

	@Test
	public void launcherDrawLoopsOverDrawables() throws Exception {
		MethodDeclaration mDraw = getLauncherMethod("draw");

		class TestVisitor extends VoidVisitorAdapter<Void> {
			boolean hasLoop = false;
			boolean hasDrawCall = false;
			boolean hasMoveCall = false;

			@Override
			public void visit(DoStmt n, Void arg) {
				// probably some kind of loop over indices
				hasLoop = true;
				super.visit(n, arg);
			}

			@Override
			public void visit(ForEachStmt n, Void arg) {
				// for-each loop: we get to check the variable type
				Type elementType = n.getVariable().getElementType();
				assertTrue(elementType.isClassOrInterfaceType(), "In the for-each loop in 'draw', the variable should be of type Shape");
				String typeName = elementType.asClassOrInterfaceType().getName().toString();
				assertEquals("Drawable", typeName, "In the for-each loop in 'draw', the variable should be of type Shape");
				hasLoop = true;

				super.visit(n, arg);
			}

			@Override
			public void visit(ForStmt n, Void arg) {
				// 3-part for loop over indices
				hasLoop = true;
				super.visit(n, arg);
			}

			@Override
			public void visit(MethodCallExpr n, Void arg) {
				switch (n.getNameAsString()) {
				case "draw":
					assertMethodInvokedOnType(n, Drawable.class);
					hasDrawCall = true;
					break;
				case "move":
					assertMethodInvokedOnType(n, Drawable.class);
					hasMoveCall = true;
					break;
				default:
					// do nothing
				}
			}

			@Override
			public void visit(WhileStmt n, Void arg) {
				// while loop over indices
				hasLoop = true;
				super.visit(n, arg);
			}
		};

		TestVisitor tv = new TestVisitor();
		mDraw.accept(tv, null);
		assertTrue(tv.hasLoop, "There should be a loop in Launcher.draw over the Shape array");
		assertTrue(tv.hasDrawCall, "There should be a call to Shape.draw inside the loop in Launcher.draw");
		assertTrue(tv.hasMoveCall, "There should be a call to Shape.move inside the loop in Launcher.draw");
	}

	@Test
	public void launcherDrawDowncastsToCircleAndScales() throws Exception {
		MethodDeclaration mDraw = getLauncherMethod("draw");

		class TestVisitor extends VoidVisitorAdapter<Void> {
			boolean bHasInstanceOf = false;
			boolean bHasScaleCall = false;

			@Override
			public void visit(IfStmt n, Void arg) {
				if (n.findFirst(InstanceOfExpr.class).isPresent()) {
					bHasInstanceOf = true;

					n.accept(new VoidVisitorAdapter<>() {
						@Override
						public void visit(MethodCallExpr n, Object arg) {
							if ("scale".equals(n.getName().toString())) {
								assertMethodInvokedOnType(n, Circle.class);
								bHasScaleCall = true;
							}
						}
					}, null);
				}
			}
		}

		TestVisitor tv = new TestVisitor();
		mDraw.accept(tv, null);
		assertTrue(tv.bHasInstanceOf,
			"There should be an 'instanceof Circle' check inside the loop in Launcher.draw");
		assertTrue(tv.bHasScaleCall,
			"Inside the if with the instanceof check, there should be a call to Circle.scale");
	} 

	private void assertMethodInvokedOnType(MethodCallExpr n, Class<?> klass) {
		Optional<NameExpr> nameExpr = n.getScope().get().findFirst(NameExpr.class);
		assertTrue(nameExpr.isPresent(), "The scope of the " + n.getNameAsString() + " call should be a variable");

		ResolvedValueDeclaration nameDecl = nameExpr.get().resolve();
		String referenceTypeName = null;
		if (nameDecl.getType().isReferenceType()) {
			referenceTypeName = nameDecl.getType().asReferenceType().getQualifiedName();
		} else if (nameDecl.getType().isArray()){
			referenceTypeName = nameDecl.getType().asArrayType()
				.getComponentType().asReferenceType().getQualifiedName();
		}

		assertEquals(klass.getCanonicalName(), referenceTypeName,
			"The " + n.getNameAsString() + " call should be done on a Shape");
	}
	
	private MethodDeclaration getLauncherMethod(String name) throws FileNotFoundException {
		ClassOrInterfaceDeclaration launcherClass = parseLauncher();

		List<MethodDeclaration> lmDraw = launcherClass.getMethodsByName(name);
		assertEquals(1, lmDraw.size(), "There should be exactly one '" + name + "' method in Launcher");
		MethodDeclaration mDraw = lmDraw.get(0);
		return mDraw;
	}
	
	private ClassOrInterfaceDeclaration parseLauncher() throws FileNotFoundException {
		JavaSymbolSolver solver = new JavaSymbolSolver(new CombinedTypeSolver(
			new ReflectionTypeSolver(),
			new JavaParserTypeSolver(new File("src"))
		));

		ParserConfiguration configuration = new ParserConfiguration();
		configuration.setSymbolResolver(solver);
		JavaParser parser = new JavaParser(configuration);
		
		CompilationUnit parsedLauncher = parser.parse(getLauncherJavaFile()).getResult().get();
		Optional<ClassOrInterfaceDeclaration> oLauncher = parsedLauncher.getClassByName("Launcher");
		assertTrue(oLauncher.isPresent(), "The uk.ac.aston.oop.acint.Launcher class should exist");
		ClassOrInterfaceDeclaration launcherClass = oLauncher.get();

		return launcherClass;
	}

	private File getLauncherJavaFile() {
		return getCodeFolderPath().resolve("Launcher.java").toFile();
	}

	private Path getCodeFolderPath() {
		return Paths.get("src", "uk", "ac", "aston", "oop", "acint");
	}
}
