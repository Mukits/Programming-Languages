package uk.ac.aston.oop.dpatterns.singleton;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class DiceRollerTest {

	private static final String CLASS_DICE_ROLLER = "DiceRoller";
	private static final String FILE_DICE_ROLLER = "src/uk/ac/aston/oop/dpatterns/singleton/DiceRoller.java";

	@Test
	public void constructor() throws Exception {
		ClassOrInterfaceDeclaration klass = parse(FILE_DICE_ROLLER, CLASS_DICE_ROLLER);

		Optional<ConstructorDeclaration> ctor = klass.getDefaultConstructor();
		assertTrue(ctor.isPresent(), CLASS_DICE_ROLLER + " should have a constructor with no parameters");
		assertTrue(ctor.get().isPrivate(), "The constructor of DiceRoller should be private");

		class NewRandomArgumentVisitor extends VoidVisitorAdapter<Void> {
			boolean hasNewRandomWithSeed = false;
			boolean hasNewRandomWithoutSeed = false;

			@Override
			public void visit(ObjectCreationExpr n, Void arg) {
				super.visit(n, arg);
				if ("Random".equals(n.getTypeAsString())) {
					hasNewRandomWithSeed = hasNewRandomWithSeed || n.getArguments().size() == 1;
					hasNewRandomWithoutSeed = hasNewRandomWithoutSeed || n.getArguments().size() == 0;
				}
			}
		}

		NewRandomArgumentVisitor v = new NewRandomArgumentVisitor();
		ctor.get().accept(v, null);
		assertTrue(v.hasNewRandomWithSeed,
			"The DiceRoller constructor should create the Random object with a seed if seed is not null");
		assertTrue(v.hasNewRandomWithoutSeed,
			"The DiceRoller constructor should create the Random object without a seed if seed is null");
	}

	@Test
	public void sameInstance() throws Exception {
		DiceRoller r = DiceRoller.getInstance();
		assertNotNull(r,
			"The getInstance() static method in DiceRoller should return an instance");
		assertSame(r, DiceRoller.getInstance(),
			"The getInstance() static method in DiceRoller should always return the same instance");
	}

	@Test
	public void privateFields() throws Exception {
		ClassOrInterfaceDeclaration klass = parse(FILE_DICE_ROLLER, CLASS_DICE_ROLLER);

		// These must exist
		FieldDeclaration fldInstance = getField(klass, "instance");
		assertIsPrivate(klass, fldInstance);
		assertIsStatic(klass, fldInstance);

		FieldDeclaration fldRandom = getField(klass, "random");
		assertIsPrivate(klass, fldRandom);
		assertIsNotStatic(klass, fldRandom);
		assertIsFinal(klass, fldRandom);

		FieldDeclaration fldSeed = getField(klass, "seed");
		assertIsPrivate(klass, fldSeed);
		assertIsStatic(klass, fldSeed);

		// No other fields may exist
		assertNoFieldsOtherThan(klass, "instance", "random", "seed");
	}

	@Test
	public void instanceStartsAsNull() throws Exception {
		ClassOrInterfaceDeclaration klass = parse(FILE_DICE_ROLLER, CLASS_DICE_ROLLER);

		FieldDeclaration fldInstance = getField(klass, "instance");
		Optional<Expression> instanceInitializer = fldInstance.getVariable(0).getInitializer();
		if (instanceInitializer.isPresent()) {
			assertTrue(instanceInitializer.get().isNullLiteralExpr(),
				"After completing the change to lazy initalisation, "
				+ "the instance field should only be set to a DiceRoller "
				+ "from the getInstance() method");
		}
	}

	@Test
	public void diceRoll() {
		final int nRolls = 10;
		final int nFaces = 20;
		for (int i = 0; i < nRolls; i++) {
			final int roll = DiceRoller.getInstance().roll(nFaces);
			assertTrue(roll >= 1 && roll <= nFaces, String.format(
				"roll(int) in DiceRoll should produce a number between 1 and %d: rolled %d",
				nFaces, roll));
		}
	}

	@Test
	public void diceRollUsesRandom() throws Exception {
		ClassOrInterfaceDeclaration klass = parse(FILE_DICE_ROLLER, CLASS_DICE_ROLLER);

		class RandomNextIntVisitor extends VoidVisitorAdapter<Void> {
			boolean usesRandom = false;
			boolean usesNextInt = false;

			@Override
			public void visit(MethodCallExpr n, Void arg) {
				super.visit(n, arg);
				usesNextInt = usesNextInt || "nextInt".equals(n.getNameAsString());
			}

			@Override
			public void visit(NameExpr n, Void arg) {
				super.visit(n, arg);
				usesRandom = usesRandom || "random".equals(n.getNameAsString());
			}
		}
		
		RandomNextIntVisitor v = new RandomNextIntVisitor();
		klass.getMethodsBySignature("roll", "int").get(0).accept(v, null);
		assertTrue(v.usesNextInt, "roll should use Random.nextInt");
		assertTrue(v.usesRandom, "roll should use the random field");
	}

	protected ClassOrInterfaceDeclaration parse(String path, String className) throws FileNotFoundException {
		CompilationUnit cu = StaticJavaParser.parse(new File(path));
		Optional<ClassOrInterfaceDeclaration> klass = cu.getClassByName(className);
		assertTrue(klass.isPresent(), className + " should exist");
		return klass.get();
	}

	protected void assertNoFieldsOtherThan(ClassOrInterfaceDeclaration klass, String... names) {
		Set<String> expectedFields = new HashSet<>(Arrays.asList(names));
		for (FieldDeclaration decl : klass.getFields()) {
			for (VariableDeclarator var : decl.getVariables()) {
				assertTrue(
					expectedFields.contains(var.getNameAsString()),
					String.format("Unexpected field %s in %s", var.getName(), CLASS_DICE_ROLLER));
			}
		}
	}

	protected FieldDeclaration getField(ClassOrInterfaceDeclaration klass, String name) {
		Optional<FieldDeclaration> fldInstance = klass.getFieldByName(name);
		assertTrue(fldInstance.isPresent(), String.format(
			"%s should have field '%s'", klass.getNameAsString(), name));
		return fldInstance.get();
	}

	protected void assertIsPrivate(ClassOrInterfaceDeclaration klass, FieldDeclaration fld) {
		assertFieldIs(FieldDeclaration::isPrivate, klass, fld, "private");
	}

	protected void assertIsFinal(ClassOrInterfaceDeclaration klass, FieldDeclaration fld) {
		assertFieldIs(FieldDeclaration::isFinal, klass, fld, "final");
	}

	protected void assertIsStatic(ClassOrInterfaceDeclaration klass, FieldDeclaration fld) {
		assertFieldIs(FieldDeclaration::isStatic, klass, fld, "static");
	}

	protected void assertIsNotStatic(ClassOrInterfaceDeclaration klass, FieldDeclaration fld) {
		assertFieldIs(f -> !f.isStatic(), klass, fld, "not static");
	}

	protected void assertFieldIs(Function<FieldDeclaration, Boolean> assertion,
		ClassOrInterfaceDeclaration klass, FieldDeclaration fld, String desc) {
		assertTrue(assertion.apply(fld), String.format(
			"%s.%s should be %s",
			klass.getNameAsString(), fld.getVariable(0).getName(), desc));
	}

}
