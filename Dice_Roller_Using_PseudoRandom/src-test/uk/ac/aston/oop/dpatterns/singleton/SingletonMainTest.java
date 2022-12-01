package uk.ac.aston.oop.dpatterns.singleton;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class SingletonMainTest {

	@Test
	public void shouldUncommentSetSeedCall() throws Exception {
		CompilationUnit cu = StaticJavaParser.parse(new File(
			"src/uk/ac/aston/oop/dpatterns/singleton/SingletonMain.java"));

		ClassOrInterfaceDeclaration klass = cu.getClassByName("SingletonMain").get();
		MethodDeclaration m = klass.getMethodsBySignature("main", "String[]").get(0);

		class SetSeedVisitor extends VoidVisitorAdapter<Void> {
			boolean hasSetSeedCall = false;

			@Override
			public void visit(MethodCallExpr n, Void arg) {
				super.visit(n, arg);
				hasSetSeedCall = hasSetSeedCall || "setSeed".equals(n.getNameAsString());
			}
		}

		SetSeedVisitor v = new SetSeedVisitor();
		m.accept(v, null);
		assertTrue(v.hasSetSeedCall,
			"By the end of the lab, should have uncommented the setSeed call in the SingletonMain main method");
	}

}
