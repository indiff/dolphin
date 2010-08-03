package com.tan.io;

import java.io.File;
import java.util.List;

import org.junit.Test;

public class FileListTest {
	@Test
	public void testFilter() {
		FileList list = new FileList(new File("C:\\compare"), ".xml", ".xsd");
		
		list.filter();
		
		List<File> results = list.getResults();
		
		for (File f : results) {
			System.out.println(f);
		}
	}
}
