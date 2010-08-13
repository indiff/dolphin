package com.tan.tcp;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		ServerSocket serverSocket = new ServerSocket(8888);
		InputStream in = null;
		
		while (true) {
			Socket clientSocket = serverSocket.accept();
			in = clientSocket.getInputStream();
			byte[] bs = new byte[2046];
			int len = -1;
			while ((len = in.read(bs, 0, bs.length)) != -1) {
				System.out.println(new String(bs, 0, len));
			}
		}
		
	}

}
