package compile;

import java.net.URI;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

import java.util.*;

// Reference: http://www.java2s.com/Code/Java/JDK-6/CompilingfromMemory.htm

public class MyJavaCompiler {
	
	private final String DEFAULT_CLASS_NAME = "Solution";
	
	/*
	 * Returns "" if code compiles successfully. Else it returns the error messages.
	 */
	
	public String checkValidJavaCode (String code) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		JavaSourceFile srcFile = new JavaSourceFile(DEFAULT_CLASS_NAME, code);
		Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(srcFile);
		boolean success = compiler.getTask( null, null, diagnostics, null, null, fileObjects).call();
		
		StringBuilder sb = new StringBuilder();
		
		for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
		      /*System.out.println(diagnostic.getCode());
		      System.out.println(diagnostic.getKind());
		      System.out.println(diagnostic.getPosition());
		      System.out.println(diagnostic.getStartPosition());
		      System.out.println(diagnostic.getEndPosition());
		      System.out.println(diagnostic.getSource());*/
		      sb.append(diagnostic.getMessage(null)+"\n");
		}		
		return sb.toString();
	}
	
}

class JavaSourceFile extends SimpleJavaFileObject {

	private final String code;
	
	protected JavaSourceFile(String filename, String code) {
		super( URI.create("string:///" + filename.replace('.','/') + Kind.SOURCE.extension),Kind.SOURCE);
        this.code = code;
	}	
	
	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) {
	    return code;
	}
	
	
}
