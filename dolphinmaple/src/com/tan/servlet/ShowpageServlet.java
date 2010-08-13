package com.tan.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tan.util.GetURL;
import com.tan.util.ReadBook;

public final class ShowpageServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	public final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	@Override
	public final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getParameter("url");
		String prefix = request.getParameter("prefix");
		String encoding = request.getParameter("encoding");
		if (prefix != null && prefix.trim().length() > 0) {
			prefix = new String(request.getParameter("prefix").getBytes("ISO8859_1"), "utf-8");
			request.setAttribute("page_source", ReadBook.readBookByName(prefix));
			request.getRequestDispatcher("showPage.jsp").forward(request, response);
			return;
		}
		if (url != null && url.trim().length() != 0) {
			url = new String(request.getParameter("url").getBytes("ISO8859_1"), "utf-8");
			if (url.matches(".*[hH][tT]{2}[pP]://.*")) request.setAttribute("page_source", 
					GetURL.getSourceA(url, encoding).replaceAll("null", ""));
			else {
				String u = GetURL.checkUrl(url).toLowerCase();
				if (!hasSuffix(request, response, u)) {
					request.getRequestDispatcher("index.jsp").forward(request, response);
					return;
				}
				request.setAttribute("page_source", GetURL.getSourceA(url, encoding));
			}
		}
		request.getRequestDispatcher("showPage.jsp").forward(request, response);
	}

	private final boolean hasSuffix(HttpServletRequest request,
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
	}
}
