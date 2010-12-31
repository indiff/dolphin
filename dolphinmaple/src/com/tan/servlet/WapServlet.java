package com.tan.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Dolphin.
 * 
 */
public final class WapServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
/*	static {
		try {
			PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(InetAddress.getByName("proxy2.zte.com.cn"), 80));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	private static Proxy PROXY ;*/
	
	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String site = req.getParameter("site");
		String word = req.getParameter("word");
		String code = req.getParameter("code");
		String enc = req.getParameter("enc");
		ServletOutputStream out = resp.getOutputStream();
		//req.setCharacterEncoding("utf-8");
		// Check site.
		if (site == null) {
			out.print("<h1>Please select the site.</h1><a href=w.jsp>Home</a>");return;
		}//end for site.
		
		// Check word.
		if (word != null  && word.trim().length() == 0) {
			out.print("<h1>The word is blank.</h1>");return; 
		} else {
			// decrypt the word with the enc and code.
			if (null != code && null != enc) {
				word = decrypt(enc, code);
			}
			word = URLEncoder.encode(word.trim(), "utf-8");
		}//end for word.
		
		// Get the site type.
		char s = site.charAt(0);
		
		// Check the site type.
		if ('0' == s) { // Baidu.
			resp.sendRedirect("http://3g.baidu.com/s?word=" + word);
		}
		else if ('1' == s) { // Wiki.
			
			/**
			 * 方法一: send redirect.
			 */
			//resp.sendRedirect("http://dolphinmaple.appspot.com/show.do?url=" + URLEncoder.encode("http://zh.m.wikipedia.org/wiki?search=", "utf-8") + word);
			
			/**
			 * Process the wiki's request.
			 */
///////////////////////////////////////////////////////// Process the wiki for wap servlet end.
			resp.reset();
			String u = "http://zh.m.wikipedia.org/wiki?search=" + word;
			String userAgent = getUserAgent(req, "User-Agent");
			URL url = new URL(u);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // use PROXY.
			String type = conn.getContentType();
			String encoding = conn.getContentEncoding();
			// parse the encoding.
			if (null == encoding) {
				encoding = getEncodingByContentType(type);
			}
			InputStream in = null;
			byte[] buf = null;
			int len = -1,bufSize = 2046;
			buf = new byte[bufSize];
			
			// Get the input stream.
			in = conn.getInputStream();
			if (null != in) {
				resp.setContentType(type);
				String title = null;
				boolean changedTitle = false;
				int titleStart,titleEnd;
				while (-1 != (len = in.read(buf, 0, bufSize))) {
					if (changedTitle) {
						out.write(buf, 0, len);
					} else {
						// change the title.
						title =  new String(buf, 0, len, encoding);
						if ((titleStart = title.indexOf("<title>")) >= 0 
								&& (titleEnd = title.indexOf("</title>")) >= 0) {
							title = title.substring(0, titleStart + "<title>".length()) + (enc == null ? enc : word) + title.substring(titleEnd);
							changedTitle = true;
							out.write(title.getBytes(encoding));
						}
					}
				}
				// Add the javascript for change the title. When the agent is IE or firefox.
				if (null != userAgent && (userAgent.indexOf("firefox") >= 0 || userAgent.indexOf("msie") >= 0)) {
					out.print(generateChangeTitleJs());
				}
				
				// finish.
				out.flush();
				in.close();
				in = null;
				title = null;
			}
			
			conn.disconnect();
			buf = null;
			conn = null;
			type = null;
			url = null;
			u = null;
			
///////////////////////////////////////////////////////// Process the wiki for wap servlet end.
		} else if ('2' == s) { // Google.
			resp.sendRedirect("http://www.google.com/m?gl=cn&source=ihp&hl=zh_cn&q=" + word);
		}
	}
	
	/**
	 * Get the header information from the request.
	 * @param req
	 * @return
	 */
	private String getUserAgent(HttpServletRequest req, String header) {
		String result = req.getHeader(header);
		if (result != null) {
			return result.toLowerCase();
		}
		return null;
	}
	
	/**
	 * Generate the change title js.
	 * @return
	 */
	private String generateChangeTitleJs() {
		String functionName = "______________title" + new Date().getTime() + "()";
		String flg = "________flg" + new Date().getTime();
		StringBuilder builder = new StringBuilder();
		builder.append("<script>var ")
			   .append(flg)
			   .append(" = true;function ")
			   .append(functionName)
			   .append(" {document.title = ")
			   .append(flg)
			   .append("?decodeURIComponent(document.title):encodeURIComponent(document.title);")
			   .append(flg)
			   .append("=!")
			   .append(flg)
			   .append(";}</script><input type=\"button\" value=\"Title\" onclick=\"")
			   .append(functionName)
			   .append(";\" style=\"position:absolute;left:0px;top:0px;height:20px;width:230px;\"/>");
		return  builder.toString();
	}
	
	/**
	 * Get the encoding from the content-type.
	 * @param type
	 * @return
	 */
	private static String getEncodingByContentType(String type) {
		if (type != null) {type=type.toLowerCase();}
		if (type.indexOf("utf-8") >= 0) {return "utf-8";}
		if (type.indexOf("gbk") >= 0) {return "gbk";}
		if (type.indexOf("gb2312") >= 0) {return "gb2312";}
		if (type.indexOf("gb18030") >= 0) {return "gb18030";}
		if (type.indexOf("big5") >= 0) {return "big5";}
		if (type.indexOf("shift-jis") >= 0 || type.indexOf("shift-jis") >= 0) {return "shift-jis";}
		if (type.indexOf("ms932") >= 0) {return "ms932";}
		return "utf-8";
	}
	
	/**
	 * Decrypt the encryptation by the ^.
	 * @param encryptation
	 * @param code
	 * @return
	 */
	private static String decrypt(String encryptation, String code) {
		if (null == code) {
			return encryptation;
		}
		int random = Integer.parseInt(code);
		String[] results = split(encryptation, ",");
		if (null == results || results.length == 0) { return encryptation;}
		int data;
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < results.length; i++) {
			data = Integer.parseInt(results[i]);
			data = data ^ random;
			buf.append((char) data);
		}
		return buf.toString();
	}
	
	/**
	 * Split the string , which had the comma ",".
	 * For example : "1234,232,454" --> new String[]{[1234,232,454];}
	 * @param string
	 * @param keyword
	 * @return
	 */
	static String[] split(final String string, final String keyword) {
		// Check string.
		if (null == string || null == keyword){
			return null;
		}
		// Check contains comma ",".
		if (string.indexOf(keyword) < 0) {
			return new String[]{string};
		}
		int last = 0,index;
		ArrayList<String> results = new ArrayList<String>();
		while (-1 != (index = string.indexOf(keyword, last))){
			results.add(string.substring(last, index));
			last = index + keyword.length();
		}
		if (string.length() - 1 != last) {
			results.add(string.substring(last));
		}
		return (String[]) (results.toArray(new String[results.size()]));
	}
	
	public static void main(String[] args) {
		String value = "1212,34,54,5,7,8";
		int index = value.lastIndexOf(",");
		System.out.println(index);
		System.out.println(value.length());
		System.out.println(value.substring(index + ",".length()));
	}
}
