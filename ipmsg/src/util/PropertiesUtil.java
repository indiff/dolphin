package util;

import static ipmsg.Constants.PROPERTY_FILE;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {
	private static Properties properties = new Properties();
	
	static {
    	properties = new Properties();
		InputStream in = null;
		File file = new File(PROPERTY_FILE);
		try {
			if (file.exists() && file.isFile()){
				in = new FileInputStream(file);
		    	properties.load(in);
			} else {
				System.err.println(file.getAbsolutePath() + " not found!");
			}
		} catch(IOException ex){
		    ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				} in = null;
			}
		}
	}
	
	private PropertiesUtil(){}
	
	public static String get(final String key) {
		return properties.getProperty(key);
	}
	
	public static String getEncoding() {
		String encoding = get("encoding");
		if (encoding == null){
			properties.put("encoding", "utf-8");
			return "utf-8";
		}
		else
			return encoding;
	}
	
	public static int getPort() {
		String port = get("port");
		if (port == null) {
			properties.put("port", "2426");
			return 2426;
		} else {
			return Integer.parseInt(port);
		}
	}
	
	public static Properties loadProperties() {
		return properties;
	}
	public static void main(String[] args) {
		System.out.println(getEncoding());
	}
}
