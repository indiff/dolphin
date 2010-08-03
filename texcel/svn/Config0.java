import java.util.HashMap;
import java.util.Map;
public class Config0 {
		private Map<String,String> map;
		
		public Config0() {
			map = new HashMap<String, String>();
			map.put("svn-path", 
					"C:\\tools\\svn-syn\\svn\\");
			
			map.put("C:\\tools\\svn-syn\\mas\\qa", 
					"C:\\tools\\svn-syn\\sh\\qa");
		}
}