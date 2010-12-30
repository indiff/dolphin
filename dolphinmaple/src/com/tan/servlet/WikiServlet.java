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
// * @author Dolphin.
 *
 */
public final class WikiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String uri = req.getRequestURI();
		ServletOutputStream out = resp.getOutputStream();
		// Check the uri.
		if (uri != null && uri.trim().length() > 0) {
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
}
