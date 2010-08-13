package com.tan.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Site2Ip {
	private static String SITE = "http://www.baidu.com";
	
	private static String name = getName();
	private static String ip ;
	private static List<String> result = new ArrayList<String>();
	
	
	public static void clear() {
		result.clear();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		result = new ArrayList<String>();
		URL url = new URL(SITE);
//		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		InputStream in = url.openStream();
//		printStream(in, "utf-8");
//		name2Ip("<a href=\"http://web.qq.com\">hello</a>");
//		name2Ip("<a href=\"http://baidu.com\" href=\"http://web.qq.com\">hello</a>");
//		System.out.println(Integer.toHexString(100));
		System.out.println(stream2String(in, "gb2312"));
	}
	
	
	public static String parseSite2Ip(String site, String ... charsets) {
		SITE = site;
		URL url = null;
		try {
			url = new URL(SITE);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			if (charsets == null || charsets.length == 0) {
				return stream2String(url.openStream(), "gb2312");
			}
			return stream2String(url.openStream(), charsets[0]);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private static void printStream(InputStream in, String ... charsets) throws Exception {
		BufferedReader reader = null;
		if (charsets.length == 0) {
			reader = new BufferedReader(new InputStreamReader(in));
		} else {
			reader = new BufferedReader(new InputStreamReader(in, charsets[0]));
		}
		if (reader == null) return;
		else {
			String str = null;
			while ((str = reader.readLine()) != null) {
				System.out.println(str);
			}
		}
		in.close();
		reader.close();
	}
	
	private static String stream2String(InputStream in, String ... charsets) throws Exception {
		StringBuilder content = new StringBuilder();
		BufferedReader reader = null;
		if (charsets.length == 0) {
			reader = new BufferedReader(new InputStreamReader(in));
		} else {
			reader = new BufferedReader(new InputStreamReader(in, charsets[0]));
		}
		String str = null;
		while ((str = reader.readLine()) != null) {
			str = name2Ip(str.toLowerCase());
			content.append(str).append("\r\n");
		}
		in.close();
		reader.close();
		return content.toString();
	}
	
	private static String name2Ip(String str) throws Exception {
		StringBuilder line = new StringBuilder();
		ip = getIp(name);
		str = str.replace(name, ip);
		if (str.matches("^.*http://.*$")) {
			getName(str);
			if (result.size() != 0) {
				for (Iterator iter = result.iterator(); iter.hasNext() ;) {
					name = (String) iter.next();
					str = str.replace(name, getIp(name));
				}
			}
		} 
		line.append(str);
		return line.toString();
	}

	private static void getName(String str, int from )
			throws UnknownHostException {
		String name = "";
		String suffix = ".com";
		int start = str.indexOf("http://", from);
		int end = str.indexOf(suffix, start);
		if (end == -1) {
			suffix = ".cn";
			end = str.indexOf(suffix, start);
		}
		if (start != -1 && end != -1) {
			name = str.substring(start + 7, end + suffix.length());
			if (name.matches("^[\\w\\\56]+$")) {
				result.add(name);
			}
		}
		
		if ((start = str.indexOf("http://", end)) != -1) {
			getName(str, start);
		}
	}

	
	private static void getName(final String str,final int from,final String prefix, final String  suffix)
			throws Exception {
		String name = "";
		int start = str.indexOf(prefix, from);
		int end = str.indexOf(suffix, start);
		if (start != -1 && end != -1) {
			name = str.substring(start + prefix.length(), end + suffix.length());
			if (!"".equals(name) && name.matches("^[\\w\\\56]+$")) {
				result.add(name);
			} else if ((start = str.indexOf(prefix , end)) != -1) {
				getName(str, start, prefix, ".cn");
			}
		} else {
			getName(str, start, prefix, ".cn");
		}
	}
	
	
	private static String getIp(String name) throws UnknownHostException {
		String ip = name;
		try {
			InetAddress address = InetAddress.getByName(name);
			ip = address.getHostAddress();
		} catch (Exception e) {
		} 
		ip = checkIp(ip);
		return ip;
	}

	private static String checkIp(String ip) {
//		Pattern pattern = Pattern.compile("^(\\d\\\56\\d\\\56\\d\\\56\\d\\\56)(.*)$");
//		Matcher matcher = pattern.matcher(ip);
//		matcher.group();
//		
//		if (!ip.matches("^\\d\\\56\\d\\\56\\d\\\56\\d\\\56$")) {
//			
//			ip.replaceAll("[]", "");
//		}
		return ip = ip.replaceAll("\\\56([a-zA-Z\\\56]*)$", "");
	}


	private static String getName(String ... sites) {
		String name;
		if (sites.length == 0) {
			name = SITE.toLowerCase().replace("http://", "")
									 .replace("ftp://", "");
		} else {
			name = sites[0].toLowerCase().replace("http://", "")
										 .replace("ftp://", "");
		}
		return name;
	}
	
	
	private static void getName(String str) {
		String[] temp = str.split("http://");
		for (String t : temp){
			String s = "";
			int from = t.indexOf(".com");
			if (from != -1){
				s = t.substring(0, from + 4);
			} else if ( (from = t.indexOf(".cn")) != -1){
				s = t.substring(0, from + 3);
			}
			if (!isEmpty(s) && s.matches("^[\\w\\\56]+$")){
				result.add(s);
			}
		}	
	}

	private static boolean isEmpty(String value) {
		if (value == null || value.trim().length() == 0){
			return true;
		}
		return false;
	}		
}
