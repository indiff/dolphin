package com.tan.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tan.util.ReadBook;

public final class BookMarkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {
		this.doPost(request, response);
	}
	@Override
	protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {
		String u = request.getParameter("username");
		String p = request.getParameter("password");
		if (isEmpty(u) || isEmpty(p) || (!"dolphin".equalsIgnoreCase(u.trim()) && !"includemain".equalsIgnoreCase(p.trim()))) {
			request.setAttribute("error", "your username or your password is wrong!");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		} else if ("dolphin".equalsIgnoreCase(u.trim()) && "includemain".equalsIgnoreCase(p.trim())){
			putCookies(response);
			request.setAttribute("role", "_tAn");
			request.setAttribute("page_source", ReadBook.readBookByIO());
			request.getSession().setAttribute("role", "dolphin");
			request.getSession().setAttribute("govern", "dolphin");
			request.getRequestDispatcher("showPage.jsp").forward(request, response);
		}
	}
	
	private void putCookies(HttpServletResponse response) {
		Cookie u_cookie = new Cookie("username", "dolphin");
		u_cookie.setMaxAge(3600);
		Cookie u_password = new Cookie("password", "includemain");
		u_password.setMaxAge(3600);
		response.addCookie(u_cookie);
		response.addCookie(u_password);
	}
	
	private final boolean isEmpty(final String v) {
		if (v == null || v.trim().length() == 0) {
			return true;
		}
		return false;
	}
	/**
	 * MD5算法
	 */
	public static byte[] getPass(String password) {
		byte[] rs = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			try {
				digest.update(password.getBytes("iso8859-1"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			rs = digest.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(new String(getPass("1"), "iso8859-1"));
	}
	
	
}
