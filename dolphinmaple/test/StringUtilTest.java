import java.util.Random;

import com.tan.util.StringUtil;


public class StringUtilTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println(StringUtil.now());
//		System.out.println(StringUtil.now().substring(9, 11));
//		System.out.println(StringUtil.now().substring(0, 8));
		
		String random = String.valueOf(System.currentTimeMillis());
		
		System.out.println(Character.getNumericValue(random.charAt(random.length() - 1)) );
		System.out.println(StringUtil.greet());
		
		
//		for (int i = 0; i < 10; i++) {
//			String value = "case '" + i + "': { newYearGreet = newYearGreets[" + i + "]; break; }";
//			
//			System.out.println(value);
//		}
	}

}
