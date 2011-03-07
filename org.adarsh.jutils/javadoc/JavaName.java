import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * 类注释
 * 
 * @author: 谭元吉
 */
public class JavaName
{
    /**
     * 字段注释1
     */
    private String field1;
    /**
     * 字段注释2
     */
    private int field2;
    /**
     * 字段注释3
     */
    private double field3;
    /**
     * 字段注释4
     */
    private short field5;
    /**
     * 字段注释6
     */
    private byte field6;
    /**
     * 字段注释7
     */
    private float field7;
    /**
     * 字段注释8
     */
    private Date field8;
    /**
     * 字段注释9
     */
    private BigDecimal field9;
    /**
     * 字段注释10
     */
    private BigInteger field10;
    /**
     * 字段注释11
     */
    private Integer field11;
    /**
     * 字段注释12
     */
    private Root field12;

    /**
     * 方法注释1
     * 
     * @param String
     * @return void
     */
    public void setField1(String field1)
    {
        this.field1 = field1;
    }

    /**
     * 方法注释2
     * 
     * @param String
     * @return void
     */
    public void setField2(int field2)
    {
        this.field2 = field2;
    }

    /**
     * 内部类注释
     * 
     * @author: 谭元吉
     */
    public static class Inner
    {
    }

    /**
     * 静态主方法注释.
     * 
     * @author: 谭元吉
     */
    public static void main(String[] args)
    {
        JavaName vo = new JavaName();
        JavaName po = new JavaName();
        
        
    }

    public double getField3()
    {
        return field3;
    }

    public void setField3(double field3)
    {
        this.field3 = field3;
    }

    public short getField5()
    {
        return field5;
    }

    public void setField5(short field5)
    {
        this.field5 = field5;
    }

    public byte getField6()
    {
        return field6;
    }

    public void setField6(byte field6)
    {
        this.field6 = field6;
    }

    public float getField7()
    {
        return field7;
    }

    public void setField7(float field7)
    {
        this.field7 = field7;
    }

    public Date getField8()
    {
        return field8;
    }

    public void setField8(Date field8)
    {
        this.field8 = field8;
    }

    public BigDecimal getField9()
    {
        return field9;
    }

    public void setField9(BigDecimal field9)
    {
        this.field9 = field9;
    }

    public BigInteger getField10()
    {
        return field10;
    }

    public void setField10(BigInteger field10)
    {
        this.field10 = field10;
    }

    public Integer getField11()
    {
        return field11;
    }

    public void setField11(Integer field11)
    {
        this.field11 = field11;
    }

    public Root getField12()
    {
        return field12;
    }

    public void setField12(Root field12)
    {
        this.field12 = field12;
    }

    public String getField1()
    {
        return field1;
    }

    public int getField2()
    {
        return field2;
    }
    
    
}
