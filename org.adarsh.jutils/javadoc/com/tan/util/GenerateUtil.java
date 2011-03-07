package com.tan.util;



import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.HashMap;
import java.util.Map;

import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.Type;
public class GenerateUtil
{
    private static final String L = "\r\n";
	/**
     * style 0: 假数据, default.
     * style 1: vo.setA(po.getA())
     */
    private int style = 1;
    private String javaName;
    private Object indent = "	";
    public String getJavaName() {
		return javaName;
	}

	public void setJavaName(String javaName) {
		this.javaName = javaName;
	}

	private Map cache = new HashMap();
    
    public GenerateUtil() {
        cache.clear();
    }
    
    /**
     * 生成 fields 对应的  setter 方法.
     * @param fields
     * @return
     */
    public String generateSetter(final FieldDoc[] fields)
    {
        if (null != fields && fields.length > 0)
        {
            StringBuilder builder = new StringBuilder();
            String name,comment;
            
            if (null != javaName) {
            	TanUtil.appendln(builder, 
            			new Object[]{
            			indent , javaName, " vo = new ", javaName , "();"
            	});
            	TanUtil.appendln(builder, 
            			new Object[]{
            			indent ,javaName, " po = new ", javaName , "();"
            	});
            }
            for (int i = 0; i < fields.length; i++) {
                name = fields[i].name();
                comment = fields[i].commentText();
                
                if (TanUtil.isNotNull(comment) && TanUtil.isNotNull(name)) {
                    // 如果注释中含有回车换行等信息将其过滤.
                    comment = comment.replaceAll(". |\\.|\r\n|\n", "");
                    TanUtil.appendln(builder, 
                    		new Object[]{
                    		indent ,"// 设置 ", comment,L, // 注释.
                            generateSetter(fields[i])
                    		}
                    );
                }
            }
            
            if (builder.length() > 0 ) {
                setClipboardData(builder);
                return builder.toString();
            }
        }
        return null;
    }
    
    /**
     * 生成 fields 对应的  getter 方法.
     * @param fields
     * @return
     */
    public String generateGetter(final FieldDoc[] fields)
    {
        if (null != fields && fields.length > 0)
        {
            StringBuilder builder = new StringBuilder();
            String name,comment;
            for (int i = 0; i < fields.length; i++) {
                name = fields[i].name();
                comment = fields[i].commentText();
                
                if (TanUtil.isNotNull(comment) && TanUtil.isNotNull(name)) {
                    // 如果注释中含有回车换行等信息将其过滤.
                    comment = comment.replaceAll(". |\\.|\r\n|\n", "");
                    TanUtil.appendln(builder, 
                            new Object[]{
                    		"// 获取 ", comment,L, // 注释.
                            generateGetter(fields[i])
                    }
                    );
                }
            }
            
            if (builder.length() > 0 ) {
                setClipboardData(builder);
                return builder.toString();
            }
        }
        return null;
    }
    
    /**
     * 根据 FieldDoc 生成对应的  getter 方法.
     * @param fieldDoc
     * @return
     */
    private Object generateGetter(FieldDoc fieldDoc)
    {
        StringBuilder b = new StringBuilder();
        String name = fieldDoc.name();
        Object qualifiedName = upperCaseFirstChar(name);
        TanUtil.append(b, new Object[]{
        		"vo.get", qualifiedName, "();"
        });
        return b;
    }

    /**
     * 根据 FieldDoc 生成对应的  setter 方法.
     * @param fieldDoc
     * @return
     */
    private Object generateSetter(final FieldDoc fieldDoc)
    {
        StringBuilder b = new StringBuilder();
        String name = fieldDoc.name();
        Object qualifiedName = upperCaseFirstChar(name);
        Object dummy = "";
        if (style == 0) {
            dummy = dummy(fieldDoc); // 根据参数类型设置假数据, default.
        } else if (style == 1) {
            dummy = "po.get" + qualifiedName + "()"; // vo.setA(po.getA()) 方式.
        }
        TanUtil.append(b,
        		new Object[]{
        		indent, "vo.set", qualifiedName, "(", dummy, ");"
        }
        );
        return b;
    }
    
    /**
     * 根据 FieldDoc 生成对应的  dummy 数据.
     * @param field
     * @return
     */
    private static Object dummy(FieldDoc field)
    {   
        Type type = field.type();
        if (null == type) return "";
        String name = type.typeName();
        if ("String".equals(name)) {
            String comment = field.commentText();
            if (null != comment) {
                comment = comment.trim();
            }
            return "\""  +  comment + "\"";
        }
        else if ("int".equals(name)) {return "1";} //  /*int*/
        else if ("Integer".equals(name)) {return "new Integer(1)";}
        else if ("byte".equals(name)) {return "(byte)1";} //  /*byte*/
        else if ("Byte".equals(name)) {return "(byte)1";} //  /*byte*/
        else if ("double".equals(name)) {return "1D";}// /*double*/
        else if ("Double".equals(name)) {return "1D";}// /*double*/
        else if ("float".equals(name)) {return "1F";}// /*float*/
        else if ("Float".equals(name)) {return "1F";}// /*float*/
        else if ("short".equals(name)) {return "(short)1";} //  /*byte*/
        else if ("Short".equals(name)) {return "(short)1";} //  /*byte*/
        else if ("long".equals(name)) {return "1L";} //  /*byte*/
        else if ("Long".equals(name)) {return "1L";} //  /*byte*/
        else if ("Date".equals(name)) {return "new Date()";} // /*Date*/
        else if ("BigDecimal".equals(name)) {return "new BigDecimal(\"1\")";}
        else if ("BigInteger".equals(name)) {return "new BigInteger(\"1\")";}
        return "new " + type.simpleTypeName() + "()";
    }
    
    /**
     * 转化第一个字母大写.
     * @param name
     * @return
     */
    private Object upperCaseFirstChar(final String name)  {
        if (name != null && cache.containsKey(name)) {
            return cache.get(name);
        }
        String value = Character.toUpperCase(name.charAt(0))  + name.substring(1);
        cache.put(name, value);
        return value;
    }
    
    /**
     * 设置数据到剪贴板.
     * @param buffer
     */
    public void setClipboardData(CharSequence buffer) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(buffer.toString());
        clipboard.setContents(selection, selection);
    }

    public int getStyle()
    {
        return style;
    }

    public void setStyle(int style)
    {
        this.style = style;
    }

}
