package util;

import static ipmsg.Constants.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Util {
	private Util(){}
	
	private static HashMap<String, DateFormat> formats = 
		new HashMap<String, DateFormat>();
	private static DateFormat popularDateFormat ;
	
	public static String format(final Date d, final String format){
		if (formats.get(format) != null) {
			if (popularDateFormat == null) 
				popularDateFormat = formats.get(format);
			return popularDateFormat.format(d);
		} 
		DateFormat f = new SimpleDateFormat(format);
		formats.put(format, f);
		return f.format(d);
	}
	
	public static String currentTime(){
		return format(new Date(), DATE_FORMAT);
	}
}
