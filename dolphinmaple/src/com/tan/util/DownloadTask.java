package com.tan.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author annegu
 * @since 2009-07-16
 * 
 */
public class DownloadTask {

	// private static transient
	private int threadNum = 10;
	private URL url = null;
	private long threadLength = 0;
	public String fileDir = "D:/concurrent/";
	public String fileName = "test.html";
	public boolean statusError = false;
	private String charset;

	public long sleepSeconds = 5;

	public String download(String urlStr, String charset) {
		statusError = false;
		this.charset = charset;
		long contentLength = 0;
		CountDownLatch latch = new CountDownLatch(threadNum);
		ChildThread[] childThreads = new ChildThread[threadNum];
		long[] startPos = new long[threadNum];
		long endPos = 0;

		try {
			// 从 url 中获得下载文件的格式与名字
			this.fileName = urlStr.substring(urlStr.lastIndexOf("/") + 1, urlStr.lastIndexOf("?")>0 ? urlStr.lastIndexOf("?") : urlStr.length());
			if("".equalsIgnoreCase(this.fileName)){
				this.fileName = UUID.randomUUID().toString();
			}

			this.url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			setHeader(con);
			// 获取 content 的长度
			contentLength = con.getContentLength();
			// 将context分为threadNum段的话， 每段的长度
			this.threadLength = contentLength / threadNum;
			// 第一步， 分析已经下载的临时文件， 设置断点，如果是一个新的下载任务， 则建立目标文件
			startPos = setThreadBreakpoint(fileDir, fileName, contentLength,
					startPos);
			// 分为多个线程下载文件
			ExecutorService exec = Executors.newCachedThreadPool();
			for (int i = 0; i < threadNum; i++) {
				// 创建子线程来负责下载数据，每段数据的起始位置 (threadLength * i + 已经下载的长度)
				startPos[i] += threadLength * i;

				/*
				 * 设置子线程的终止位置, 非最后一个线程为 ( threadLength * (i + 1) - 1)
				 * 最后一个线程的终止位置即为下载内容的长度
				 */
				if (i == threadNum - 1) {
					endPos = contentLength;
				} else {
					endPos = threadLength * (i + 1) - 1;
				}
				// 开始执行子线程
				ChildThread thread = new ChildThread(this, latch, i,
						startPos[i], endPos);
				childThreads[i] = thread;
				exec.execute(thread);
			}

			try {
				// 等待 CountdownLatch信号为0, 表示所有的子线程都结束
				latch.await();
				exec.shutdown();
				// 第三步， 把分段下载来的临时文件中的内容写入目标文件中
				tempFileToTargetFile(childThreads);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileDir + fileName;
	}

	private void tempFileToTargetFile(ChildThread[] childThreads) {
		try {
			BufferedOutputStream outputStream = new BufferedOutputStream(
					new FileOutputStream(fileDir + fileName));

			// �����������̴߳�������ʱ�ļ�����˳�����������д��Ŀ���ļ���
			for (int i = 0; i < threadNum; i++) {
				if (statusError) {
					for (int k = 0; k < threadNum; k++) {
						if (childThreads[k].tempFile.length() == 0)
							childThreads[k].tempFile.delete();
					}
					System.out.println("�����������񲻳ɹ��������������߳���");
					break;
				}

				BufferedInputStream inputStream = new BufferedInputStream(
						new FileInputStream(childThreads[i].tempFile));
				System.out.println("Now is file " + childThreads[i].id);
				int len = 0;
				long count = 0;
				byte[] b = new byte[1024];
				while ((len = inputStream.read(b)) != -1) {
					count += len;
					outputStream.write(b, 0, len);
					if ((count % 4096) == 0) {
						outputStream.flush();
					}
				}

				inputStream.close();
				// ɾ����ʱ�ļ�
				if (childThreads[i].status == ChildThread.STATUS_HAS_FINISHED) {
					childThreads[i].tempFile.delete();
				}
			}

			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private long[] setThreadBreakpoint(String fileDir2, String fileName2,
			long contentLength, long[] startPos) {
		File file = new File(fileDir + fileName);
		long localFileSize = file.length();

		if (file.exists()) {
			System.out.println("file " + fileName + " has exists!");
			// ���ص�Ŀ���ļ��Ѵ��ڣ��ж�Ŀ���ļ��Ƿ�����
			if (localFileSize < contentLength) {
				System.out.println("Now download continue ... ");

				// ����Ŀ���ļ���������ʱ�ļ������öϵ��λ�ã���ÿ����ʱ�ļ��ĳ���
				File tempFileDir = new File(fileDir);
				File[] files = tempFileDir.listFiles();
				for (int k = 0; k < files.length; k++) {
					String tempFileName = files[k].getName();
					// ��ʱ�ļ�������ʽΪ��Ŀ���ļ���+"_"+���
					if (tempFileName != null && files[k].length() > 0
							&& tempFileName.startsWith(fileName + "_")) {
						int fileLongNum = Integer.parseInt(tempFileName
								.substring(tempFileName.lastIndexOf("_") + 1,
										tempFileName.lastIndexOf("_") + 2));
						// Ϊÿ���߳����������ص�λ��
						startPos[fileLongNum] = files[k].length();
					}
				}
			}
		} else {
			// ������ص�Ŀ���ļ������ڣ��򴴽����ļ�
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return startPos;
	}

	/**
	 * 
	 * @author annegu
	 * @since 2009-07-16
	 * 
	 */
	public class ChildThread extends Thread {
		public static final int STATUS_HASNOT_FINISHED = 0;
		public static final int STATUS_HAS_FINISHED = 1;
		public static final int STATUS_HTTPSTATUS_ERROR = 2;
		private DownloadTask task;
		private int id;
		private long startPosition;
		private long endPosition;
		private final CountDownLatch latch;
		// private RandomAccessFile tempFile = null;
		private File tempFile = null;
		// 线程的状态码
		private int status = ChildThread.STATUS_HASNOT_FINISHED;

		public ChildThread(DownloadTask task, CountDownLatch latch, int id,
				long startPos, long endPos) {
			super();
			this.task = task;
			this.id = id;
			this.startPosition = startPos;
			this.endPosition = endPos;
			this.latch = latch;

			try {
				tempFile = new File(this.task.fileDir + this.task.fileName
						+ "_" + id);
				if (!tempFile.exists()) {
					tempFile.createNewFile();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		public void run() {
			System.out.println("Thread " + id + " run ...");
			HttpURLConnection con = null;
			InputStream inputStream = null;
			BufferedOutputStream outputStream = null;
			long count = 0;
			long threadDownloadLength = endPosition - startPosition;

			try {
				outputStream = new BufferedOutputStream(new FileOutputStream(
						tempFile.getPath(), true));
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
			}

			for (;;) {
				try {
					// ��URLConnection
					con = (HttpURLConnection) task.url.openConnection();
					setHeader(con);
					con.setAllowUserInteraction(true);
					// ����l�ӳ�ʱʱ��Ϊ10000ms
					con.setConnectTimeout(10000);
					// ���ö�ȡ��ݳ�ʱʱ��Ϊ10000ms
					con.setReadTimeout(10000);

					if (startPosition < endPosition) {
						// ����������ݵ���ֹ���
						con.setRequestProperty("Range", "bytes="
								+ startPosition + "-" + endPosition);
						System.out.println("Thread " + id
								+ " startPosition is " + startPosition);
						System.out.println("Thread " + id + " endPosition is "
								+ endPosition);

						//�ж�http status�Ƿ�ΪHTTP/1.1 206 Partial Content����200 OK
						//���������}��״̬����status��ΪSTATUS_HTTPSTATUS_ERROR
						if (con.getResponseCode() != HttpURLConnection.HTTP_OK
								&& con.getResponseCode() != HttpURLConnection.HTTP_PARTIAL) {
							System.out.println("Thread " + id + ": code = "
									+ con.getResponseCode() + ", status = "
									+ con.getResponseMessage());
							status = ChildThread.STATUS_HTTPSTATUS_ERROR;
							this.task.statusError = true;
							outputStream.close();
							con.disconnect();
							System.out.println("Thread " + id + " finished.");
							latch.countDown();
							break;
						}

						inputStream = con.getInputStream();
						int len = 0;
						byte[] b = new byte[1024];
						while (!this.task.statusError
								&& (len = inputStream.read(b)) != -1) {
							outputStream.write(b, 0, len);
							
							count += len;
							startPosition += len;
							// ÿ����4096��byte��һ���ڴ�ҳ�����������flushһ��
							if (count % 4096 == 0) {
								outputStream.flush();
							}
						}

						if (count >= threadDownloadLength) {
							status = ChildThread.STATUS_HAS_FINISHED;
						}
						outputStream.flush();
						outputStream.close();
						inputStream.close();
						con.disconnect();
					} else {
						status = ChildThread.STATUS_HAS_FINISHED;
					}

					
					System.out.println("Thread " + id + " finished.");
					latch.countDown();
					break;
				} catch (IOException e) {
					try {
						outputStream.flush();
						TimeUnit.SECONDS.sleep(getSleepSeconds());
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					continue;
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
					con.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void setHeader(URLConnection con) {
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.3) Gecko/2008092510 Ubuntu/8.04 (hardy) Firefox/3.0.3");
		con.setRequestProperty("Accept-Language", "en-us,en;q=0.7,zh-cn;q=0.3");
		con.setRequestProperty("Accept-Encoding", "aa");
		con.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		con.setRequestProperty("Keep-Alive", "300");
		con.setRequestProperty("Connection", "keep-alive");
		con.setRequestProperty("If-Modified-Since", "Fri, 02 Jan 2009 17:00:05 GMT");
		con.setRequestProperty("If-None-Match", "\"1261d8-4290-df64d224\"");
		con.setRequestProperty("Cache-Control", "max-age=0");
		con.setRequestProperty("Referer", "http://www.skycn.com/soft/14857.html");
	}

	public long getSleepSeconds() {
		return sleepSeconds;
	}

	public void setSleepSeconds(long sleepSeconds) {
		this.sleepSeconds = sleepSeconds;
	}

}
