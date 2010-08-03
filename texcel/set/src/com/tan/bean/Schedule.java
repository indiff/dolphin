package com.tan.bean;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

/**
 * 進捗管理状況.
 * @author dolphin
 *
 * 2010/07/09 16:36:39
 */
public @Data  class Schedule {
	/** プログラムID.**/
	private String programId;
	
	/** リーダ.**/
	private String leader;	
	
	/** 実際の着手日.**/
	private Date actualStartDate;
	
	/** 仕様理解進捗率.**/
	private BigDecimal understandRate;
	
	/** 単体テスト設計進捗率.**/
	private BigDecimal utdRate;
	
	/** 単体テスト設計レビュー進捗率.**/
	private BigDecimal utdReviewRate;
	
	/** 製造（コーディング）進捗率.**/
	private BigDecimal codingRate;
	
	/** CDI進捗率.**/
	private BigDecimal cdiRate;
	
	/** 単体テスト進捗率. **/
	private BigDecimal utRate;
	
	 @Override
	 public String toString() {
//	 	return "Schedule [実際の着手日=" + actualStartDate + ", 仕様理解進捗率=" + understandRate + ", 単体テスト設計進捗率="
//	 			+ utdRate + ", 単体テスト設計レビュー進捗率=" + utdReviewRate + ", 製造（コーディング）進捗率=" + codingRate + ", CDI進捗率=" + cdiRate
//	 			+ ", 単体テスト進捗率=" + utRate + "]";
		 
		 return "Schedule\r\n{\r\n実際の着手日=" + new SimpleDateFormat("yyyy年MM月dd日").format(actualStartDate) + ",\r\n仕様理解進捗率=" + understandRate + ",\r\n単体テスト設計進捗率="
			+ utdRate + ",\r\n単体テスト設計レビュー進捗率=" + utdReviewRate + ",\r\n製造（コーディング）進捗率=" + codingRate + ",\r\nCDI進捗率=" + cdiRate
			+ ",\r\n単体テスト進捗率=" + utRate + "\r\n}";		 
	 }
	 
	 public static void main(String[] args) {
	 }
}
