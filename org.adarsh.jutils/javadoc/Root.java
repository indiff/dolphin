/**
 * Provides external entry points (tool and programmatic)
 * for the javadoc program.
 *
 * @since 1.4
 * @author dolphin.
 */
public class Root {
	
	public static int OPERATE_TYPE = 1;
	/**
	 * 姓名
	 */
	private String name;
	
	
    /**
     * Constructor should never be called.
     */
	public Root() {}

	
	/**
	 * set the n for the name.
	 * @param n
	 */
	public void setName(String n) {name = n;}
	
	/**
	 * get the name.
	 * @return
	 */
	public String getName() {return name;}

	
	public static void main(String[] args) {
		
		System.out.println("連絡可能時間帯（自）. を設定する.\r\n".replaceAll(". |\\.|\r\n", ""));
	}
	
	
}
