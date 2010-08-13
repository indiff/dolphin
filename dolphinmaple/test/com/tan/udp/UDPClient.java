package com.tan.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Scanner;

/**
 * 
 * UPD 是一种不可靠的网络的通讯的方式,这里不可靠是从理论上讲的,从使用的角度看还是
 * 可靠的
 */
public class UDPClient {

	/**
	 * @param args
	 * @throws SocketException 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("------ Client ------");
		Scanner scanner = new Scanner(System.in);
		String content = scanner.next();
		byte[] buff = content.getBytes();
		DatagramPacket dp = new DatagramPacket(buff, 0, buff.length, new InetSocketAddress("127.0.0.1", 9999));
		DatagramSocket ds = new DatagramSocket();
		ds.send(dp);
		ds.close();
	}

}
