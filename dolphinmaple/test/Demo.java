import java.net.URL;
import java.net.URLConnection;

import sun.net.ftp.FtpClient;


public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Throwable{
		// TODO Auto-generated method stub
		String address = "ftp://10.30.1.168/ITS/%B3%A3%D3%C3%CF%C2%D4%D8/Office%20Professional%20Plus%202007_cn.7z";
		
		URL url =  new URL(address);
		
		URLConnection conn = url.openConnection();
		
		int length = conn.getContentLength();
		
		System.out.println(length);
		
		FtpClient client = new FtpClient();
		
		client.openServer("10.81.34.230", 21);
		client.login("admin", "admin");
		
/*		client.openServer("10.30.1.168", 21);
		client.login("admin", "admin");
*/		
		int response = client.readServerResponse();
		
		System.out.println(response);
	}

}
