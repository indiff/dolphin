package com.tan.bean;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;

import lombok.Data;

import com.tan.util.TanUtil;

@SuppressWarnings("rawtypes")
public final @Data
class QaInfo implements Comparable {
	/** ＱＡ管理番号. **/
	private String qaNum;

	/** 発生工程. **/
	private String happendProject;

	/** 発生日. **/
	private Date happendDate;

	/** 質問者会社名. **/
	private String questionCompany;

	/** 質問者氏名. **/
	private String name;

	/** 開発分類. **/
	private String developKind;

	/** サブシステム名. **/
	private String subSystem;

	/** プログラムID. **/
	private String programId;

	/** プログラム名. **/
	private String programName;

	/** 質問区分. **/
	private String kubun;

	/** 発信内容. **/
	private String askContent;

	/** 緊急度. **/
	private String urgency;

	/** 影響度. **/
	private String effect;

	/** 回答期限. **/
	private Date dueTime;

	/** 質問レベル. **/
	private String askLevel;

	/** 評価者. **/
	private String evaluator;

	/** （Ａ）　回答内容. **/
	private String answerContent;

	/** 回答日. **/
	private Date answerDate;

	/** 回答者. **/
	private String answerName;

	/** Leader. **/
	private String leader;

	@Override
	public int compareTo(Object o) {
		if (o == null)
			return 1;
		QaInfo info = (QaInfo) o;
		return this.name.compareTo(info.getName());
	}

	public static void main(String[] args) throws Throwable {
		for (int size = 0; size < 100; size++) {

			for (int i = 0; i < size; i++) {
				showPercent(i + 1, size);
			}
		}
	}

	private static synchronized void showPercent(final int index, final int size) {

		BigDecimal i = new BigDecimal(index);
		BigDecimal s = new BigDecimal(size);
		MathContext mc = new MathContext(3, RoundingMode.HALF_UP);
		TanUtil.println(i.divide(s, mc).multiply(new BigDecimal(100), mc) + "%");
	}
}
