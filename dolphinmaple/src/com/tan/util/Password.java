package com.tan.util;

public class Password {
	private final static String hexDigits[] = { 	
		"0", "1", "2", "3", "4", "5", "6", "7", 
		"8", "9", "a", "b", "c", "d", "e"
	};
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(toHexString((byte) 19));
	}
	
	public static String toHexString(byte b) {
		int i = (int) b;
		if ( i < 0) i = 256 + i;
		int d1 = i / 16;
		int d2 = i % 16;
		System.out.println("d1 --> " + d1);
		System.out.println("d2 --> " + d2);
		return  hexDigits[d1] + hexDigits[d2] ;
	}
}
