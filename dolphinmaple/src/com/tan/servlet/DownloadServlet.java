package com.tan.servlet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.tan.util.Download;

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
		if (!isEmpty(url) && filters == null) {
			download(url, filename, response);
		}else if (isEmpty(url) || filename == null) {
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
	
	private final void download(String url, String filename, HttpServletResponse response) {
		response.setContentType("application/octet-stream;charset=ISO8859-1");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		ServletOutputStream so = null;
		BufferedInputStream bi = null;
		try {
			so =  response.getOutputStream();
//			URL u = new URL(url);
//			URLConnection connection = u.openConnection();
//			connection.addRequestProperty("", "");
//			String contentEncoding = connection.getContentEncoding();
//			Object content = connection.getContent();
//			String contentType = connection.getContentType();
//			int contentLength = connection.getContentLength();
//			InputStream in = connection.getInputStream();
//			System.out.println(
//					"contentEncoding:\t" + contentEncoding + "\r\n" + 
//					"content:\t" + content + "\r\n" + 
//					"contentType:\t" + contentType + "\r\n" + 
//					"in:\t" + in + "\r\n" + 
//					"contentLength:\t" + contentLength + "\r\n" 
//					);
			bi = new BufferedInputStream(new URL(url).openStream());
			int l ;
			byte[] buf = new byte[4096];
			while ( (l = bi.read(buf, 0, buf.length)) != -1) {
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
			}
			if (bi != null) {
				try {
					bi.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
