package com.tan.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Dolphin.
 *
 */
public final class DownloadUtil {
	/**
	 * return the download's file name
	 * @param url
	 * @param suffixs
	 * @return file's name
	 */
	public final static String getFilename(final String url,String ... suffixs) {
		if (url ==  null || url.trim().length() == 0) {
			return "tan";
		}
		int idx = url.lastIndexOf("/");
		String name = "" ;
		if (idx >= 0) {
			idx++;
			name = url.substring(idx);
		}
		StringBuffer result = new StringBuffer(name);
		suffixs = filterWhiteSpace(suffixs);
		if (suffixs != null && suffixs.length != 0) {
			for (String f : suffixs) {
				result.append('.' + f);
			}
			return result.toString();
		}
		return name;
	}
	
	/**
	 * replace the filters's white space
	 * @param filters
	 * @return the filters
	 */
	private final static String[] filterWhiteSpace(String[] filters) {
		if (filters == null || filters.length == 0 ) {
			return null;
		}
		for (int i = 0; i < filters.length; i++) {
			filters[i] = filters[i].trim();
		}
		return filters;
	}
	
	public final static void download(final String url, final String filename, final HttpServletResponse response) {
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
/*			if (so != null) {
				try {
					so.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				so = null;
			}*/
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
	
	public static void main(String args[]){
		System.out.println(getFilename("http://www.fdafdsafdsa.com/fdsafdsa/false.wmv", "filter   ", "   hello"));
		System.out.println(getFilename("false.wmv", "filter   ", "   hello"));
		System.out.println(getFilename("http://www.baidu.com/some-1-v.pdf", "wmv"));
		System.out.println(getFilename("http://www.baidu.com/some-2-v.pdf", "mp3"));
	}
}
