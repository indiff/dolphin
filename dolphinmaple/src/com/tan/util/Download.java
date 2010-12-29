package com.tan.util;

public final class Download {
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
		String name = "tan";
		if (idx >= 0) {
			idx++;
			name = url.substring(idx);
		}
		StringBuffer result = new StringBuffer(name);
		suffixs = filterWhiteSpace(suffixs);
		if (suffixs != null && suffixs.length != 0) {
			for (String f : suffixs) {
				result.append(f);
			}
			return result.toString();
		}
		return "tan" + name ;
	}
	
	public final static String getFileName() {
		return "tan.o";
	}
	
	public static void main(String args[]){
		System.out.println(getFilename("http://www.fdafdsafdsa.com/fdsafdsa/false.wmv", "filter   ", "   hello"));
		System.out.println(getFilename("false.wmv", "filter   ", "   hello"));
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
}
