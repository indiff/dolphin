package com.tan.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tan.util.Download;

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
		String filename = Download.getFilename(url, filters);
		if (isEmpty(url) || filename == null) {
			request.getRequestDispatcher("error.jsp").forward(request, response);
		} else {
			download(url, filename, response) ;
		}
	}
	
	private final boolean isEmpty(final String value) {
		if (value == null || value.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
	private final void download(final String url, final String filename, final HttpServletResponse response) {
		response.reset();
		response.setContentType("application/octet-stream;charset=ISO8859-1");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		ServletOutputStream so = null;
		InputStream bi = null;
		int l;
		int bufSize = 4096;
		byte[] buf = new byte[bufSize];
		try {
			so =  response.getOutputStream();
			bi = new URL(url).openStream();
			while (-1 != (l = bi.read(buf, 0, bufSize))) { // while not at the end of the stream.
				so.write(buf, 0, l);
			}
		} catch (IOException e) {
			response.reset();
			response.setStatus(500);
		} finally {
			if (so != null) {
				try {
					so.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				so = null;
			}
			if (bi != null) {
				try {
					bi.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				bi = null;
			}
		}
	}
}
