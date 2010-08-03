package com.tan.compile;



import static com.tan.util.TanUtil.warnln;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.tools.SimpleJavaFileObject;

/**
 * Load the programText to compile.
 * Use the i/o to load the programText
 * the default encoding is "utf-8"
 * 
 * you can set the encoding when use the constructor.
 * @author dolphin
 *
 * 2010/07/21 10:20:34
 */
public class JavaFile extends SimpleJavaFileObject {

	// ------------------------------ FIELDS ------------------------------
	private final String programText;
	private final String classpath;
	private File classFile ;
	private String className;
	private String encoding;
	
	// -------------------------- PUBLIC INSTANCE METHODS
	public JavaFile(String className) throws URISyntaxException {
		super(convert(className + ".java"), Kind.SOURCE);
		this.className = className;
		classpath = System.getProperty("user.dir", "");
		this.programText = read(className);
	}
	
	public JavaFile(String className, String encoding) throws URISyntaxException {
		super(convert(className + ".java"), Kind.SOURCE);
		this.className = className;
		classpath = System.getProperty("user.dir", "");
		this.encoding = encoding;
		this.programText = read(className, encoding);
	}	
	
	private String read(String className, String encoding) {
		if (StringUtil.isEmpty(encoding)) {
			warnln("The Encoding can not be null or empty!");
			return null;
		}
		File f = new File(classpath,className + ".java");
		InputStream in = null;
		try {
			in = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		byte[] buf = new byte[2046];
		int len = -1;
		StringBuilder builder = new StringBuilder();
		try {
			while ((len = in.read(buf, 0, buf.length)) != -1) {
				builder.append(new String(buf, 0, len, encoding));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				} in = null;
			}
		}
		return builder.toString();
	}

	private String read(String className) {
		encoding = "utf-8";
		return read(className, encoding);
	}
	
	public File getClassFile() {
		if (classFile != null) return classFile;
		return classFile =  new File(classpath, className + ".class");
	}
	
	private static URI convert(String string) {
		return new File(System.getProperty("user.dir", ""),string).toURI();
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
		return programText;
	}
}
