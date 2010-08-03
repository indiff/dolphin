package com.tan.util;

import static com.tan.util.TanUtil.append;

import java.util.Locale;
public class Checker {
	
	public static final String LINE = System.getProperty("line.separator");
	
//	private StringManager sm = StringManager.getManager("com.tan");
	
	public Checker() {
	}
	
	/**
	 * check column.
	 * @param name
	 * @param userColumnName
	 * @param propNameInRow
	 * @param varNameInTable
	 * @param propNameInTable
	 * @param nullValue
	 * @param type
	 * @param minOccurs
	 * @return
	 */
	public String checkColumn(
			final String name,
			final String userColumnName,
			final String propNameInRow,
			final String varNameInTable,
			final String propNameInTable,
			final String nullValue,
			final String type,
			final String minOccurs
			) {
		StringBuilder b = new StringBuilder();
		if (StringUtil.isEmpty(name)) {
			append(b, "Name未设置"); return b.toString();
		} 
		String varNameInTableExpect = "column" + name ;
		String propNameInTableExpect = name + "Column";
		if(!name.equals(userColumnName)){
			append(b, "Generator_UserColumnName 有误，和 name 不相等.", LINE); 
		}
		if(!name.equals(propNameInRow)){
			append(b, "Generator_ColumnPropNameInRow 有误，和 name 不相等.", LINE); 
		}
		if(!varNameInTableExpect.equals(varNameInTable)){
			append(b, "varNameInTableExcept 有误，格式为: {columnName}", "正确值:", 
					varNameInTableExpect, LINE); 
		}
		if(!propNameInTableExpect.equals(propNameInTable)){
			append(b, "varNameInTableExcept 有误，格式为: {nameColumn}", "正确值:", 
					propNameInTableExpect, LINE); 
		}
		if(
			!StringUtil.isEmpty(nullValue) && 
			!"_throw".equals(nullValue) &&
			!"_empty".equals(nullValue)){
			append(b, "nullValue 有误，可选值: {_empty, _throw}，你输入的值: ", nullValue ," ,正确值:_empty", LINE); 
		}
		if(	    !"xs:decimal".equals(type) &&
				!"xs:string".equals(type) &&
				!"xs:boolean".equals(type) &&
				!"xs:dateTime".equals(type)
				){
			append(b, "type 有误，可选值: {xs:decimal, xs:string, xs:dateTime, xs:boolean}，你输入的值: ", type, LINE); 
		}
		if (!StringUtil.isNumber(minOccurs)) {
			append(b, "minOccurs 有误，值为数字", LINE); 
		}
		
		if (b.length() == 0) {
			return null;
		}
		return b.toString();
	}
	
	/**
	 * check row.
	 * @param name
	 * @param userTableName
	 * @param rowDeletedName
	 * @param rowChangedName
	 * @param rowClassName
	 * @param rowChangingName
	 * @param rowEvArgName
	 * @param rowEvHandlerName
	 * @param tableClassName
	 * @param tableVarName
	 * @param rowDeletingName
	 * @param tablePropName
	 * @return
	 */
	public String checkRow(
			final String name,
			final String userTableName, 
			final String rowDeletedName, 
			final String rowChangedName,
			final String rowClassName, 
			final String rowChangingName, 
			final String rowEvArgName, 
			final String rowEvHandlerName, 
			final String tableClassName, 
			final String tableVarName, 
			final String rowDeletingName,
			final String tablePropName) {
		StringBuilder b = new StringBuilder();

		if (StringUtil.isEmpty(name)) {
			append(b, "Name未设置");
			return b.toString();
		}
		// userTableName.
		if (!name.equals(userTableName)) {
			append(b, "警告:UserTableName 原值:", userTableName, ",正确值:", name, LINE);
		}
		// RowDeletedName.
		String rowDeletedNameExpect = name + "RowDeleted";
		if (!rowDeletedNameExpect.equals(rowDeletedName)) {
			append(b, "警告:RowDeletedName 原值:", rowDeletedName, ",正确值:", rowDeletedNameExpect, LINE);
		}
		// RowChangedName.
		String rowChangedNameExpect = name + "RowChanged";
		if (!rowChangedNameExpect.equals(rowChangedName)) {
			append(b, "警告:RowChangedName 原值:", rowChangedName, ",正确值:", rowChangedNameExpect, LINE);
		}

		// RowClassName.
		String rowClassNameExpect = name + "Row";
		if (!rowClassNameExpect.equals(rowClassName)) {
			append(b, "RowClassName有误,原值:", rowClassName, ",正确值:", rowClassNameExpect, LINE);
		}

		// RowChangingName.
		String rowChangingNameExpect = name + "RowChanging";
		if (!rowChangingNameExpect.equals(rowChangingName)) {
			append(b, "RowChangingName有误,原值:", rowChangingName, ",正确值:", rowChangingNameExpect, LINE);
		}

		// RowEvArgName.
		String rowEvArgNameExpect = name + "RowChangeEvent";
		if (!rowEvArgNameExpect.equals(rowEvArgName)) {
			append(b, "RowEvArgName有误,原值:", rowEvArgName, ",正确值:", rowEvArgNameExpect, LINE);
		}

		// RowEvHandlerName.
		String rowEvHandlerNameExpect = name + "RowChangeEventHandler";
		if (!rowEvHandlerNameExpect.equals(rowEvHandlerName)) {
			append(b, "RowEvHandlerName有误,原值:", rowEvHandlerName, ",正确值:", rowEvHandlerNameExpect, LINE);
		}

		// TableClassName.
		String tableClassNameExpect = name + "DataTable";
		if (!tableClassNameExpect.equals(tableClassName)) {
			append(b, "TableClassName有误,原值:", tableClassName, ",正确值:", tableClassNameExpect, LINE);
		}

		// TableVarName.
		String tableVarNameExpect = "table" + name;
		if (!tableVarNameExpect.equals(tableVarName)) {
			append(b, "TableVarName有误,原值:", tableVarName, ",正确值:", tableVarNameExpect, LINE);
		}

		// RowDeletingName.
		String rowDeletingNameExpect = name + "RowDeleting";
		if (!rowDeletingNameExpect.equals(rowDeletingName)) {
			append(b, "RowDeletingName有误,原值:", rowDeletingName, ",正确值:", rowDeletingNameExpect, LINE);
		}

		// TablePropName.
		if (!name.equals(tablePropName)) {
			append(b, "TablePropName有误,原值:", tablePropName, ",正确值:", name, LINE);
		}

		if (b.length() == 0) {
			return null;
		}

		return b.toString();
	}
	
	public boolean isRight(String result) {
		return result == null;
	}
	
	public static void main(String[] args) {
		StringManager smZh = StringManager.getManager("com.tan", Locale.SIMPLIFIED_CHINESE);
		StringManager smJa = StringManager.getManager("com.tan", Locale.JAPANESE);
		TanUtil.println("k001:", smZh.getString("k001"));
		TanUtil.println("k001:", smJa.getString("k001"));
	}
}
