package com.tan.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tan.util.Site2Ip;

public final class ParseSiteServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	public final void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		this.doPost(request, response);
	}
	
	@Override
	public final void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String url = request.getParameter("url");
		String encoding = request.getParameter("encoding");
		if (encoding != null) {
			encoding = encoding.trim();
		}
		if (!isEmpty(url)) {
			Site2Ip.clear();
			request.setAttribute("page_source", Site2Ip.parseSite2Ip(url, encoding));
			request.getRequestDispatcher("showPage.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}
	
	private final boolean isEmpty(final String v) {
		if (v == null || v.trim().length() == 0) {
			return true;
		}
		return false;
	}
}
