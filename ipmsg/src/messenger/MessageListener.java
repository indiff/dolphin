package messenger;

public interface MessageListener {
    void openMsg(final String host,
    			 final String user);
    
    void addMember(final String host,
		    	   final String nickName,
		    	   final String group,
		    	   final String addr, 
		    	   final int absence);
    
    void receiveMsg(final String host,
    			    final String user,
    			    final String msg, 
    			    final boolean lock);
    
    void removeMember(final String host);
}