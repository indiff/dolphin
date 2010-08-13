package com.tan.util;

/**
 *
 * @author annegu
 * @since 2009-07-16
 *
 */
public class DownloadStartup {

	private static final String encoding = "utf-8";
	public static void main(String[] args) {

		DownloadTask downloadManager = new DownloadTask();

//		String urlStr = "http://yztele4.skycn.com/down/Thunder5.9.3.951.zip";
//		String urlStr = "http://apache.freelamp.com/velocity/tools/1.4/velocity-tools-1.4.zip";
//		String urlStr = "http://cdn.dropbox.com/Dropbox%200.7.110.exe";
		String urlStr = "http://down.360safe.com/setup.exe";
//		String urlStr = "http://www.dianping.com/";

		downloadManager.setSleepSeconds(5);
		String downladFileName = downloadManager.download(urlStr, encoding);
		System.out.println("Download file is " + downladFileName + ".");
	}

}
