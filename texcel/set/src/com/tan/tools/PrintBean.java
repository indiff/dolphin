package com.tan.tools;

import static com.tan.util.TanUtil.append;
import static com.tan.util.TanUtil.println;

import java.lang.reflect.Method;

import com.tan.bean.Module;

public class PrintBean {
	public final static int SETTER = 0x01;

	public final static int GETTER = 0x02;

	public static void main(String[] args) {
		
		printMethod(Module.class, SETTER);
//		
//		println("--------------------------");
//		
//		printMethod(Schedule.class, GETTER);
		
//		BigDecimal one = new BigDecimal(0.03345645);
//		BigDecimal two = new BigDecimal(0.2156465);
		
//		println(one.round(new MathContext(2, RoundingMode.HALF_UP)));
//		println(NumberUtil.round(one.multiply(new BigDecimal(0.25)), 1));
//		
//		println(one.round(new MathContext(1, RoundingMode.HALF_UP)));
////		
////		println(one.compareTo(new BigDecimal(0.1)));
//		
//		
//		println(new SimpleDateFormat("HHmm").format(new Date()));
//		
//		
//		BigDecimal first = new BigDecimal(1);
//		
//		println(first.compareTo(new BigDecimal(1)));
		
	}

	private static void printMethod(Class<?> clazz, int type) {
		Method[] methods = clazz.getDeclaredMethods();
		for (Method m : methods) {
			String methodName = m.getName();
			Class<?>[] paramTypes = m.getParameterTypes();
			StringBuilder buf = new StringBuilder("(");
			if (paramTypes != null && paramTypes.length != 0){
				for (Class<?> paramType : paramTypes){
					append(buf, paramType.getSimpleName(), ',');
				}
			}
			int len = buf.length();
			if (len != 1) buf.deleteCharAt(len - 1);
			buf.append(')');
			switch (type) {
				case SETTER: {
					if (methodName.startsWith("set")) {
						println(methodName,buf, ';');
					}
					break;
				}
				case GETTER: {
					if (methodName.startsWith("get")) {
						println(methodName,buf, ';');
					}
					break;
				}
			}
		}
	}
}
