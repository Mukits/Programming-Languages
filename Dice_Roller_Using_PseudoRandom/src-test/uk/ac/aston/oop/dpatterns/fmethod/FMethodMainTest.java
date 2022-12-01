package uk.ac.aston.oop.dpatterns.fmethod;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class FMethodMainTest {

	@Test
	public void mainMethod() throws FileNotFoundException {
		CompilationUnit cu = StaticJavaParser.parse(new File(
			"src/uk/ac/aston/oop/dpatterns/fmethod/FMethodMain.java"
		));

		ClassOrInterfaceDeclaration fMethodMain = cu.getClassByName("FMethodMain").get();
		MethodDeclaration mMain = fMethodMain.getMethodsBySignature("main", "String[]").get(0);

		class UncommentedNewVisitor extends VoidVisitorAdapter<Void> {
			boolean uncommentedExecuting = false;
			boolean uncommentedDryRun = false;

			@Override
			public void visit(ObjectCreationExpr n, Void arg) {
				super.visit(n, arg);

				switch (n.getTypeAsString()) {
				case "ExecutingCommandReader":
					uncommentedExecuting = true;
					break;
				case "DryRunCommandReader":
					uncommentedDryRun = true;
					break;
				}
			}
		}
		
		UncommentedNewVisitor v = new UncommentedNewVisitor();
		mMain.accept(v, null);
		assertTrue(v.uncommentedDryRun,
			"You should have uncommented the creation of the DryRunCommandReader");
		assertTrue(v.uncommentedExecuting,
			"You should have uncommented the creation of the ExecutingCommandReader");
	}

}
