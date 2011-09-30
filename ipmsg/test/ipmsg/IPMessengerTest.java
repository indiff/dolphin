package ipmsg;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

import org.junit.Test;

public class IPMessengerTest {

	static class Messenger extends IPMessenger {

		@Override
		public void addMember(String host, String nickName, String group,
				String addr, String signature, int absence) {
		}

		@Override
		public void openMsg(String host, String user) {
		}

		@Override
		public void receiveMsg(String host, String user, String msg,
				boolean lock) {
		}

		@Override
		public void removeMember(String host) {
		}

	}

	Messenger messenger = new Messenger();

	private String makeTelegram(final int command, final String supplement) {

		StringBuffer sb = new StringBuffer();

		sb.append(Constants.PROTOCOL_VER);
		sb.append(":");
		sb.append(new Date().getTime() / 1000);
		sb.append(":");
		sb.append("username");
		sb.append(":");
		sb.append("hostname");
		sb.append(":");
		sb.append(command);
		sb.append(":");
		sb.append(supplement);
		//	        sb.append(":");
		//	        sb.append(signature);

		return sb.toString();
	}

	private void broadcastMsg(final String msg) throws IOException {
		DatagramSocket socket      = new DatagramSocket( /*7788*/ );
		String message = msg;
		
		System.out.println( msg );
		byte[] byteMsg = message.getBytes("gb2312");
//		226.81.9.8
		DatagramPacket packet = new DatagramPacket(byteMsg, byteMsg.length,
//				InetAddress.getByName("255.255.255.255"), 7878);
				InetAddress.getByName("10.20.9.164"), 2425);
		socket.send(packet);
	}
	
    

	@Test
	public void testLogin() {
		try {
			broadcastMsg(makeTelegram(Constants.IPMSG_BR_ENTRY|Constants.IPMSG_BROADCASTOPT,
			        "何骧1"+"\0"+"牛逼1"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
