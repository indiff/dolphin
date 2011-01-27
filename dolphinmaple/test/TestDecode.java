
public class TestDecode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String encode = encode("www.wujie.net/news.htm");//"ʋʋʋ˒ʋʉʖʕʙ˒ʒʙʈ˓ʒʙʋʏ˒ʔʈʑ ";
		
		String b = decode(encode);
		
		System.out.println(b);
	}

	private static String encode(String value) {
		char[] chars = value.toCharArray();
		
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {
			b.append((char)(chars[i] ^ 764));
		}
		return b.toString();
	}
	
	private static String decode(String encode) {
		char[] chars = encode.toCharArray();
		
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {
			b.append((char)(chars[i] ^ 764));
		}
		return b.toString();
	}

}
