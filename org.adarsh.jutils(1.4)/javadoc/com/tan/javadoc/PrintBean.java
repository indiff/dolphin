package com.tan.javadoc;

import static com.tan.util.TanUtil.append;
import static com.tan.util.TanUtil.println;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;

public class PrintBean {
	public final static int SETTER = 0x01;

	public final static int GETTER = 0x02;
	
	
	
	private String msg;
	private boolean flg;
	private int num;
	private short xy;
	private byte f;
	private char a;
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isFlg() {
		return flg;
	}

	public void setFlg(boolean flg) {
		this.flg = flg;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public short getXy() {
		return xy;
	}

	public void setXy(short xy) {
		this.xy = xy;
	}

	public byte getF() {
		return f;
	}

	public void setF(byte f) {
		this.f = f;
	}

	public char getA() {
		return a;
	}

	public void setA(char a) {
		this.a = a;
	}

	public static void main(String[] args) {
		printMethod(PrintBean.class, SETTER);
	}

	private static void printMethod(Class<?> clazz, int type) {
		if (type == SETTER) {
			println("-------------SETTER-------------");
		} else if (type == GETTER) {
			println("-------------GETTER-------------");
		}
		Method[] methods = clazz.getDeclaredMethods();
		for (Method m : methods) {
			String methodName = m.getName();
			Class<?>[] paramTypes = m.getParameterTypes();
			StringBuilder buf = new StringBuilder("(");
			if (paramTypes != null && paramTypes.length != 0){
				for (Class<?> paramType : paramTypes){
					append(buf, createDummyType(paramType), ',');
				}
			}
			int len = buf.length();
			if (len != 1) buf.deleteCharAt(len - 1);
			buf.append(')');
			switch (type) {
				case SETTER: {
					if (methodName.startsWith("set")) {
						println("demo.", methodName,buf, ';');
					}
					break;
				}
				case GETTER: {
					
					if (methodName.startsWith("get")) {
						println("demo.", methodName,buf, ';');
					}
					break;
				}
			}
		}
	}

	private static Object createDummyType(Class<?> paramType) {
		if (String.class == paramType) {
			return "\"\"";
		} else if (int.class == paramType) {
			return 0;
		} else if (byte.class == paramType) {
			return "(byte)0";
		} else if (short.class== paramType) {
			return "(short)0";
		} else if (long.class == paramType) {
			return "0L";
		} else if (char.class == paramType) {
			return "'0'";
		} else if (float.class == paramType) {
			return "0F";
		} else if (boolean.class == paramType) {
			return "false";
		} else if (Integer.class == paramType) {
			return 0;
		} else if (Byte.class == paramType) {
			return "(byte)0";
		} else if (Short.class== paramType) {
			return "(short)0";
		} else if (Long.class == paramType) {
			return "0L";
		} else if (Character.class == paramType) {
			return "'0'";
		} else if (Float.class == paramType) {
			return "0F";
		} else if (Boolean.class == paramType) {
			return "false";
		} else if (BigDecimal.class == paramType) {
			return "new BigDecimal(0)";
		} else if (Date.class == paramType) {
			return "new Date()";
		} 
		return null;
	}
}
