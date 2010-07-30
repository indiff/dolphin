package ipmsg;

/**
 * IPMessengerクラスで用いる定数を定義します。
 * 
 * @author Naoki Takezoe
 */
public final class Constants {
	private Constants(){}
	
	public static final String DATE_FORMAT = "yyyy年MM月dd日 hh时mm分ss秒";
    // command 
    public static final int IPMSG_NOOPERATION = 0x00000000;
    public static final int IPMSG_BR_ENTRY    = 0x00000001;
    public static final int IPMSG_BR_EXIT     = 0x00000002;
    public static final int IPMSG_ANSENTRY    = 0x00000003;
    public static final int IPMSG_BR_ABSENCE  = 0x00000004;

    public static final int IPMSG_BR_ISGETLIST  = 0x00000010;
    public static final int IPMSG_OKGETLIST     = 0x00000011;
    public static final int IPMSG_GETLIST       = 0x00000012;
    public static final int IPMSG_ANSLIST       = 0x00000013;
    public static final int IPMSG_BR_ISGETLIST2 = 0x00000018;

    public static final int IPMSG_SENDMSG    = 0x00000020;
    public static final int IPMSG_RECVMSG    = 0x00000021;
    public static final int IPMSG_READMSG    = 0x00000030;
    public static final int IPMSG_DELMSG     = 0x00000031;
    public static final int IPMSG_ANSREADMSG = 0x00000032;

    public static final int IPMSG_GETINFO  = 0x00000040;
    public static final int IPMSG_SENDINFO = 0x00000041;

    public static final int IPMSG_GETABSENCEINFO  = 0x00000050;
    public static final int IPMSG_SENDABSENCEINFO = 0x00000051;
    
    // option for all command
    public static final int IPMSG_ABSENCEOPT = 0x00000100;
    public static final int IPMSG_SERVEROPT  = 0x00000200;
    public static final int IPMSG_DIALUPOPT  = 0x00010000;
    
    // option for send command
    public static final int IPMSG_SENDCHECKOPT = 0x00000100;
    public static final int IPMSG_SECRETOPT    = 0x00000200;
    public static final int IPMSG_BROADCASTOPT = 0x00000400;
    public static final int IPMSG_MULTICASTOPT = 0x00000800;
    public static final int IPMSG_NOPOPUPOPT   = 0x00001000;
    public static final int IPMSG_AUTORETOPT   = 0x00002000;
    public static final int IPMSG_RETRYOPT     = 0x00004000;
    public static final int IPMSG_PASSWORDOPT  = 0x00008000;
    public static final int IPMSG_NOLOGOPT     = 0x00020000;
    public static final int IPMSG_NEWMUTIOPT   = 0x00040000;
    public static final int IPMSG_NOADDLISTOPT = 0x00080000;
    public static final int IPMSG_READCHECKOPT = 0x00100000;
    public static final int IPMSG_SECRETEXOPT  =(IPMSG_READCHECKOPT|IPMSG_SECRETOPT);
    
    // Private constants
	public static final int PORT         = 2426;
//	public static final int PORT         = 2425;
    public static final int BUFSIZE      = 8192;
    public static final int PROTOCOL_VER = 1;

}
