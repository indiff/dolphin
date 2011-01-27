package com.tan.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class StringUtil {
	final static int ENC = 764;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String encode = encode("www.wujie.net/news.htm");// "ʋʋʋ˒ʋʉʖʕʙ˒ʒʙʈ˓ʒʙʋʏ˒ʔʈʑ ";

		String b = decode(encode);

		System.out.println(encode);
		System.out.println(b);
		System.out.println(now());
		System.out.println(greet());

	}

	public final static boolean isEmpty(final String value) {
		return null == value || value.trim().length() == 0;
	}

	public static String encode(String value) {
		char[] chars = value.toCharArray();

		StringBuilder b = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {

			b.append((char) (chars[i] ^ ENC));
		}
		return b.toString();
	}

	public static String decode(String encode) {
		char[] chars = encode.toCharArray();

		StringBuilder b = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {
			b.append((char) (chars[i] ^ ENC));
		}
		return b.toString();
	}

	/**
	 * "http://localhost/dm/fdafdsajlfkdsafdsa/k.jsp?p=2"; -->
	 * http://localhost/dm/fdafdsajlfkdsafdsa/ "http://www.baidu.com/"; -->
	 * http://www.baidu.com/
	 * 
	 * @param url
	 * @return
	 */
	public static String getSite(final String url) {
		int idx = url.lastIndexOf("/");
		if (idx > 7 && idx >= 0) {
			String last = url.substring(idx + 1);
			if (last.matches("[a-zA-Z0-9]+")) {
				last = null;
				return url + '/';
			}
			return url.substring(0, idx + 1);
		}
		if (url.length() - 1 == idx) {
			return url;
		}
		return url + '/';
	}

	public static String url(final String url) {
		int type = -1;
		try {
			type = Integer.parseInt(url);
		} catch (Throwable e) {
		}
		if (type == 0) {
			return "http://www.wujie.net/news.htm";
		} else if (type == 1) {
			return "http://www.soundofhope.org/default.asp";
		} else if (type == 2) {
			return "http://www.tiananmenmother.org/index.htm";
		} else if (type == 3) {
			return "http://www.kanzhongguo.com/";
		} else if (type == 4) {
			return "http://www.ntdtv.com/";
		} else if (type == 5) {
			return "http://www.dajiyuan.com/";
		} else if (type == 6) {
			return "http://www.rfa.org/mandarin";
		} else if (type == 7) {
			return "http://www.aboluowang.com/";
		} else if (type == 8) {
			return "http://www.youmaker.com/video/index.html";
		} else if (type == 9) {
			return "http://gardennetworks.com/";
		} else if (type == 10) {
			return "http://heartyit.com/chinese.html";
		} else if (type == 11) {
			return "http://www.huaxiabao.org/";
		}
		return url;
	}

	private static final DateFormat CHINA_FORMAT = new SimpleDateFormat(
			"yy-MM-dd kk:mm:ss", Locale.CHINA);
	static {
		CHINA_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
	}
	private static final Pattern TIME_PATTERN = Pattern
			.compile("^\\d{2}-\\d{2}-\\d{2} (\\d{2}):\\d{2}:\\d{2}$");

	public static String now() {
		return CHINA_FORMAT.format(new Date());
	}

	public static String greet() {
		String now = StringUtil.now();
		String year = now.substring(0, 2);
		String month = now.substring(3, 5);
		String date = now.substring(6, 8);
		String hour = now.substring(9, 11);
		// Matcher matcher = TIME_PATTERN.matcher(now);
		// if (matcher.find()) {
		// String hour = matcher.group(1);
		
		String[] newYearGreets = {
				"远方的你是否无恙？在遥远的思念里，改变的是我的容颜，不变的是永远爱你的心！爱人，真心愿你新年快乐！",

				"日出+日落=朝朝暮暮，月亮+星星=无限思念，风花+雪月=柔情蜜意，流星+心语=祝福万千，祝你有个快乐的新年！",

				"我最亲爱的朋友：在新年来临之际，祝愿上帝保佑您！观音菩萨护住您！财神抱住您！爱神射住您！食神吻住您！",

				"我把新世纪的祝福和希望，悄悄地放在将融的雪被下，让它们沿着春天的秧苗生长，送给你满年的丰硕与芬芳！",

				"你在新年夜被通缉了，你的罪行是：1.对朋友太好，又够义气；2.青春的面孔，灿烂的笑容。本庭现判决如下：罚你终身做我的朋友，不得上诉！",

				"金钱是一种有用的东西，但是只有在你觉得知足的时候，它才会带给你快乐。所以你应该把多余的钱交给我，这样我们两个都会感到快乐了！",

				"今年过节不收礼，其实给点也可以。十块八块不嫌弃，十万八万过得去．你要真是没的送，短信一条也可以。新年快乐！",

				"一直很想跟你说，但不知你会不会觉得我太心急。我又怕被别人抢先一步，所以我决定鼓起勇气告诉你：新年快乐！",

				"祝您在新年的假期里：小酒喝到六亲不认，香烟抽到同归于尽，麻将搓到昼夜不分，跳舞跳到筋疲力尽，吹牛吹到自己不信。",

				"听说过年你要到我们家做客，我弟弟会代我去接你，为便于确认身份，请你左手拿两条上等烟，右手提两瓶茅台酒。",

				"祝你正财、偏财、横财，财源滚滚；亲情、友情、私情，情情如意；官运、财运、桃花运，运运亨通。",
		};
		
		String newYearGreet = "";
		if (year.equals("11") && (month.equals("01") || month.equals("02"))) {
			String timeMillis = String.valueOf(System.currentTimeMillis());
			char random = timeMillis.charAt(timeMillis.length() - 1);
			switch (random) {
				case '0': { newYearGreet = newYearGreets[0]; break; }
				case '1': { newYearGreet = newYearGreets[1]; break; }
				case '2': { newYearGreet = newYearGreets[2]; break; }
				case '3': { newYearGreet = newYearGreets[3]; break; }
				case '4': { newYearGreet = newYearGreets[4]; break; }
				case '5': { newYearGreet = newYearGreets[5]; break; }
				case '6': { newYearGreet = newYearGreets[6]; break; }
				case '7': { newYearGreet = newYearGreets[7]; break; }
				case '8': { newYearGreet = newYearGreets[8]; break; }
				case '9': { 
					random = timeMillis.charAt(timeMillis.length() - 2);
					int value = Character.getNumericValue(random);
					if (value <= 5) {
						newYearGreet = newYearGreets[9];
					} else {
						 newYearGreet = newYearGreets[10];
					}
					 break; }
				default : newYearGreet = newYearGreets[10];
			}
		}

		if (hour != null) {
			int h = Integer.parseInt(hour);

			if (h >= 0 && h <= 4) {
				return "深夜了，注意休息!" + newYearGreet;
			} else if (h > 4 && h <= 6) {
				return "凌晨好!" + newYearGreet;
			} else if (h >= 7 && h <= 12) {
				return "早上好!" + newYearGreet;
			} else if (h == 12) {
				return "中午好，吃饭！" + newYearGreet;
			} else if (h > 12 && h <= 13) {
				return "中午，可以听歌，午休一下！" + newYearGreet;
			} else if (h > 13 && h <= 16) {
				return "下午好!" + newYearGreet;
			} else if (h > 16 && h <= 17) {
				return "傍晚好!快吃晚饭了!" + newYearGreet;
			} else if (h > 17 && h <= 19) {
				return "晚饭过后,可以看下新闻联播!" + newYearGreet;
			} else if (h > 19 && h <= 21) {
				return "晚上好,可以看下电视剧，电影!" + newYearGreet;
			} else if (h > 21 && h <= 22) {
				return "可以睡觉了，晚上!" + newYearGreet;
			} else if (h >= 22 && h <= 24) {
				return "很晚了可以休息了!" + newYearGreet;
			}
		}
		// }
		// matcher = null;
		return "";
	}

}
