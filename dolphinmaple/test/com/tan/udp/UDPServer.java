package com.tan.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * 
 * UPD 是一种不可靠的网络的通讯的方式,这里不可靠是从理论上讲的,从使用的角度看还是
 * 可靠的
 */
public class UDPServer {

	/**
	 * @param args
	 * @throws SocketException 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("------ Server ------");
		byte[] buff = new byte[2046];
		// 创建一个数据包,用来服务端接受到的数据
		DatagramPacket dp = new DatagramPacket(buff, 0, buff.length);
		// 创建一个绑定本机的 9999 端口的数据报套接字
		DatagramSocket ds = new DatagramSocket(9999);
		while (true) {
			// 阻塞式的接受一个数据包
			ds.receive(dp);
			dp.getLength();
			System.out.println(new String(buff, 0, buff.length));
		}
	}

}
