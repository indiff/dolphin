
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
	private String dir;
	private File file;
	private List lists;
	private String keyWord;

	public JarSearcher(final String dir) {
		lists = new ArrayList();
		init(dir);
		
	}

	private void init(String dir) {
		this.dir = dir;
		this.file = new File(dir);
		file.listFiles(new FileFilter() {
			public boolean accept(File path) {
				if (path.isDirectory()) {
					path.listFiles(this);
				} else if (path.isFile()
						&& path.getName().toLowerCase().endsWith(".jar")) {
					lists.add(path);
				}
				return false; // ignore the return segment.
			}
		});
	}

	public int size() {
		if (null == lists) {
			return 0;
		}
		return lists.size();
	}

	public void process(boolean searchClass) {
		JarFile jarFile = null;
		JarEntry jarEntry = null;
		String name;
		File file;
		Enumeration entries;
		for (Iterator iter = lists.iterator(); iter.hasNext();) {
			file = (File) iter.next();
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

					if (searchClass) { // class
						searchClass(name,file,  jarEntry);
					} else {
						searchJarContent(name, jarFile, jarEntry);
					}
				}
			}
		}
	}

	public void searchJarContent(final String name, final JarFile jarFile,
			final JarEntry jarEntry) {
		if (!jarEntry.isDirectory() && name.endsWith(".properties")
		// !name.endsWith(".png") &&
		// !name.endsWith(".jpg") &&
		// !name.endsWith(".classes") &&
		// !name.endsWith(".gif")
		) {
			if (search(jarFile, jarEntry)) {
				System.out.println(jarFile.getName() + "\t" + jarEntry);
			}
		}
	}

	public void searchClass(final String name, final File jarFile, final JarEntry jarEntry) {
		if (name.endsWith(".class")) {

			if (name.indexOf("Tomcat".toLowerCase()) >= 0) {
				System.out.println(jarFile + "\t" + jarEntry);
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final String path = "D:\\MyEclipse65\\myeclipse\\eclipse\\plugins\\";
		JarSearcher searcher = new JarSearcher(path);
		System.out.println("Jar文件数:" + searcher.size());
		boolean searchClass = false;
		searcher.setKeyWord("show custom locations");
		searcher.process(searchClass);
	}

	private void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
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
			if (builder.indexOf("Open Declaration") >= 0) {
				return true;
			}
			builder = null;
		}
		return false;
	}

}
