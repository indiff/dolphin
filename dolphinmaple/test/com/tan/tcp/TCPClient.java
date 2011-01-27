//package com.tan.tcp;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.InetAddress;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.PaintEvent;
//import org.eclipse.swt.events.PaintListener;
//import org.eclipse.swt.graphics.Rectangle;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Shell;
//
//public class TCPClient extends Shell {
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) throws Exception {
//		init();
//
//		/*
//		 * SWTError 不能修复的错误,操作系统的错误
//		 * SWTException 可以恢复的错误以及无效的线程访问之类的错误
//		 * IllegalArgumentException 可恢复错误或者参数为null
//		 * */
//	}
//
//	private static void init() {
//		Display display = new Display();
//		// display 是一个顶层的容器组件, Container 或者 Component 的功能，主要负责与顶层的窗口系统之间的连接。代表 "屏幕"
//		/*
//		 * 	一个 Display 可以包含多个 Shell 
//		 *  而一个程序只含有一个 Display, 通常是一个单例的组件 (Singleton)
//		 * */
//		Shell shell = new Shell(display);
//		/*
//		 * 标识位于屏幕上面的 窗口,是 Composite 组件 和 Control 组建构成的组建树的根
//		 * */
//		/*
//		 * Composite 可以包含其他的 Composite 和 Control 容器
//		 * Control 一个重量级 (HeavyWeight) 系统对象
//		 * Button, Label ,表格，工具栏，树形结构组件都是 Control 子类
//		 * */
//		shell.setText("Hello World");
//		shell.addPaintListener(new PaintListener() {
//			public void paintControl(PaintEvent e) {
//				Shell shell = (Shell) e.getSource();
//				Display display = shell.getDisplay();
//				Rectangle clientArea = shell.getClientArea();
//				int width = clientArea.width;
//				int height = clientArea.height;
////				e.gc.setClipping(20, 20, width - 40, height - 40);
//				e.gc.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
//				e.gc.fillPolygon(new int[] { 0, 0, width, 0, width / 2,
//								height });
//			}
//		});
//
//		shell.setSize(150, 150);
//
//		shell.open();
//
//		/*
//		 * 消息循环代码: Swing 中主要使用事件驱动模型而不这样利用类似于 Windows 程序设计中的
//		 * 消息循环的方法来处理事件
//		 * 
//		 * 反复读取和分派 (dispatch) 事件,没有事件时候把控制权给 CPU
//		 * */
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch())
//				display.sleep();
//		}
//
//		/*
//		 * 创建了资源，需要对资源释放
//		 * 释放父组件资源的同时也释放了子组件的资源
//		 * */
//		display.dispose();
//	}
//
//	@SuppressWarnings("unused")
//	private static void sendMessage() throws IOException, UnknownHostException {
//		Socket socket = new Socket(InetAddress.getByName("localhost"), 8888);
//		OutputStream out = socket.getOutputStream();
//
//		out.write("Hello ".getBytes());
//		out.close();
//		socket.close();
//	}
//
//}
