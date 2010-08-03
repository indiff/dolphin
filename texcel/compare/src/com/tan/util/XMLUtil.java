package com.tan.util;

import static com.tan.util.StringUtil.isEmpty;
import static com.tan.util.TanUtil.println;
import static com.tan.util.TanUtil.warnln;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.tan.bean.Dataset;
import com.tan.bean.Module;

public class XMLUtil {
//	final static String xmlPath = "c:/compare/demo.xml";
	 final static String xmlPath = "c:/compare/SMSIMtnDataSet.xsd";
	 
	 final static String xpathExpression = "//xs:sequence//xs:element";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Document doc = getDocument(xmlPath);
		List<?> elements1 = doc.selectNodes("/xs:schema/xs:element/xs:complexType/xs:choice/xs:element");
		
		int index = 0;
		for (Iterator<?> iter1 = elements1.iterator();iter1.hasNext();) {
			index++;
			Node n = (Node) iter1.next();
			String name = n.valueOf("@name");
			println(name);
			
//			Document d = n.getDocument();
			List<?> elements2 = doc.selectNodes(n.getUniquePath() + "/xs:complexType/xs:sequence//xs:element");
			warnln(elements2.size());
		}
		
//		Checker  chk = new Checker();
		
//		analyseRow(doc, chk);
		
//		analyseColumn(doc, chk);
	}

	@SuppressWarnings("unchecked")
	public static void analyseColumnOld(Document doc, Checker chk) {
		List<Node> nodes = null;
		try {
			nodes = doc.selectNodes("//xs:sequence//xs:element");
		} catch (Exception e) {
			warnln("No Elements. //xs:sequence//xs:element");return;
		}
		checkList("搜寻xs:element失败!", nodes);

		println("sequence --> element 个数: " + nodes.size());

		int index = 0;
		for (Iterator<Node> iter = nodes.iterator(); iter.hasNext();) {
			int idx = ++index;
			Node n = iter.next();
			// printNodeInfo(n);
			println(n.getClass(),'\t', n.getUniquePath());
			
			// 項目名.											
			String name = n.valueOf("@name");
			String userColumnName = n.valueOf("@msprop:Generator_UserColumnName");
			String columnPropNameInRow = n.valueOf("@msprop:Generator_ColumnPropNameInRow");
			String varNameInTable = n.valueOf("@msprop:Generator_ColumnVarNameInTable");
			String propNameInTable = n.valueOf("@msprop:Generator_ColumnPropNameInTable");
			String nullValue = n.valueOf("@msprop:nullValue");
			// データ型.											
			String type = n.valueOf("@type");
			String minOccurs = n.valueOf("@minOccurs");
			
//			println("name: ", name);
//			println("msprop:Generator_UserColumnName:", userColumnName);
//			println("msprop:Generator_ColumnPropNameInRow:", columnPropNameInRow);
//			println("msprop:Generator_ColumnVarNameInTable :", varNameInTable);
//			println("msprop:Generator_ColumnPropNameInTable :", propNameInTable);
//			println("nullValue :", nullValue);
//			println("type :", type);
//			println("minOccurs :", minOccurs);
			
			String result = chk.checkColumn(
					name, 
					userColumnName,
					columnPropNameInRow, 
					varNameInTable,
					propNameInTable, 
					nullValue, 
					type,
					minOccurs);
			
			if (!chk.isRight(result)) {
				warnln("*************" +  name + ":\r\n" + result + "*************");
			}
		}
	}

	public static Dataset[] analyseRow(Document doc, Checker chk) {
		List<?> parents = null;
		try {
//			parents = doc.selectNodes("//xs:choice/xs:element");
			parents = doc.selectNodes("/xs:schema/xs:element/xs:complexType/xs:choice/xs:element");
		} catch (Exception e) {
			// Ignore.
			warnln("No Elements. //xs:choice//xs:element.");
			return null;
		}
		int size = parents.size();
		Dataset[] datasets = new Dataset[size];
		int index = 0;
		for (Iterator<?> iter = parents.iterator(); iter.hasNext();) {
			int idx = index++;
			Node n = (Node) iter.next();
			
			// TODO analyse column.
			List<Module> modules = analyseColumn(doc, chk, n.getUniquePath() + "/xs:complexType/xs:sequence//xs:element");
			
			datasets[idx] = new Dataset();
			datasets[idx].setNo(idx);
			datasets[idx].setModule(modules);
			
			String name = n.valueOf("@name");	datasets[idx].setTable(name);
			String userTableName = n.valueOf("@msprop:Generator_UserTableName");
			String rowDeletedName = n.valueOf("@msprop:Generator_RowDeletedName");
			String rowChangedName = n.valueOf("@msprop:Generator_RowChangedName");
			String rowClassName = n.valueOf("@msprop:Generator_RowClassName");
			String rowChangingName = n.valueOf("@msprop:Generator_RowChangingName");
			String rowEvArgName = n.valueOf("@msprop:Generator_RowEvArgName");
			String rowEvHandlerName = n.valueOf("@msprop:Generator_RowEvHandlerName");
			String tableClassName = n.valueOf("@msprop:Generator_TableClassName");
			String tableVarName = n.valueOf("@msprop:Generator_TableVarName");
			String rowDeletingName = n.valueOf("@msprop:Generator_RowDeletingName");
			String tablePropName = n.valueOf("@msprop:Generator_TablePropName");
			
			String result = chk.checkRow(name,
					userTableName, 
					rowDeletedName, 
					rowChangedName,
					rowClassName, 
					rowChangingName, 
					rowEvArgName, 
					rowEvHandlerName, 
					tableClassName, 
					tableVarName, 
					rowDeletingName, 
					tablePropName);
			
			datasets[idx].setRemark(result);
		}
		return datasets;
	}	
	
	public static Dataset[] analyseRow(Document doc, Checker chk, boolean logger) {
		List<?> parents = null;
		try {
//			parents = doc.selectNodes("//xs:choice/xs:element");
			parents = doc.selectNodes("/xs:schema/xs:element/xs:complexType/xs:choice/xs:element");
		} catch (Exception e) {
			// Ignore.
			warnln("No Elements. //xs:choice//xs:element.");
			return null;
		}
		int size = parents.size();
		Dataset[] datasets = new Dataset[size];
		println("choice -> element 个数: " + size);
		int index = 0;
		for (Iterator<?> iter = parents.iterator(); iter.hasNext();) {
			int idx = index++;
			Node n = (Node) iter.next();
			
			// TODO analyse column.
			List<Module> modules = analyseColumn(doc, chk, xpathExpression);
			
			datasets[idx] = new Dataset();
			datasets[idx].setNo(idx);
			datasets[idx].setModule(modules);
			
			String name = n.valueOf("@name");	datasets[idx].setTable(name);
			String userTableName = n.valueOf("@msprop:Generator_UserTableName");
			String rowDeletedName = n.valueOf("@msprop:Generator_RowDeletedName");
			String rowChangedName = n.valueOf("@msprop:Generator_RowChangedName");
			String rowClassName = n.valueOf("@msprop:Generator_RowClassName");
			String rowChangingName = n.valueOf("@msprop:Generator_RowChangingName");
			String rowEvArgName = n.valueOf("@msprop:Generator_RowEvArgName");
			String rowEvHandlerName = n.valueOf("@msprop:Generator_RowEvHandlerName");
			String tableClassName = n.valueOf("@msprop:Generator_TableClassName");
			String tableVarName = n.valueOf("@msprop:Generator_TableVarName");
			String rowDeletingName = n.valueOf("@msprop:Generator_RowDeletingName");
			String tablePropName = n.valueOf("@msprop:Generator_TablePropName");
			
			
			println("-------------------------");
			println(	
					"name:\t", name, Checker.LINE,
					"UserTableName:\t", userTableName,Checker.LINE,
					"RowDeletedName:\t", rowDeletedName,Checker.LINE,
					"RowChangedName:\t", rowChangedName,Checker.LINE,
					"RowClassName:\t", rowClassName,Checker.LINE,
					"RowChangingName:\t", rowChangingName,Checker.LINE,
					"RowEvArgName:\t", rowEvArgName,Checker.LINE,
					"RowEvHandlerName:\t", rowEvHandlerName,Checker.LINE,
					"TableClassName:\t", tableClassName,Checker.LINE,
					"TableVarName:\t", tableVarName,Checker.LINE,
					"RowDeletingName:\t", rowDeletingName,Checker.LINE,
					"TablePropName:\t", tablePropName
					);
			
			
			String result = chk.checkRow(name,
					userTableName, 
					rowDeletedName, 
					rowChangedName,
					rowClassName, 
					rowChangingName, 
					rowEvArgName, 
					rowEvHandlerName, 
					tableClassName, 
					tableVarName, 
					rowDeletingName, 
					tablePropName);
			
			datasets[idx].setRemark(result);
			if (!chk.isRight(result)) {
				warnln("*************" +  name + ":\r\n" + result + "*************");
			} else {
				warnln("Right");
			}
			ExcelUtil.sleep(200);
			warnln(datasets[idx]);
		}
		return datasets;
	}

	private static List<Module> analyseColumn(Document doc, Checker chk, String xpathExpression) {
		List<?> lists = null;
		try {
			lists = doc.selectNodes(xpathExpression);
		} catch (Exception e) {
			// ignore.
			warnln("No Element //xs:sequence//xs:element ");
			return null;
		}
		
		if (TanUtil.isEmpty(lists)) {
			TanUtil.warnln("empty lists");
			return null;
		}
		
		List<Module> modules = new ArrayList<Module>();
		int index = 0;
		for (Iterator<?> iter = lists.iterator(); iter.hasNext();) {
			int idx = ++index;
			Node column = (Node) iter.next();
			
			Module module = new Module();
			
			// 項目名.											
			String name = column.valueOf("@name");
			String userColumnName = column.valueOf("@msprop:Generator_UserColumnName");
			String columnPropNameInRow = column.valueOf("@msprop:Generator_ColumnPropNameInRow");
			String varNameInTable = column.valueOf("@msprop:Generator_ColumnVarNameInTable");
			String propNameInTable = column.valueOf("@msprop:Generator_ColumnPropNameInTable");
			String nullValue = column.valueOf("@msprop:nullValue");
			// データ型.											
			String type = column.valueOf("@type");
			String minOccurs = column.valueOf("@minOccurs");
			
			
			module.setNo(idx);
			module.setName(name);
			module.setId(name);
			module.setDataType(type);
			
//			println("name: ", name);
//			println("msprop:Generator_UserColumnName:", userColumnName);
//			println("msprop:Generator_ColumnPropNameInRow:", columnPropNameInRow);
//			println("msprop:Generator_ColumnVarNameInTable :", varNameInTable);
//			println("msprop:Generator_ColumnPropNameInTable :", propNameInTable);
//			println("nullValue :", nullValue);
//			println("type :", type);
//			println("minOccurs :", minOccurs);
			
			String result = chk.checkColumn(
					name, 
					userColumnName,
					columnPropNameInRow, 
					varNameInTable,
					propNameInTable, 
					nullValue, 
					type,
					minOccurs);
			module.setRemark(result);
			
			
//			if (!chk.isRight(result)) {
//				// warnln("*************" +  name + ":\r\n" + result + "*************");
//			}
			
			modules.add(module);
		}
		return modules;
	}
	
	
	private static List<Module> analyseColumn(Node n, Checker chk, boolean logger) {
		Document doc = n.getDocument();
		List<?> lists = doc.selectNodes("//xs:sequence//xs:element");
		
		if (TanUtil.isEmpty(lists)) {
			TanUtil.warnln("empty lists");
		}
		
		List<Module> modules = new ArrayList<Module>();
		
		int index = 0;
		for (Iterator<?> iter = lists.iterator(); iter.hasNext();) {
			int idx = ++index;
			Node column = (Node) iter.next();
			
			Module module = new Module();
			
			// 項目名.											
			String name = column.valueOf("@name");
			String userColumnName = column.valueOf("@msprop:Generator_UserColumnName");
			String columnPropNameInRow = column.valueOf("@msprop:Generator_ColumnPropNameInRow");
			String varNameInTable = column.valueOf("@msprop:Generator_ColumnVarNameInTable");
			String propNameInTable = column.valueOf("@msprop:Generator_ColumnPropNameInTable");
			String nullValue = column.valueOf("@msprop:nullValue");
			// データ型.											
			String type = column.valueOf("@type");
			String minOccurs = column.valueOf("@minOccurs");
			
			
			module.setNo(idx);
			module.setName(name);
			module.setId(name);
			module.setDataType(type);
			
//			println("name: ", name);
//			println("msprop:Generator_UserColumnName:", userColumnName);
//			println("msprop:Generator_ColumnPropNameInRow:", columnPropNameInRow);
//			println("msprop:Generator_ColumnVarNameInTable :", varNameInTable);
//			println("msprop:Generator_ColumnPropNameInTable :", propNameInTable);
//			println("nullValue :", nullValue);
//			println("type :", type);
//			println("minOccurs :", minOccurs);
			
			String result = chk.checkColumn(
					name, 
					userColumnName,
					columnPropNameInRow, 
					varNameInTable,
					propNameInTable, 
					nullValue, 
					type,
					minOccurs);
			module.setRemark(result);
			
			
			if (!chk.isRight(result)) {
				// warnln("*************" +  name + ":\r\n" + result + "*************");
			}
			warnln(module);
			modules.add(module);
		}
		
		return modules;
	}



	public static void printNodeInfo(Node n) {
		println("Name :", n.getName());
		println("NodeType :", n.getNodeType());
		println("NodeTypeName :", n.getNodeTypeName());
		println("Path :", n.getPath());
		println("StringValue :", n.getStringValue());
		println("Text :", n.getText());
		println("UniquePath :", n.getUniquePath());
	}

	final public static void checkList(List<?> lists) {
		checkList(null, lists);
	}

	final public static void checkList(String msg, List<?> lists) {
		if (TanUtil.isEmpty(lists)) {
			warnln(msg, "未查询到,List为空,程序终止.");
			System.exit(0);
		}
	}

	final public static Document getDocument(String xmlPath) {
		if (isEmpty(xmlPath)) {
			warnln("传入的路径有误!");
			return null;
		}
		File f = new File(xmlPath);
		return getDocument(f);
	}
	
	final public static Document getDocument(final File f) {
		SAXReader reader = new SAXReader();

		Document doc = null;
		try {
			doc = reader.read(f);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc;
	}	

}
