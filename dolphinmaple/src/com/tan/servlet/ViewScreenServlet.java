package com.tan.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tan.util.ScreenCapture;

public final class ViewScreenServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	public final void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		this.doPost(request, response);
	}
	
	@Override
	public final void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String u = request.getParameter("username");
		String p = request.getParameter("password");
		if (isEmpty(u) || isEmpty(p) || (!"dolphin".equalsIgnoreCase(u.trim()) && !"includemain".equalsIgnoreCase(p.trim()))) {
			request.setAttribute("error", "your username or your password is wrong!");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		} else if ("dolphin".equalsIgnoreCase(u.trim()) && "includemain".equalsIgnoreCase(p.trim())){
			request.setAttribute("role", "_tAn");
			String e = ScreenCapture.createScreenCapture();
			request.setAttribute("exception", e);
			request.getRequestDispatcher("view.jsp").forward(request, response);
		}
	}
	
	private final boolean isEmpty(final String v) {
		if (v == null || v.trim().length() == 0) {
			return true;
		}
		return false;
	}
}
