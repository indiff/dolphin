package com.tan.util;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ScreenCapture {
	public static void main(String[] args) throws Exception {
//		Robot r = new Robot();
//		Rectangle rect = new Rectangle(0, 0, 1280, 1024);
//		BufferedImage bimg = r.createScreenCapture(rect);
//		FileOutputStream fo = new FileOutputStream("a.jpeg");
//		JPEGImageEncoder jer = JPEGCodec.createJPEGEncoder(fo);
//		jer.encode(bimg);
//		fo.close();
		createScreenCapture();
	}
	
	public static String createScreenCapture() {
		OutputStream os = null;
		String path = ScreenCapture.class.getResource("/").getFile();
		try {
			Robot robot = new Robot();
			Rectangle rect = new Rectangle(0, 0, 1028, 1024);
			BufferedImage bimg = robot.createScreenCapture(rect);
			os = new FileOutputStream(
					new StringBuilder().append(path.substring(0, path.lastIndexOf("WEB-INF")))
					.append("Picture").append(File.separator).append("tmp.jpg").toString());
			JPEGImageEncoder jer = JPEGCodec.createJPEGEncoder(os);
			jer.encode(bimg);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "successful!!";
	}
}
