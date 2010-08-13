package com.tan.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tan.util.GetURL;
import com.tan.util.ReadBook;

public final class ShowServlet extends HttpServlet{
	
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
		if (encoding == null || encoding.trim().length() == 0) {
			encoding = "gb2312";
		}
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		if (prefix != null && prefix.trim().length() > 0) {
			prefix = new String(request.getParameter("prefix").getBytes("ISO8859_1"), "utf-8");
			request.setAttribute("page_source", ReadBook.readBookByName(prefix));
			request.getRequestDispatcher("showPage.jsp").forward(request, response);
			return;
		}
		if (url != null && url.trim().length() != 0) {
			url = new String(request.getParameter("url").getBytes("ISO8859_1"), "utf-8");
			if (url.matches(".*[hH][tT]{2}[pP]://.*")) 
				response.getWriter().print(GetURL.getSourceA(url, encoding));
			else {
				String u = GetURL.checkUrl(url).toLowerCase();
				if (!hasSuffix(request, response, u)) {
					request.getRequestDispatcher("index.jsp").forward(request, response);
					return;
				}
				response.getWriter().print(GetURL.getSourceA(url, encoding));
			}
		}
		response.getWriter().print("<script type=\"text/javascript\">function _g_j(l){document.forms[document.forms.length -1].elements[0].value=l.href;document.forms[document.forms.length -1].submit();return false;}</script><form name='ta_n_f' action=\"show.do\" method=\"post\"><input type=\"hidden\" name=\"url\" id=\"ta_n_f_l\"/><input type=\"hidden\" name=\"encoding\" id=\"" + encoding+ "\" value=\"" + encoding+ "\"/></form></html>");
		response.flushBuffer();
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
