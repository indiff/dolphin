package com.tan.compile;



import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

/**
 * Compile the java file.
 * @author dolphin
 *
 * 2010/07/21 10:08:05
 */
public final class Compiler {
	private JavaFile javaFile;
	public Compiler(final JavaFile javaFile) {
		this.javaFile = javaFile;
	}
	public boolean compile(JavaFileObject... source) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		final JavaCompiler.CompilationTask task = compiler.getTask( 
		/*
		 * default
		 * System
		 * .err
		 */
		null,
		/*
		 * standard file manager, If we wrote our own JavaFileManager, we could
		 * control the location of the generated class files. By default they
		 * will go in the CWD, which MUST be on the classpath
		 */
		null,
		/* standard DiagnosticListener */
		null,
		/* no options */
		null,
		/* no annotation classes */
		null,
		/*
		 * We must convert JavaFileObject... source code list to Iterable<?
		 * extends JavaFileObject>
		 */
		Arrays.asList(source));
		return task.call();
	}
	
	public Class<?> compileSource(String javaName) {
		final boolean status = 
			this.compile(javaFile);
		Class<?> clazz = null;
		if (!status) return null;
		else {
			File f = javaFile.getClassFile();
			URLClassLoader loader = null;
			try {
				loader = URLClassLoader.newInstance(new URL[]{f.toURI().toURL()});
				clazz = loader.loadClass(javaName);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return clazz;
	}
	// --------------------------- main() method ---------------------------
	public static void main(String[] args) throws Exception{
		// compile item
//		JavaFile javaFile = null;
//		try {
//			javaFile = new JavaFile("Demo");
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
//		Compiler compiler = new Compiler(javaFile);
//		String classpath = System.getProperty("java.class.path", "");
//		System.setProperty("java.class.path", classpath + ";D:\\PSWG\\workspace\\texcel");
//		out.println("status of compile: " + 
//				compiler.compileSource("Demo"));
//		
//		out.println("Your CWD is " + new File(".").getCanonicalPath());
//		out.println("Your classpath is " + System.getProperty("java.class.path", ""));
		
//		URL url = ClassLoader.getSystemClassLoader().getResource("");
//		URI uri = new URI("file:/D:/PSWG/workspace/texcel/Config.class");
//		out.println(url);
//		out.println(uri);
//		URLClassLoader loader = 
//			URLClassLoader.newInstance(new URL[]{uri.toURL()});
//		Class<?> clazz = loader.loadClass("Config");
//		out.println(clazz);
	}
}
