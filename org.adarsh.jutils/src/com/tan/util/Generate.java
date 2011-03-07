package com.tan.util;

public class Generate {
	private static String INDENT = "\t";
	private static String N = System.getProperty("line.separator", "\r\n");
	
	
	public static void generateDummyCode(StringBuffer b, String name,
			String comment) {
		String methodSuffix = Character.toUpperCase(name.charAt(0)) + 
		name.substring(1);
		b.append(INDENT + "// 设置" + comment + N + 
				INDENT + "vo.set" + methodSuffix  
				+ "(po.get" + methodSuffix
				+ "());" + N
		);
	}


	public static void generateDummyObjects(StringBuffer b, String javaName) {
		b.append(INDENT + javaName  + " vo = " + "new " + javaName + "();" + N);
		b.append(INDENT + javaName  + " po = " + "new " + javaName + "();" + N);
	}

}
