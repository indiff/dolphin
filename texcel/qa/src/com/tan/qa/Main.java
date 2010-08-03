package com.tan.qa;

import static com.tan.util.TanUtil.println;
import static com.tan.util.TanUtil.showInputDialog;
import static com.tan.util.TanUtil.warnln;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.tan.bean.Qa;
import com.tan.file.QAOperator;
import com.tan.file.Searcher;
import com.tan.util.DialogUtil;
import com.tan.util.Leader;
import com.tan.util.StringUtil;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String currentPath = BaseUtil.getCurrentPath();
		
		DialogUtil util = new DialogUtil();
		Leader leader = new Leader();
		
		String path = util.chooseDirectory("ExcelDir", 
				"質問票", 
				leader.getOldPath());
		
		util.forceDispose();
		
		// check path.
		if (StringUtil.isEmpty(path)) {
			warnln("Path is null");
			return;
		}
		
		Searcher searcher = 
			new Searcher(new File(path), ".xls");
		
		/*** Logger Excel 文件的个数.**/
		List<File> files = searcher.getResults();
		int size = files.size();
		
		/*** Logger Excel 文件的个数.**/
		println("文件的个数:" + size);
		
		
		String number = 
			showInputDialog("请输入初始番号:", "0010");
		
		// check Number.
		if (number == null || number.length() != 4 ||  !number.matches("^\\d{4}$")) {
			warnln(number + "不是数字", "请输入4位数字");
			return;
		}
		
		/**
		 * 创建目录.
		 */
		QAOperator operator = new QAOperator(currentPath);
		operator.setL(leader);
		
		List<Qa> list = new ArrayList<Qa>();
		
		int index = 1;
		// QAOperator.mkdir(currentPath);
		if (files != null && size != 0) {
			for (Iterator<File> iter = files.iterator(); iter.hasNext();) {
				File f = iter.next();
				
				// write the number to the excel.
				Qa q = operator.modifyExcel(f, number);
				
				list.add(q);
				number = add(number);
				
				BaseUtil.showPercent(index++, size);
			}
		}
		Collections.sort(list);
		
		Iterator<Qa> iter = list.iterator();
		while (iter.hasNext()) {
			println(iter.next());
		}
	}
	
	/**
	 * add the number.
	 * @param number
	 * @return
	 */
	private static String add(String number) {
		int n = Integer.parseInt(number);
		++n;
		String newNumber = String.valueOf(n);
		return formatNumber(newNumber);
	}
	
	/**
	 * format number when the string's length 
	 * is not four.
	 * @param number
	 * @return
	 */
	public static String formatNumber(String number) {
		int length = number.length();
		if (length < 4) {
			if (length == 3) return "0" + number;
			if (length == 2) return "00" + number;
			if (length == 1) return "000" + number;
		}
		return number;
	}

}
