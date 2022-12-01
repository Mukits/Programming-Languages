package uk.ac.aston.oop.inheritance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class JavadocTest {

	private class JavadocChecker extends VoidVisitorAdapter<Void> {
		public boolean hasJavadoc = false;
		
		@Override
		public void visit(JavadocComment n, Void arg) {
			hasJavadoc = true;
		}
	}

	private JavaParser parser;

	@BeforeEach
	public void setup() {
		ParserConfiguration pc = new ParserConfiguration();
		pc.setDoNotAssignCommentsPrecedingEmptyLines(false);
		parser = new JavaParser(pc);
	}

	@Test
	public void packageInfoMain() {
		assertTrue(
			mainPackageInfoFile().exists(),
			"There should be a package-info.java file for the main package");
	}


	@Test
	public void packageInfoShapes() {
		assertTrue(
			shapesPackageInfoFile().exists(),
			"There should be a package-info.java file for the shapes package");
	}

	@Test
	public void packageInfoMainHasJavadoc() throws Exception {
		CompilationUnit cu = parser.parse(mainPackageInfoFile()).getResult().get();
		assertPackageDeclarationHasJavadocComment(cu);
	}

	@Test
	public void packageInfoShapesHasJavadoc() throws Exception {
		CompilationUnit cu = parser.parse(shapesPackageInfoFile()).getResult().get();
		assertPackageDeclarationHasJavadocComment(cu);
	}

	@Test
	public void shapeClassesHaveDescriptions() throws Exception {
		File[] shapesJavaFiles = shapesPackagePath().toFile()
			.listFiles((dir, name) -> !"package-info.java".equals(name) && name.endsWith(".java"));

		for (File f : shapesJavaFiles) {
			CompilationUnit cu = parser.parse(f).getResult().get();
			assertEquals(1, cu.getTypes().size(), f.getName() + " should have one type");
			TypeDeclaration<?> type = cu.getTypes().get(0);
			assertTrue(type instanceof ClassOrInterfaceDeclaration, f.getName() + " should have a class");
			assertTrue(type.getComment().isPresent(), type.getName() + " should have a comment");
			assertTrue(type.getComment().get() instanceof JavadocComment, type.getName() + " should have a Javadoc comment");
		}
	}

	@Test
	public void shapeClassIsFullyDocumented() throws Exception {
		File fShapeJava = shapesPackagePath().resolve("Shape.java").toFile();
		CompilationUnit cu = parser.parse(fShapeJava).getResult().get();
		ClassOrInterfaceDeclaration declShape = (ClassOrInterfaceDeclaration) cu.getType(0);

		// CONSTRUCTOR
		
		ConstructorDeclaration ctor = declShape.getConstructorByParameterTypes(
			double.class, double.class, double.class, double.class).get();

		assertTrue(ctor.getComment().isPresent(),
			"Shape's constructor should have a comment");
		assertTrue(ctor.getComment().get() instanceof JavadocComment,
			"Shape's constructor should have a Javadoc comment");

		String jdocCtorContent = ctor.getComment().get().getContent();
		for (Parameter param : ctor.getParameters()) {
			assertTrue(jdocCtorContent.contains("@param " + param.getNameAsString()),
				"Expected to see '@param "
				+ param.getNameAsString()
				+ "' in the Javadoc comment for the constructor");
		}

		for (MethodDeclaration m : declShape.getMethods()) {
			if (!m.isPublic()) {
				continue;
			}

			assertTrue(m.getComment().isPresent(),
				"Method Shape." + m.getNameAsString() + " should have a comment above it");
			assertTrue(m.getComment().get() instanceof JavadocComment,
				"Method Shape." + m.getNameAsString() + " should have a Javadoc comment above it");

			String content = m.getComment().get().getContent();
			if (!"void".equals(m.getTypeAsString())) {
				assertTrue(content.contains("@return "),
					"Expected to see @return in the Javadoc comment for " + m.getNameAsString());
			}

			for (Parameter param : m.getParameters()) {
				assertTrue(content.contains("@param " + param.getNameAsString()),
					String.format(
						"Expected to see '@param %s' in the Javadoc comment for Shape.%s",
						param.getNameAsString(), m.getNameAsString()));
			}
		}
		
	}

	private void assertPackageDeclarationHasJavadocComment(CompilationUnit cu) {
		JavadocChecker checker = new JavadocChecker();
		cu.accept(checker, null);
		assertTrue(checker.hasJavadoc,
			"package-info.java should have one Javadoc comment above the package declaration");
	}

	private Path mainPackagePath() {
		return Path.of("src", "uk", "ac", "aston", "oop", "inheritance");
	}

	private Path shapesPackagePath() {
		return Path.of(mainPackagePath().toString(), "shapes");
	}

	private File mainPackageInfoFile() {
		return Path.of(mainPackagePath().toString(), "package-info.java").toFile();
	}

	private File shapesPackageInfoFile() {
		return Path.of(shapesPackagePath().toString(), "package-info.java").toFile();
	}
	
}
