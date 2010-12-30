package com.tan.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ShowServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	public final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	@Override
	public final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getParameter("url");
		//String prefix = request.getParameter("prefix");
		String encoding = request.getParameter("encoding");
		ServletOutputStream out = response.getOutputStream();
		/**
		 * 1. string url format.
		 */
		// check for url.
		if (null == url || url.trim().length() == 0) {
			out.write("<h1 color='red'>URL is blank!</h1><a href='./index.jsp'>HOME</a>".getBytes());
			return;
		}
		
		// check url's suffix.
		if ( url.endsWith(".mp3") ||
				url.endsWith(".rmvb") ||
				url.endsWith(".rm") ||
				url.endsWith(".mp4") ||
				url.endsWith(".rar") ||
				url.endsWith(".zip") ||
				url.endsWith(".7z") ||
				url.endsWith(".wma") ||
				url.endsWith(".torrent") ||
				url.endsWith(".avi") ||
				url.endsWith(".asf") ||
				url.endsWith(".wmv")
			) {
			out.write(("<h1 color='red'>Please Download The " + url + "</h1><BR>Suffix can not be mp3,rmvb,rm,mp4,rar,zip,7z,wma,torrent,avi,asf<BR><a href='./index.jsp'>HOME</a>").getBytes());
			return;
		}
		// check url's preffix.
		url = url.toLowerCase();
		if (!url.startsWith("http://")) {
			url = "http://" + url;
		}
		
		/**
		 * 2. Encoding.
		 */		
		// check for encoding.
		if (encoding == null || encoding.trim().length() == 0) {
			// set the default encoding.
			encoding = "gb2312";
		}
		
		// check for prefix.
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		response.reset();
		URL u = null;
		try {
			u = new URL(url);
		} catch (MalformedURLException e) {
			out.write("<h1 color='red'>URL is blank!</h1><a href='./index.jsp'>HOME</a>".getBytes());
		}
		// connection.
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		int length  = conn.getContentLength();
		String contentType = conn.getContentType();
		
		conn.setConnectTimeout(10 * 1000); // Time out 10 seconds.
		
		
		// set response's header information.
		response.setContentType(contentType);
		response.setContentLength(length);
		
		InputStream in = conn.getInputStream();
		
		if (null != in) {
			byte[] buf = new byte[2048]; // Buf.
			int len = -1;
			while (-1 != (len = in.read(buf, 0, 2048)) ) {
				out.write(buf, 0, len);
			}
			conn.disconnect();
			out.flush();
			out.close();
			in.close();
			// Finished.
			in = null;
			out = null;
			u = null;
			conn = null;
			buf = null;
		}
		
		// Debug the information of the connection.
		//out.write("Length\t" + length + "<br>Encoding\t" + contentEncoding + "<br>type\t" + contentType+ "<a href='./index.jsp'>Home</a>");
	}

	/*private final boolean hasSuffix(HttpServletRequest request,
			HttpServletResponse response, String u) throws ServletException,
			IOException {
		if (!u.endsWith(".com") && !u.endsWith(".cn")
				&& !u.endsWith(".net") && !u.endsWith(".org")
				&& !u.endsWith(".cc") && !u.endsWith(".edu")
				&& !u.endsWith(".gov") && !u.endsWith(".int")
				&& !u.endsWith(".mil") && !u.endsWith(".biz")
				&& !u.endsWith(".info") && !u.endsWith(".pro")
				&& !u.endsWith(".name") && !u.endsWith(".museum")
				&& !u.endsWith(".coop") && !u.endsWith(".aero")
				&& !u.endsWith(".hk") && !u.endsWith(".xx")
				&& !u.endsWith(".in") && !u.endsWith(".jp")
				&& !u.endsWith(".kp") && !u.endsWith(".mo")
				&& !u.endsWith(".us") && !u.endsWith(".uk")
				&& !u.endsWith(".kr")) {
			request.setAttribute("error", "Your suffix name is wrong!!!");
			return false;
		}
		return true;
	}*/
}
