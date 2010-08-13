package com.tan.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/**
 *  过滤器,过滤字符内容
 */
public final class EncodingFilter implements Filter {
	private String encoding;

	public final void destroy() {
		encoding = null;
	}

	public final void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		if (encoding != null) {
			req.setCharacterEncoding(encoding);
		}
		chain.doFilter(req, res);
	}

	public final void init(FilterConfig config) throws ServletException {
		encoding = config.getInitParameter("encoding");
	}

}
