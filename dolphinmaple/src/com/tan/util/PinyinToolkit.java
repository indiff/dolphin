package com.tan.util;

public class PinyinToolkit {
	private PinyinToolkit() {
	}

	// 字母Z使用了两个标签，这里有２７个值
	//i, u, v都不做声母, 跟随前面的字母
	private static final char[] chartable = { '啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈',
			'哈', '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然', '撒', '塌', '塌',
			'塌', '挖', '昔', '压', '匝', '座' };
	//首字母表
	private static char[] initialtable = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z' };

//	private static int[] table = new int[27];
	private static int[] table = { 45217, 45253, 45761, 46318, 46826, 47010,
			47297, 47614, 47614, 48119, 49062, 49324, 49896, 50371, 50614,
			50622, 50906, 51387, 51446, 52218, 52218, 52218, 52698, 52980,
			53689, 54481, 55289 };

	//初始化
//	static {
//		for (int i = 0; i < 27; ++i) {
//			table[i] = gbValue(chartable[i]);
//		}
//	}

	/**
	 * 根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串
	 *
	 * @param SourceStr 源字符串
	 * @return 拼音首字母的字符串
	 */
	public static String cn2Pinyin(String SourceStr) {
		String r = "";
		int StrLength = SourceStr.length();
		int i;
		try {
			for (i = 0; i < StrLength; i++) {
				r += char2Initial(SourceStr.charAt(i));
			}
		} catch (Exception e) {
			r = "";
		}
		return r;
	}

	//------------------------private 方法区------------------------
	/**
	 * 输入字符,得到他的声母,英文字母返回对应的大写字母,其他非简体汉字返回 '0'
	 *
	 * @param ch 字符
	 * @return 拼音首字母
	 */
	private static char char2Initial(char ch) {
		if (ch >= 'a' && ch <= 'z')
			return (char) (ch - 'a' + 'A');
		if (ch >= 'A' && ch <= 'Z') return ch;
		int gb = gbValue(ch);
		if (gb < table[0]) return ch;
		int i;
		for (i = 0; i < 26; ++i) {
			if (match(i, gb)) break;
		}
		if (i >= 26) return ch;
		else return initialtable[i];
	}

	private static boolean match(int i, int gb) {
		if (gb < table[i])
			return false;
		int j = i + 1;
		//字母Z使用了两个标签
		while (j < 26 && (table[j] == table[i]))
			++j;
		if (j == 26)
			return gb <= table[j];
		else
			return gb < table[j];

	}

	/**
	 * 取出汉字的编码
	 * cn 汉字
	 */
	private static int gbValue(char ch) {
		String str = new String();
		str += ch;
		try {
			byte[] bytes = str.getBytes("GB2312");
			if (bytes.length < 2)
				return 0;
			return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
		} catch (Exception e) {
			return 0;
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(cn2Pinyin("不"));
//		System.out.println(cn2Pinyin(""));
//		System.out.println(cn2Pinyin(" 放大abc123"));
	}
}