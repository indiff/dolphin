package com.tan.util;

public final class Download {
	/**
	 * return the download's file name
	 * @param url
	 * @param filters
	 * @return file's name
	 */
	public final static String getFilename(String url,String ... filters) {
		if (url ==  null || url.trim().length() == 0) {
			return null;
		}
		StringBuilder result = new StringBuilder("tan_custom.");
		filters = filterWhiteSpace(filters);
		if (filters != null && filters.length != 0) {
			for (String f : filters) {
				result.append(f);
			}
			return result.toString();
		}
		return "tan" + url.substring(url.lastIndexOf(".")) ;
	}
	
	public final static String getFileName() {
		return "tan.o";
	}
	
	public static void main(String args[]){
		System.out.println(getFilename("http://www.fdafdsafdsa.com/fdsafdsa/false.wmv", "filter   ", "   hello"));
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
