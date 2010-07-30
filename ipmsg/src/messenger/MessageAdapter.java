package messenger;

public class MessageAdapter implements MessageListener{

    public MessageAdapter() {
    }
    
    public void openMsg(
    		final String host,
    		final String user){};
    		
    @Override
	public void addMember(String host, 
			String nickName, 
			String group,
			String addr, String signature) {
		
	}

	public void addMember(String host,
    		String nickName,
    		String group,
    		String addr, 
    		int absence){};
    		
    public void receiveMsg(
    		final String host,
    		final String user,
    		final String msg, 
    		final boolean lock){};
    		
    public void removeMember(final String host){};
}