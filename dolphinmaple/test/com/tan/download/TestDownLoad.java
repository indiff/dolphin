package com.tan.download;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class TestDownLoad {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		int startPosition = 0;
		int endPosition = 101;
		
		URL url = new URL("http://localhost:88/base/download.txt");
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		setHeader(con);
		con.setAllowUserInteraction(true);
		// ����l�连接超时 10000ms
		con.setConnectTimeout(10000);
		// ���ö�ȡ��ݳ�ʱʱ��读取超时 10000ms
		con.setReadTimeout(10000);
		
//		con.setRequestProperty("Range", "bytes=" + startPosition + "-"
//				+ endPosition);
		con.setRequestProperty("Range", "bytes=" + startPosition + "-" + endPosition);
		System.out.println("start position  "
				+ startPosition);
		System.out.println("end position " + endPosition);

		
		System.out.println("Content-Length : " + con.getContentLength());
		System.out.println("Content-Type : " + con.getContentType());
		System.out.println("ReponseCode : " + con.getResponseCode());
//		System.out.println("HeaderFields : " + con.getContentLength());
		InputStream in = con.getInputStream();
		FileOutputStream fileOutputStream = new FileOutputStream("c:\\t\\one.txt");
		byte[] buf = new byte[2046];
		int len = -1;
		while ( (len = in.read(buf, 0, buf.length)) != -1) {
			fileOutputStream.write(buf, 0, len);
		}
		fileOutputStream.flush();
		in.close();
		fileOutputStream.close();
		System.out.println("Download successful");
	}
	
	
	private static  void setHeader(URLConnection con) {
				con.setRequestProperty(
								"User-Agent",
								"Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.3) Gecko/2008092510 Ubuntu/8.04 (hardy) Firefox/3.0.3");
				con.setRequestProperty("Accept-Language", "en-us,en;q=0.7,zh-cn;q=0.3");
				con.setRequestProperty("Accept-Encoding", "aa");
				con.setRequestProperty("Accept-Charset",
						"ISO-8859-1,utf-8;q=0.7,*;q=0.7");
				con.setRequestProperty("Keep-Alive", "300");
				con.setRequestProperty("Connection", "keep-alive");
				con.setRequestProperty("If-Modified-Since",
						"Fri, 02 Jan 2009 17:00:05 GMT");
				con.setRequestProperty("If-None-Match", "\"1261d8-4290-df64d224\"");
				con.setRequestProperty("Cache-Control", "max-age=0");
				con.setRequestProperty("Referer",
						"http://www.skycn.com/soft/14857.html");
	}
}

class Downloader extends Thread{
	
	private InputStream in;
	private OutputStream out;
	
	public Downloader(InputStream in , OutputStream out) {
		this.in = in;
		this.out = out;
	}
	
	public void run() {
		if (in == null || out == null) {
			System.out.println("The streams is null!");
			return;
		} else {
			int len = -1;
			byte[] buf = new byte[2046];
			try {
				while ((len = in.read(buf, 0, buf.length)) != -1) {
					out.write(buf, 0, len);
				}
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
