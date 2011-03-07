

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class JarSearcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final String path = "D:\\eclipses\\eclipse-rcp-helios-SR1-win32\\eclipse\\plugins\\";
		final File dir = new File(path);
		final List lists = new ArrayList();
		dir.listFiles(new FileFilter(){
			public boolean accept(File file) {
				if (file.isDirectory()) {
					file.listFiles(this);
				} else if(file.isFile() && file.getName().toLowerCase().endsWith(".jar")) {
					lists.add(file);
				}
				return false; // ignore the return segment.
			}
		}) ;
		
		int size = lists.size();
		System.out.println("Jar文件数:" + size);
		if (size > 0) {
			JarFile jarFile = null;
			JarEntry jarEntry = null;
			String name;
			File file;
			Enumeration entries;
			for (Iterator iter = lists.iterator(); iter.hasNext();) {
				file  = (File) iter.next();
				try {
					jarFile = new JarFile(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if (null != jarFile) {
					entries = jarFile.entries();
					
					while (entries.hasMoreElements()) {
						jarEntry = (JarEntry) entries.nextElement();
						name = jarEntry.getName();
						
						// process the entry's stream.
						name = name.toLowerCase();
						if (
								!jarEntry.isDirectory() &&
								name.endsWith(".properties") 
//								!name.endsWith(".png") &&
//								!name.endsWith(".jpg") &&
//								!name.endsWith(".classes") &&
//								!name.endsWith(".gif") 
						 ) {
							if (search(jarFile, jarEntry)) {
								System.out.println(file + "\t" + jarEntry);
							}
						}
						
//						if (name.endsWith(".class")) {
//							
//							if (name.indexOf("FileEditorInput".toLowerCase())>= 0) {
//								System.out.println(file + "\t" + jarEntry);
//							}
//						}
					}
				}
			}
		}
	}

	private static boolean search(JarFile jarFile, JarEntry jarEntry) {
		int len = -1;
		byte[] buf = new byte[2046];
		StringBuffer builder = new StringBuffer();
		InputStream in = null;
		try {
			in = jarFile.getInputStream(jarEntry);
			while (-1 != (len = in.read(buf, 0, 2046))) {
				builder.append(new String(buf, 0, len));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != in)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			in = null;
		}
		if (builder.length() > 0) {
			if (builder.indexOf("Open Declaration") >= 0 ) {
				return true;
			}
			builder = null;
		}
		return false;
	}

}
