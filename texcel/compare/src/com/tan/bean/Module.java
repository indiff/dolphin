package com.tan.bean;

import lombok.Data;

public @Data final class Module {
	/** No. **/
	private Integer no;
	/** 項目名. **/
	private String name;
	/** 項目ID. **/
	private String id;
	/** データ型. **/
	private String dataType;
	/** 桁数. **/
	private Integer rowNum;
	/** PKEY. **/
	private String pkey;
	/** 必須. **/
	private boolean necess;
	/** 備考. **/
	private String remark;
}
