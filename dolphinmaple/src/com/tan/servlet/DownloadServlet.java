package com.tan.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tan.util.DownloadUtil;
import com.tan.util.StringUtil;

/**
 * Download the url, if with suffix.
 * @author Administrator
 *
 */
public final class DownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected final void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected final void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getParameter("url");
		String[] filters = request.getParameterValues("downloadSuffix");
		String filename = DownloadUtil.getFilename(url, filters);
		if (StringUtil.isEmpty(url) || filename == null) {
			request.getRequestDispatcher("error.jsp").forward(request, response);
		} else {
			DownloadUtil.download(url, filename, response) ;
		}
	}
}
