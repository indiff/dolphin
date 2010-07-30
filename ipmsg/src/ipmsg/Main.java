package ipmsg;

import java.io.File;
import java.io.IOException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String currentPath = System.getProperty("user.dir");
		String path = 
			currentPath + File.separatorChar + "ipmsg.jar;" + 
			currentPath + File.separatorChar + "lib\\swt.jar"
		;
		System.out.println("java -cp " +  path  + ";%CLASSPATH% ipmsg.swt.Main");
		try {
			Runtime.getRuntime().exec("java -cp " +  path  + ";%CLASSPATH% ipmsg.swt.Main -tray");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
