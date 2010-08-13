package com.tan.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
/**
 * 获取url地址网页中的link 或者下载地址
 * @autor tan
 */
public final class GetURL {
	//存放结果
	private static StringBuffer result = new StringBuffer();
	
	/**
	 * 通过url获取下载链接
	 * @param url_str	url地址
	 * @param protocols 匹配的协议数组
	 * @param suffix 	匹配的后缀名 
	 * @return 下载的link或者地址
	 */		
	public static String getSourceA(String url, String encoding) {
		URL u = null ;
		if (encoding == null || encoding.trim().length() == 0) {
			encoding = "gb18030";
		}
		StringBuilder builder = new StringBuilder();
		if (url != null && url.length() != 0)
			try {
				u = new URL(url);
			} catch (MalformedURLException e2) {
				e2.printStackTrace();
			}
		InputStream in = null;
		BufferedInputStream bis = null;
		try {
			in = u.openStream();
			bis = new BufferedInputStream(in);
			byte[] b = new byte[8192];
			int len = -1;
			while ((len = bis.read(b, 0, b.length)) != -1) {
				builder.append(addJavascriptEvent(new String(b, 0, len, encoding)));
//				builder.append(filter(new String(b, 0, len, "gb18030")));
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return builder.toString();
	}
	

	/**
	 * 通过url获取下载链接
	 * @param url_str	url地址
	 * @param protocols 匹配的协议数组
	 * @param suffix 	匹配的后缀名 
	 * @return 下载的link或者地址
	 */	
	public static String getSourceB(String url_str,String[] protocols,String[] suffixs)  {
		StringBuffer result = new StringBuffer();
		InputStream in = null;
		BufferedInputStream bufIn = null;
		try {
			URL url = new URL(url_str);
			in = url.openStream();
			bufIn = new BufferedInputStream(in);
			byte[] b = new byte[8192];
			int len = -1;
			while ((len = bufIn.read(b, 0, b.length)) != -1)	
				result = result.append(new String(b, 0, len, "gb2312")) ;
			analyze(result.toString().trim(), protocols, suffixs);
		} catch (Exception e){
			return e.getMessage();
		} finally {
			if (bufIn != null) {
				try {
					bufIn.close();
				} catch(IOException e) {
					return e.getMessage();
				}
			}
		}
		return getResult().toString();
	}


	/**
	 * 静态的StringBuffer变量,存放结果
	 * @return StringBuffer存放的结果
	 */
	public static StringBuffer getResult() {
		return result;
	}


	public static void main(String[] args) {
		String demo = "src='/fdasfdadfhttp://www.fdsa.fdsfs.com/some.GIF	";
		demo = demo.trim().replaceAll("[hH][tT]{2}[pP]", "http");
		demo = demo.trim().replaceAll("[gG][iI][fF]","gif");
		System.out.println(getSourceA("http://www.baidu.com", null));
		System.out.println(addJavascriptEvent("fdaonfdsa <a"));
//		char[] cs = "我是中国人".toCharArray();
//		System.out.println(cs[2]);
	}
	
	private static String addJavascriptEvent(final String value) {
		if (value.indexOf("<a") < 0) {
			return value;
		}
		StringBuilder builder = new StringBuilder(10);
		char[] cs = value.toCharArray();
		int cursor = -1;
		for (int i = 0; i < cs.length; i++) {
			if (i != cs.length && cs[i] == 60  && cs[i + 1] == 97) {
				cursor = i + 1;
				builder.append("<a onclick='return _g_j(this)'");
			} else if (i == cursor) {
				continue;
			} else {
				builder.append(cs[i]);
			}
		}
		return builder.toString();
	}
	/**
	 * 分析字符串内容,去除空白字符
	 * @param demo		被匹配的字符串 
	 * @param protocols 匹配的协议数组
	 * @param suffix 	匹配的后缀名 
	 */
	public final static void analyze(String long_str,String[] protocols,String[] suffixs) {
		result = new StringBuffer();
		if (!isEmpty(long_str)){
			for (String temp : long_str.trim().split("[ \t]+")){
				String url = getUrl(temp.trim(), protocols, suffixs);
				if (!"".equals(url) && result.toString().indexOf(url) == -1)
					result.append(url).append("\n");
				}
		}
	}
	
	/**
	 * 通过正则表达式获取匹配的图像或者其他的下载链接
	 * @param demo		被匹配的字符串 
	 * @param protocols 匹配的协议数组
	 * @param suffix 	匹配的后缀名
	 * @return 如果匹配成功返回匹配到的下载链接,匹配失败的话就返回空字符串
	 */
	private final static String getUrl(String demo , final String[] protocols,
			final String[] suffixs){
		if (demo.length() == 0 || protocols.length == 0 || suffixs.length == 0) return "";
		for (String protocol : protocols){
			String temp1 = "";
			char[] ps = protocol.toCharArray();
			for (char p : ps){
				temp1 += "[" + Character.toString(p)  + Character.toUpperCase(p) + "]";
			}
			for (String suffix : suffixs){
				String temp2 = "";
				char[] ss = suffix.toCharArray();
				for (char s : ss){
					temp2 += "[" + Character.toString(s)  + Character.toUpperCase(s) + "]";
				}
				if (demo.matches(".*" + temp1 + "://.*") & demo.matches(".*\\\56"+ temp2 + ".*")){
					demo = demo.replaceAll("<", "");
					demo = demo.replaceAll(">", "");
					demo = demo.trim().replaceAll(temp1, protocol);
					demo = demo.trim().replaceAll(temp2, suffix); 
					return demo.substring(demo.indexOf(protocol), demo.indexOf(suffix)) + suffix;
				}				
			}
		}		
		return "";
	}
	
	/**
	 * 替换Html标签中的link标签, 增加onclick事件
	 * @param value 被过滤的字符串
	 * @return value 过滤之后的字符串
	 */
	private static String filter(String value) {
		return value.replace("<a", "<a onclick='return _g_j(this)'");
	}
	/**
	 * 检查url
	 * @param url 被检查的 url
	 * @return url 检查后的 url
	 * */
	public static String checkUrl(String url) {
		StringBuilder b = new StringBuilder();
		url = url.toLowerCase();
		if (url.matches("^http://[a-z0-9\\./]+$")) {
			return url;
		} else if (url.startsWith("dzh")) {
			return "http://dzh.mop.com";
		} else if (url.startsWith("minisite")) {
			return "http://minisite.qq.com/others8";
		} else if (url.startsWith("xiaoyou")) {
			return "http://xiaoyou.qq.com";
		} else if (url.startsWith("web.qq")) {
			return "http://web.qq.com";
		} else if (!url.startsWith("www")) {
			b.append("http://www.").append(url);
		}else if (!url.startsWith(".")) {
			b.append("http://www").append(url);
		}
		
		return b.toString();
	}
	
	private static boolean isEmpty(final String value) {
		if (value == null || value.trim().length() == 0) {
			return true;
		}
		return false;
	}
}
