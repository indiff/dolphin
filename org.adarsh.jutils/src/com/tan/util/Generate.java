package com.tan.util;

public final class Generate {
	private final static String INDENT = "\t";
	private final static String N = System.getProperty("line.separator", "\r\n");
	
	
	public final static void generateDummyCode(final StringBuffer b, final String name,
			final String comment) {
		if (name == null || "serialVersionUID".equals(name)){
			return;
		}
		String methodSuffix = Character.toUpperCase(name.charAt(0)) +  name.substring(1);
		b.append(INDENT + "// 设置" + comment + N + 
				INDENT + "vo.set" + methodSuffix  
				+ "(po.get" + methodSuffix
				+ "());" + N
		);
	}


	public final static void generateDummyObjects(final StringBuffer b, final String javaName) {
		b.append(INDENT + javaName  + " vo = " + "new " + javaName + "();" + N);
		b.append(INDENT + javaName  + " po = " + "new " + javaName + "();" + N);
	}

}
