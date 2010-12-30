package com.tan.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
// * @author Dolphin.
 *
 */
public final class WapServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String site = req.getParameter("site");
		String word = req.getParameter("word");
		String code = req.getParameter("code");
		String enc = req.getParameter("enc");
		ServletOutputStream out = resp.getOutputStream();
		//req.setCharacterEncoding("utf-8");
		// Check site.
		if (site == null) {
			out.print("<h1>Please select the site.</h1>");return;
		}//end for site.
		
		// Check word.
		if (word != null  && word.trim().length() == 0) {
			out.print("<h1>The word is blank.</h1>");return; 
		} else {
			// decrypt the word with the enc and code.
			if (null != code && null != enc) {
				word = decrypt(enc, code);
			}
			word = URLEncoder.encode(word.trim(), "utf-8");
		}//end for word.
		
		// Get the site type.
		char s = site.charAt(0);
		
		// Check the site type.
		if ('0' == s) { // Baidu.
			resp.sendRedirect("http://3g.baidu.com/s?word=" + word);
		}
		else if ('1' == s) { // Wiki.
			resp.sendRedirect("http://dolphinmaple.appspot.com/show.do?url=" + URLEncoder.encode("http://zh.m.wikipedia.org/wiki?search=", "utf-8") + word);
		}
		else if ('2' == s) { // Google.
			resp.sendRedirect("http://www.google.com/m?gl=cn&source=ihp&hl=zh_cn&q=" + word);
		}
	}

	private static String decrypt(String encryptation, String code) {
		if (null == code) {
			return encryptation;
		}
		int random = Integer.parseInt(code);
		String[] results = split(encryptation, ",");
		if (null == results || results.length == 0) { return encryptation;}
		int data;
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < results.length; i++) {
			data = Integer.parseInt(results[i]);
			data = data ^ random;
			buf.append((char) data);
		}
		return buf.toString();
	}
	
	static String[] split(final String string, final String keyword) {
		// Check string.
		if (null == string || null == keyword){
			return null;
		}
		// Check contains comma ",".
		if (string.indexOf(keyword) < 0) {
			return new String[]{string};
		}
		int last = 0,index;
		ArrayList<String> results = new ArrayList<String>();
		while (-1 != (index = string.indexOf(keyword, last))){
			results.add(string.substring(last, index));
			last = index + keyword.length();
		}
		if (string.length() - 1 != last) {
			results.add(string.substring(last));
		}
		return (String[]) (results.toArray(new String[results.size()]));
	}
	
	public static void main(String[] args) {
		String value = "1212,34,54,5,7,8";
		int index = value.lastIndexOf(",");
		System.out.println(index);
		System.out.println(value.length());
		System.out.println(value.substring(index + ",".length()));
	}
}
