package com.tan.qa.filter;

import static com.tan.util.TanUtil.chooseDir;
import static com.tan.util.TanUtil.println;
import static com.tan.util.TanUtil.warnln;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.tan.file.QAOperator;
import com.tan.file.Searcher;
import com.tan.qa.BaseUtil;
import com.tan.util.TanUtil;

/**
 * QA filter.
 * 将选择的文件夹下面的 qa excel文件
 * 遍历所有 yyyyMMdd 文件夹内的 Excel 文件夹.
 *  复制到
 * 当前日期文件夹内(yyyyMMdd)
 * @author dolphin
 *
 * 2010/07/08 14:15:27
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File current = BaseUtil.getCurrentPathFile();
		
		// TODO Delete.
//		File current = 
//			new File("D:\\QA\\Qa-Filter");
		
		
		File chooseDir = chooseDir(current.getParent(), "选择文件夹");
		checkRoot(chooseDir);
		
		Excels sets = new Excels();
		
		/** Check the root.**/
		List<File> rootResults = getFileList(chooseDir, ".xls");
		
		if (rootResults == null || rootResults.size() ==0) {
			warnln("选择的文件夹内位找到该excel");System.exit(0);
		}
		
		sets.addAll(rootResults);
		
		// 存放的目录.
		File mkdir = new QAOperator(current.getParentFile(), "yyyyMMdd").getDir();
		
		// Get the parent files.
		File[] parentDateDirs = 
			parentDateDirs(current);
		
		for (File dir : parentDateDirs) {
			List<File> results = getFileList(dir, ".xls");
			if (TanUtil.isEmpty(results))
				continue;
			for (File f : results) {
				sets.add(f);
			}
		}
		
		warnln("文件个数:" + sets.size());
		if (sets == null || sets.size() == 0) {
			warnln("没有更新的QA, App Exit.");
			System.exit(0);
		}
		
		/*************** Add the check when the dir had Qa excels. *****************/
		if (mkdir != null && mkdir.isDirectory()) {
			List<File> hadExcels = getFileList(mkdir, ".xls");
			if (hadExcels != null && hadExcels.size() >= 1) {
				// had excels.
				mkdir = new File(mkdir, new SimpleDateFormat("HHmm").format(new Date()));
				
				if (!mkdir.exists()) {
					mkdir.mkdir();
				}
				
				if (mkdir.isFile()) {
					mkdir.delete(); mkdir.mkdir();
				}
			}
		}
		/*************** Add the check when the dir had Qa excels. *****************/

		
		for (File q : sets ) {
			println("copy " + q + ' ' + mkdir + File.separator  + q.getName());
		}
		
		for (File q : sets ) {
			println("echo " + q.getName() + " >> file.txt");
		}
		
		
		warnln("See the filter file list : file.txt");
	}

	private static void checkRoot(final File root) {
		if (root == null) {
			warnln("未选择文件夹!");System.exit(-1);
		}
		if (root.isFile()) {
			warnln("请选择文件夹!");System.exit(-1);
		}
		if (!root.exists()) {
			warnln("该文件不存在!");System.exit(-1);
		}
	}

	private static File[] parentDateDirs(final File current) {
		return current.getParentFile().listFiles(new QaFileFilter());
	}
	
	private static List<File> getFileList(final File dir, final String extension) {
		return new Searcher(dir, extension).getResults();
	}
}
