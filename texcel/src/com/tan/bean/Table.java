package com.tan.bean;

import lombok.Data;

/**
 * @author dolphin
 *
 * 2010/06/08 11:32:07
 */
public @Data class Table {
	// 論　理　名.
	private String logicName;
	// 物　理　名.
	private String physicalName;
	// 型.
	private String type;
	// 長さ.
	private double length;
	// 精度.
	private double decimal;
	// 必須.
	private boolean required;
	// 主キー.
	private boolean primaryKey;
	// 定　義　内　容.
	private String definition;
	// デフォルト値
	private Object defaultValue;
	// IDENTITY初期値
	private Object initialValue;
	// IDENTITYインクリメント
	private Object increment;
}
