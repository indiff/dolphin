package com.tan.util;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GetLinks {
	
	
	private String pagelink ;
	private String encoding ;
	
	private StringBuilder result ;
	
	private StringBuilder content;
	
	private final static String LINE = System.getProperty("line.separator");
	
	public GetLinks(String pagelink) {
		this.pagelink = pagelink;
		content = new StringBuilder();
	}
	
	public GetLinks(String link, String encoding) {
		this.pagelink = link;
		this.encoding = encoding;
		content = new StringBuilder();
	}
	
	public String getContent() {
		return content.toString();
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// String url = "http://localhost:8080/show/images.jsp";
//		String url = "http://www.qq.com.cn";
//		System.out.println(new GetLinks(url).dumpHrefs());
		
		String[] suffixs = { "gif", "jpg", "wmv", "rm", "mp3", "wma", "zip",
				"rar", "torrent", "avi", "asf", "rmvb", "html"};
		StringBuilder suffix = new StringBuilder();
		suffix.append('(');
		for(String s : suffixs) {
			suffix.append("\\.").append(s).append('|');
		}
		suffix.append(')');
		suffix.deleteCharAt(suffix.length() - 2);
		
		System.out.println(suffix);
		
		System.out.println(new GetLinks("http://www.qq.com").dump(suffixs));
	}
	
	
	public String dump(String[] suffixs) throws MalformedURLException,
			IOException {
		result = url2string(this.pagelink);
		StringBuilder pattern = new StringBuilder();
		for(String s : suffixs) {
			pattern.append("\\.").append(s).append('|');
		}
		pattern.deleteCharAt(pattern.length() - 1);
		
//		Pattern p1 = Pattern.compile("href|src=[\"\']?([a-z0-9_#?{}:/\\.]+" + p + ")[\"\']?", Pattern.CASE_INSENSITIVE);
		Pattern p1 = Pattern.compile("(href|src)=[\"\']?([a-z0-9_#?{}:/\\.]+(" + pattern.toString() + "))[\"\']?", Pattern.CASE_INSENSITIVE);

		Matcher m = p1.matcher(result);

		int index = pagelink.indexOf("/", 7);
		// 插入的字符串
		
		String insert = "";
		if (index > 0 && pagelink.indexOf("/", index) > 0){
			insert = pagelink.substring(0, index + 1);
		} else {
			insert = pagelink;
		}

		while (m.find()) {
			String buf = m.group(2);
			StringBuilder demo = new StringBuilder(buf == null ? " " : buf);
			if (demo.charAt(0) == '/'){
				demo.insert(0, insert);
			} else if (demo.charAt(0) == '.' && demo.charAt(1) == '.'){
				demo.delete(0, 3);
				demo.insert(0, insert);
			} else if (demo.charAt(0) == '.' && demo.charAt(1) != '.'){
				demo.deleteCharAt(0);
				demo.insert(0, insert);
			} else if (demo.indexOf("http") != 0){
				demo.insert(0, insert);
			}  
			content.append(demo).append(LINE);
		} 
		return content.toString();
	}	

	private String dumpHrefs() throws MalformedURLException,
			IOException {
		result = url2string(this.pagelink);
		
		Pattern p1 = Pattern.compile("href=[\"\']?([a-z0-9_#?{}:/\\.]+)[\"\']?", Pattern.CASE_INSENSITIVE);

		
		Matcher m = p1.matcher(result);

		int index = pagelink.indexOf("/", 7);
		// 插入的字符串
		String insert = "";
		if (index > 0 && pagelink.indexOf("/", index) > 0){
			insert = pagelink.substring(0, index + 1);
		} else {
			insert = pagelink;
		}

		while (m.find()) {
			StringBuilder demo = new StringBuilder(m.group(1));
			if (demo.charAt(0) == '/'){
				demo.insert(0, insert);
			} else if (demo.charAt(0) == '.' && demo.charAt(1) == '.'){
				demo.delete(0, 3);
				demo.insert(0, insert);
			} else if (demo.charAt(0) == '.' && demo.charAt(1) != '.'){
				demo.deleteCharAt(0);
				demo.insert(0, insert);
			} else if (demo.indexOf("http") != 0){
				demo.insert(0, insert);
			}  
			content.append(demo).append(LINE);
		} 
		return content.toString();
	}


	private String dumpJsps0() throws MalformedURLException,
			IOException {
		result = url2string(this.pagelink);
		
		Pattern p1 = Pattern.compile("href=[\"\']?([a-z0-9_#?{}:/\\.]+\\.jsp)[\"\']?", Pattern.CASE_INSENSITIVE);
		
		Matcher m = p1.matcher(result);

		int index = pagelink.indexOf("/", 7);
		// 插入的字符串
		String insert = "";
		if (index > 0 && pagelink.indexOf("/", index) > 0){
			insert = pagelink.substring(0, index + 1);
		} else {
			insert = pagelink;
		}

		while (m.find()) {
			StringBuilder demo = new StringBuilder(m.group(1));
			if (demo.charAt(0) == '/'){
				demo.insert(0, insert);
			} else if (demo.charAt(0) == '.' && demo.charAt(1) == '.'){
				demo.delete(0, 3);
				demo.insert(0, insert);
			} else if (demo.charAt(0) == '.' && demo.charAt(1) != '.'){
				demo.deleteCharAt(0);
				demo.insert(0, insert);
			} else if (demo.indexOf("http") != 0){
				demo.insert(0, insert);
			}  
			content.append(demo).append(LINE);
		} 
		return content.toString();}


	private String dumpHtmls() throws MalformedURLException,
			IOException {
		result = url2string(this.pagelink);
		
		Pattern p1 = Pattern.compile("href=[\"\']?([a-z0-9_#?{}:/\\.]+\\.htm|\\.html|\\.shtml)[\"\']?", Pattern.CASE_INSENSITIVE);
		
		Matcher m = p1.matcher(result);

		int index = pagelink.indexOf("/", 7);
		// 插入的字符串
		String insert = "";
		if (index > 0 && pagelink.indexOf("/", index) > 0){
			insert = pagelink.substring(0, index + 1);
		} else {
			insert = pagelink;
		}

		while (m.find()) {
			StringBuilder demo = new StringBuilder(m.group(1));
			if (demo.charAt(0) == '/'){
				demo.insert(0, insert);
			} else if (demo.charAt(0) == '.' && demo.charAt(1) == '.'){
				demo.delete(0, 3);
				demo.insert(0, insert);
			} else if (demo.charAt(0) == '.' && demo.charAt(1) != '.'){
				demo.deleteCharAt(0);
				demo.insert(0, insert);
			} else if (demo.indexOf("http") != 0){
				demo.insert(0, insert);
			}  
			content.append(demo).append(LINE);
		} 
		return content.toString();}



	private String dumpImages(final String url) throws MalformedURLException,
			IOException {
		StringBuilder result = url2string(this.pagelink);;

		Pattern p1 = Pattern.compile("src=[\"\']?([a-z0-9_#?{}:/\\.]+(\\.bmp|\\.jpg|\\.png|\\.gif))[\"\']?", Pattern.CASE_INSENSITIVE);
		
		Matcher m = p1.matcher(result);
		int index = url.indexOf("/", 7);
		// 插入的字符串
		String insert = "";
		if (index > 0 && url.indexOf("/", index) > 0){
			insert = url.substring(0, index + 1);
		} else {
			insert = url;
		}
		while (m.find()) {
			StringBuilder demo = new StringBuilder(m.group(1));
			if (demo.charAt(0) == '/'){
				demo.insert(0, insert);
			} else if (demo.charAt(0) == '.' && demo.charAt(1) == '.'){
				demo.delete(0, 3);
				demo.insert(0, insert);
			} else if (demo.charAt(0) == '.' && demo.charAt(1) != '.'){
				demo.deleteCharAt(0);
				demo.insert(0, insert);
			} else if (demo.indexOf("http") != 0){
				demo.insert(0, insert);
			}  
			content.append(demo);
		} 
		return content.toString();
	}


	private String dumpJsps(boolean flg) throws MalformedURLException,
			IOException {
		if (flg){
			return content.append(dumpJsps0()).toString();
		}
		StringBuilder result = url2string(this.pagelink);

		Pattern p1 = Pattern.compile("href=[\"\']?([a-z0-9_#?{}:/\\.]+\\.jsp)[\"\']?", Pattern.CASE_INSENSITIVE);
		
		Matcher m = p1.matcher(result);
		int index = this.pagelink.indexOf("/", 7);
		// 插入的字符串
		String insert = "";
		if (index > 0 && this.pagelink.indexOf("/", index) > 0){
			insert = this.pagelink.substring(0, index + 1);
		} else {
			insert = this.pagelink;
		}
		while (m.find()) {
			StringBuilder demo = new StringBuilder(m.group(1));
			if (demo.charAt(0) == '/'){
				demo.insert(0, insert);
			} else if (demo.charAt(0) == '.' && demo.charAt(1) == '.'){
				demo.delete(0, 3);
				demo.insert(0, insert);
			} else if (demo.charAt(0) == '.' && demo.charAt(1) != '.'){
				demo.deleteCharAt(0);
				demo.insert(0, insert);
			} else if (demo.indexOf("http") != 0){
				demo.insert(0, insert);
			}  
			content.append(demo);
		} 
		return content.toString();
	}

	private StringBuilder url2string(final String url) throws MalformedURLException,
			IOException {
		if (result != null) {
			return result;
		}
		if (encoding != null) {
			return url2string(url, encoding);
		}
		URL u = new URL(url);
		InputStream in = u.openStream();
		byte[] buf = new byte[2046];
		int len = -1;
		result = new StringBuilder();
		while ((len = in.read(buf, 0, buf.length)) != -1) {
			result.append(new String(buf, 0, len));
		}
		in.close();
		return result;
	}
	
	private StringBuilder url2string(final String url, final String encoding) throws MalformedURLException,
			IOException {
		if (result != null) {
			return result;
		}
		if (this.encoding == null) {
			this.encoding = encoding;
		}
		URL u = new URL(url);
		InputStream in = u.openStream();
		byte[] buf = new byte[2046];
		int len = -1;
		result = new StringBuilder();
		while ((len = in.read(buf, 0, buf.length)) != -1) {
			result.append(new String(buf, 0, len, this.encoding));
		}
		in.close();
		return result;
	}	

}



