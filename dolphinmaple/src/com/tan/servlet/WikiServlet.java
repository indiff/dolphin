package com.tan.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Dolphin.
 *
 */
public final class WikiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String[] KEYS=  {};
	
	public final static String g(final String value) {
		int len = value.length();
		int code = random();
		StringBuilder b =  new StringBuilder();
		b.append("<A HREF=\"http://1.latest.dolphinmaple.appspot.com/wap?");
		b.append("code=");
		b.append(code);
		b.append("&site=1&");
		b.append("enc=");
		for (int i = 0; i <len; i++){
			b.append(value.charAt(i) ^ code);
			b.append(",");
		}
		b.deleteCharAt(b.length() - 1);
		b.append("\">" + value + "</A>");
		return b.toString();
   }
	
   public final static int random() {
	   String time = String.valueOf(new java.util.Date().getTime());
	   int random = 9;
	   for (int i = time.length() - 1; i >= 0; i--) {
		   random = (int) (time.charAt(i));
		   if (random != 0) {
			   return random - 48;
		   }
	   }
	   return random;
   }
	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String uri = req.getRequestURI();
		String type = req.getParameter("type");
		//"谢燕益={MOD}",.
		ServletOutputStream out = resp.getOutputStream();
		if (type != null && type.charAt(0) == '0') { // iterate the keys.
			for(int i = 0; i < KEYS.length; i++) {
				out.print(KEYS[i]);
			}
		}
		// Check the uri.
		else if (uri != null && uri.trim().length() > 0) {
			String wikiKey = "wiki/";
			String url;
			int len = wikiKey.length();
			int idx = uri.indexOf("wiki/");
			if (idx >= 0) {
				idx += len;
				url = uri.substring(idx);
				if (url != null) {
					url = URLEncoder.encode("http://zh.m.wikipedia.org/wiki?search=" + url.trim() , "utf-8");
					resp.sendRedirect("http://dolphinmaple.appspot.com/show.do?url=" + url); // Mobile.
//					resp.sendRedirect("http://dolphinmaple.appspot.com/show.do?url=http://zh.wikipedia.org/wiki/" + word);
				}
			}
		} else {
			out.println("No wiki information!");
		}
	}
	
	
	public static void main(String args[]) {
		for (String k : KEYS) {
//			System.out.println(g(k));
		}
		
		String demo = "东突";
		System.out.println(Integer.valueOf(String.valueOf((int)demo.charAt(0)), 16));
		System.out.println((int)demo.charAt(0));
	}
}
