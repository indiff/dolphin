package com.tan.util;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.internal.core.SourceField;


/**
 * 生成代码.
 * @author TanYuanji
 *
 */
public final class Generate {
	private final static String INDENT = "\t";
	private final static String N = System.getProperty("line.separator", "\r\n");
	
	public final static void generateDummyCode(final StringBuffer b, 
			final IField field,
			final String comment) {
		final String name = field.getElementName();
		if (name == null || "serialVersionUID".equals(name)){
			return;
		}
		
		String typeSignature = null, dummy = null;
		try {
			typeSignature = field.getTypeSignature();
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		
		if ( null != typeSignature ) {
			dummy = StringUtil.getDummyField( typeSignature , comment, name );
		}
		
		generateDummyCode( b,  name, comment , dummy);
	}
	
	
	public final static void generateDummyCode(
			final StringBuffer b, 
			final String name,
			final String comment,
			final String ... dummys) {
		if (name == null || "serialVersionUID".equals(name)){
			return;
		}
		final String methodSuffix =
				Character.toUpperCase(name.charAt(0)) + 
				name.substring(1), dummy;
		
		if ( null == dummys || dummys.length == 0  || dummys[0] == null ) {
			dummy = "po.get" + methodSuffix + "()";
		} else {
			dummy = dummys[0];
		}
		
		StringUtil.append(b,
				INDENT, "// 设置", comment , N,
				INDENT,"vo.set" , methodSuffix,'(',dummy,");", N
		);
	}
	
	public final static void generateDummyGetter(final StringBuffer b, final String name,
			final String comment) {
		if (name == null || "serialVersionUID".equals(name)){
			return;
		}
		String methodSuffix = Character.toUpperCase(name.charAt(0)) +  name.substring(1);
		
		StringUtil.append(b,
			INDENT , "// 获取" , comment , N  ,
			INDENT , "po.get" , methodSuffix  , "();"  , N
//			INDENT , "System.out.println(po.get" , methodSuffix  , "());"  , N
		);
	}

	public final static void generateGetterSetter(final StringBuffer b, final String name,
			final String comment) {
//		String name = field.getElementName();
//		int type = field.getElementType();
		if (name == null || "serialVersionUID".equals(name)){
			return;
		}
		String methodSuffix = Character.toUpperCase(name.charAt(0)) +  name.substring(1);
		StringUtil.append(b,
				 INDENT + "// 获取" + comment + N + 
				 INDENT + "public Object get" + methodSuffix + "() {" + N + 
				 INDENT + INDENT + "return this." + name + ";" + N + 
				 INDENT + "}"+ N + N
		);
		
//		String methodSuffix = Character.toUpperCase(name.charAt(0)) +  name.substring(1);
		b.append(INDENT + "// 设置" + comment + N + 
				INDENT + "public Object set" + methodSuffix + "(final Object " + name + ") {" + N + 
				 INDENT + INDENT + "this." + name + "=" + name + ";" + N + 
				 INDENT + "}"+ N 
		);
	}

	public final static void generateDummyObjects(final StringBuffer b, final String javaName) {
		b.append(INDENT + javaName  + " vo = " + "new " + javaName + "();" + N);
		b.append(INDENT + javaName  + " po = " + "new " + javaName + "();" + N);
	}
	
	
	public static void main(String[] args) {
		StringBuffer b = new StringBuffer();
//		generateDummyGetter( b, "name", "姓名" );
		generateDummyCode( b, "name", "姓名" , "张三" );
		System.out.println(b);
	}
}
