package com.tan.compile;



import java.net.URISyntaxException;

public class CompilerUtil {

	/**
	 * @param args
	 */
	public static Class<?> compile(String javaName) {
		// compile item
		JavaFile javaFile = null;
		try {
			javaFile = new JavaFile(javaName);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		Compiler compiler = new Compiler(javaFile);
		return compiler.compileSource(javaName);
	}

}
