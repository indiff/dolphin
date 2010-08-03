package com.tan.util;

import lombok.Getter;

/**
 * 
 * @author dolphin
 *
 * 2010/07/26 10:25:18
 */
public class PathConfig extends AbstractConfig {
	private @Getter String oldPath;
	private @Getter String excelsPath;
	private @Getter String xsdsPath;
	
	@Override
	void setMapValues() {
		oldPath =  this.getCache().remove("oldPath");
		excelsPath =  this.getCache().remove("excelsPath");
		xsdsPath =  this.getCache().remove("xsdsPath");
	}
}
