package ipmsg.swt;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * ログ出力を行うstaticメソッドを提供します。
 * カレントディレクトリのmessage.logというファイルに出力されます。
 * 
 * @author takezoe
 */
public final class Logger {
    
	/** ログファイルの名前 */
	private static final String LOG_FILE = "message.log";
	/** ログファイルのEncoding */
	private static final String LOG_FILE_ENCODING = "utf-8";
	
    /**
     * ログを出力します。
     * 
     * @param message メッセージ
     */
    final public synchronized static void writeLog(final String message){
    	OutputStream out = null;
    	try {
    		out = new FileOutputStream(LOG_FILE,true);
   			out.write(message.getBytes(LOG_FILE_ENCODING));
   			out.flush();
    	} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
    		if (out != null) {
    			try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				} out = null;
    		}
    	}
    }
}
