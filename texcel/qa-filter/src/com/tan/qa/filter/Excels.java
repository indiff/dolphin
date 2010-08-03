package com.tan.qa.filter;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.tan.util.TanUtil;

/**
 * Filter the excels by the excel file's name.
 * @author dolphin
 *
 * 2010/07/13 16:02:20
 */
public class Excels extends LinkedList<java.io.File>{
	
	private static final long serialVersionUID = 1L;

	private String[] extensions;
	
	private List<String> names;
	
	public Excels() {
		this(".xls");
	}
	
	public Excels(String ... exts) {
		extensions = exts;
		names = new ArrayList<String>();
	}

	@Override
	public boolean add(File e) {
		// get the file name.
		String name = e.getName().toLowerCase();
		
		// add the file's name to the list names.
		names.add(name);
		
		/**
		 * when the list names contains e
		 * then remove the element.
		 * and the method "contains" overrided.
		 */
		if (contains(e)) {
			if (this != null && this.size() != 0) {
				for (File f : this) {
					// when the excels lists contains the name then remove the file.
					if (name.equals(f.getName().toLowerCase())) 
						return remove(f);
				}
			}
		}
		
		// add the element to the excels.
		for (String ext : extensions) {
			if (name.endsWith(ext)) {
				return super.add(e);
			}
		}
		return false;
	}
	
	/**
	 * contains the file's name by the (ArrayList)names.
	 */
	@Override
	public boolean contains(Object o) {
		if (o == null) return false;
		File f = (File) o;
		// when the name duplicate then return true.
		return names.contains(f.getName().toLowerCase());
	}

	
	public static void main(String[] args) {
		Excels sets = new Excels();
		
		// add the all to the excels list.
		File f0= new File("D:\\zsky\\01-管理域\\02-質問票\\02-翻訳後\\xxx\\qa-other1.xls");
		File f2= new File("D:\\zsky\\01-管理域\\02-質問票\\02-翻訳後\\xxx\\qa-other3.xls");
		File f3= new File("D:\\zsky\\01-管理域\\02-質問票\\02-翻訳後\\xxx\\qa-20100101.xls");
		File f4= new File("D:\\zsky\\01-管理域\\02-質問票\\02-翻訳後\\xxx\\qa-20100202.xls");
		File f7= new File("D:\\zsky\\01-管理域\\02-質問票\\02-翻訳後\\xxx\\qa-20100303.xls");
		File f8= new File("D:\\zsky\\01-管理域\\02-質問票\\02-翻訳後\\xxx\\qa-other2.xls");
		
		// the name duplicate.
		File f1= new File("D:\\zsky\\01-管理域\\02-質問票\\02-翻訳後\\20100202\\qa-20100101.xls");
		File f5= new File("D:\\zsky\\01-管理域\\02-質問票\\02-翻訳後\\20100303\\qa-20100202.xls");
		File f6= new File("D:\\zsky\\01-管理域\\02-質問票\\02-翻訳後\\20100101\\qa-20100303.xls");
		
		
		// add the no duplicate.
		File f9= new File("D:\\zsky\\01-管理域\\02-質問票\\02-翻訳後\\20100101\\qa-20101010.xls");
		
		
		sets.add(f0);
		sets.add(f2);
		sets.add(f3);
		sets.add(f4);
		sets.add(f7);
		sets.add(f8);
		

		sets.add(f1);
		sets.add(f5);
		sets.add(f6);
		
		sets.add(f9);
		
		for (File f : sets) {
			TanUtil.println(f);
		}
	}
}
