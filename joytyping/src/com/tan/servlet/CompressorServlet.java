package com.tan.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tan.util.Compressor;

public class CompressorServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String u = req.getParameter("u");
		String url = parse(u);
		if (url != null) {
			String encodedURL = URLEncoder.encode(url, "utf-8");
			URL googleURL = new URL("http://dolphinmaple.appspot.com/compress?u=" + encodedURL);
			
			URLConnection conn = googleURL.openConnection();
			
			String encoding = conn.getContentEncoding();
			
			InputStream in = conn.getInputStream();
			
			Compressor compressor = new Compressor();
			byte[] buf = new byte[8192];
			byte[] results = new byte[4096];
			int len = -1;
			int inflateLen = -1;
			StringBuffer b = new StringBuffer();
			while( (len = in.read(buf)) != -1) {
				inflateLen = 
					compressor.inflate(buf, 0, len, results);
				b.append(new String(results, 0, inflateLen));
			}
//			while( (len = in.read(buf)) != -1) {
//				byte[] decompressed = 
//					compressor.decompress(buf, 0, len);
//				b.append(new String(decompressed,0, decompressed.length, encoding));
//			}
			
			in.close();
			
			PrintWriter writer = resp.getWriter();
			if (b.length() > 0) {
				resp.reset();
				writer.print(b);
				writer.flush();
				resp.flushBuffer();
				writer.close();
			} else {
				writer.print("<font color=\"red\">no data</font>");
			}
		}
	}

	private String parse(String u) {
		if (u != null) {
			u = u.trim();
			if (u.length() != 0) {
				u = u.toLowerCase();
				if (!u.startsWith("http://")) {
					return "http://" + u;
				} else {
					return u;
				}
			}
		}
		return null;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
