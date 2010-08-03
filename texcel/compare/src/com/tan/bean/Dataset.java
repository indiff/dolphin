package com.tan.bean;

import java.util.List;

import lombok.Data;

public @Data final class Dataset {
	/** No. **/
	private Integer no;
	/** データテーブルID. **/
	private String table;
	/** データテーブル名. **/
	private String tableName;
	/** 備考. **/
	private String remark;
	/** 模块. **/
	private List<Module> module;
}
