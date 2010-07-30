package ipmsg.swt;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * ���O�o�͂��s��static���\�b�h��񋟂��܂��B
 * �J�����g�f�B���N�g����message.log�Ƃ����t�@�C���ɏo�͂���܂��B
 * 
 * @author takezoe
 */
public final class Logger {
    
	/** ���O�t�@�C���̖��O */
	private static final String LOG_FILE = "message.log";
	/** ���O�t�@�C����Encoding */
	private static final String LOG_FILE_ENCODING = "utf-8";
	
    /**
     * ���O���o�͂��܂��B
     * 
     * @param message ���b�Z�[�W
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
