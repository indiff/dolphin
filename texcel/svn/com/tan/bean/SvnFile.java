package com.tan.bean;

import java.io.File;

public class SvnFile implements Comparable{
	private String parent;
	private String name;
	private boolean isFile;
	private String absolultePath;
	private File file ;

	public SvnFile(String parent,File file) {
		this.parent = parent;
		this.isFile = file.isFile();
		this.file = file;
		absolultePath = file.getAbsolutePath();
		int idx = absolultePath.indexOf(parent);
		if (idx >= 0) {
			name = absolultePath.substring(parent.length());
		}
	}
	
	
	
	
	




	@Override
	public int compareTo(Object o) {
		SvnFile f = (SvnFile) o;
		int len = f.getName().length();
		int selflength = this.getName().length();
		if (len > selflength) {
			return len - selflength;
		} else if(len < selflength) {
			return selflength - len;
		} else {
			return f.getName().compareTo(this.getName());
		}
	}




	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isFile() {
		return isFile;
	}
	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}
	public String getAbsolutePath() {
		return absolultePath;
	}

	public void setAbsolultePath(String absolultePath) {
		this.absolultePath = absolultePath;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isFile ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (!file.exists()) 
			return false;
		SvnFile other = (SvnFile) obj;
		if (!other.getFile().exists()) 
			return false;
		if (isFile != other.isFile)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
