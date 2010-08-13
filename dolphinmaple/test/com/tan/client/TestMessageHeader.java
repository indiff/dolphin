package com.tan.client;

import java.net.URL;

import sun.net.www.MessageHeader;

public class TestMessageHeader {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		MessageHeader header = new MessageHeader(new URL("http://www.baidu.com").openStream());
		
		System.out.println(header);
	}

}
