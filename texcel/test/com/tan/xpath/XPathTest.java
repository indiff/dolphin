package com.tan.xpath;

import static com.tan.util.TanUtil.println;
import static com.tan.util.TanUtil.warnln;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;
import org.junit.Test;

import com.tan.util.XMLUtil;

/**
 * Test the xpath expression.
 * @author dolphin
 *
 * 2010/07/02 16:29:23
 */
public class XPathTest {

	@Test
	/**
	 * select the root element AAA.
	 */
	public void testExample1(){
		Document doc = XMLUtil.getDocument("c:\\xml\\demo1.xml");
		String text = doc.asXML();
		println(text);
		String xpathExpression = "/AAA";
		Node n = doc.selectSingleNode(xpathExpression);
		warnln(' ', "select element: ", n.getName(), n.getUniquePath());
	}

	
	@Test
	/**
	 * select all elements CCC which are children of the root element AAA.
	 */
	public void testExample2(){
		Document doc = XMLUtil.getDocument("c:\\xml\\demo1.xml");
		String text = doc.asXML();
		println(text);
		String xpathExpression = "/AAA/CCC";
		Node n = doc.selectSingleNode(xpathExpression);
		warnln(' ', "select element: ", n.getName(), n.getUniquePath());
	}
	
	@Test
	/**
	 * select all elements BBB.
	 */
	public void testExample3(){
		Document doc = XMLUtil.getDocument("c:\\xml\\demo1.xml");
		String text = doc.asXML();
		println(text);
		String xpathExpression = "//BBB";
		List<?> lists = doc.selectNodes(xpathExpression);
		warnln("BBB 有:", lists.size(), "个");
		for (Iterator<?> i = lists.iterator(); i.hasNext();) {
			Node n = (Node) i.next();
			warnln(' ', "select element: ", n.getName(), n.getUniquePath());
		}
	}
	
	@Test
	/**
	 * select all elements BBB which are children of DDD.
	 */
	public void testExample4(){
		Document doc = XMLUtil.getDocument("c:\\xml\\demo2.xml");
		String text = doc.asXML();
		println(text);
		String xpathExpression = "//DDD/BBB";
		List<?> lists = doc.selectNodes(xpathExpression);
		warnln("//DDD/BBB 有:", lists.size(), "个");
		for (Iterator<?> i = lists.iterator(); i.hasNext();) {
			Node n = (Node) i.next();
			warnln(' ', "select element: ", n.getName(), n.getUniquePath());
		}
	}
	
	@Test
	/**
	 * select all the elements enclosed by /AAA/CCC/DDD
	 */
	public void testExample5(){
		Document doc = XMLUtil.getDocument("c:\\xml\\demo3.xml");
		String text = doc.asXML();
		println(text);
		String xpathExpression = "/AAA/CCC/DDD/*";
		List<?> lists = doc.selectNodes(xpathExpression);
		warnln("/AAA/CCC/DDD/* 有:", lists.size(), "个");
		for (Iterator<?> i = lists.iterator(); i.hasNext();) {
			Node n = (Node) i.next();
			warnln(' ', "select element: ", n.getName(), n.getUniquePath());
		}
	}
	
	@Test
	/**
	 * select all the elements BBB which have 3 ancestors.
	 */
	public void testExample6(){
		Document doc = XMLUtil.getDocument("c:\\xml\\demo3.xml");
		String text = doc.asXML();
		println(text);
		String xpathExpression = "/*/*/*/BBB";
		List<?> lists = doc.selectNodes(xpathExpression);
		warnln("/*/*/*/BBB 有:", lists.size(), "个");
		for (Iterator<?> i = lists.iterator(); i.hasNext();) {
			Node n = (Node) i.next();
			warnln(' ', "select element: ", n.getName(), n.getUniquePath());
		}
	}
	
	@Test
	/**
	 * select all the elements.
	 */
	public void testExample7(){
		Document doc = XMLUtil.getDocument("c:\\xml\\demo3.xml");
		String text = doc.asXML();
		println(text);
		String xpathExpression = "//*";
		List<?> lists = doc.selectNodes(xpathExpression);
		warnln("//* 有:", lists.size(), "个");
		for (Iterator<?> i = lists.iterator(); i.hasNext();) {
			Node n = (Node) i.next();
			warnln(' ', "select element: ", n.getName(), n.getUniquePath());
		}
	}
	
	@Test
	/**
	 * select first BBB child of element AAA.
	 */
	public void testExample8(){
		Document doc = XMLUtil.getDocument("c:\\xml\\demo4.xml");
		String text = doc.asXML();
		println(text);
		String xpathExpression = "/AAA/BBB[1]";
		List<?> lists = doc.selectNodes(xpathExpression);
		warnln("//* 有:", lists.size(), "个");
		for (Iterator<?> i = lists.iterator(); i.hasNext();) {
			Node n = (Node) i.next();
			warnln(' ', "select element: ", n.getName(), n.getUniquePath(), n.valueOf("@name"));
		}
	}
	
	@Test
	/**
	 * select last BBB child of the element of AAA.
	 */
	public void testExample9(){
		Document doc = XMLUtil.getDocument("c:\\xml\\demo4.xml");
		String text = doc.asXML();
		println(text);
		String xpathExpression = "/AAA/BBB[last()]";
		List<?> lists = doc.selectNodes(xpathExpression);
		warnln("//* 有:", lists.size(), "个");
		for (Iterator<?> i = lists.iterator(); i.hasNext();) {
			Node n = (Node) i.next();
			warnln(' ', "select element: ", n.getName(), n.getUniquePath(), n.valueOf("@name"));
		}
	}
	
	@Test
	/**
	 * select the all attribute.
	 */
	public void testExample10(){
		Document doc = XMLUtil.getDocument("c:\\xml\\demo4.xml");
		String text = doc.asXML();
		println(text);
		String xpathExpression = "/AAA/BBB[last()]";
		List<?> lists = doc.selectNodes(xpathExpression);
		warnln("//* 有:", lists.size(), "个");
		for (Iterator<?> i = lists.iterator(); i.hasNext();) {
			Node n = (Node) i.next();
			warnln(' ', "select element: ", n.getName(), n.getUniquePath(), n.valueOf("@name"));
		}
	}
	
	@Test
	/**
	 * select the all attributes id.
	 */
	public void testExample11(){
		Document doc = XMLUtil.getDocument("c:\\xml\\demo5.xml");
		String text = doc.asXML();
		println(text);
		String xpathExpression = "//@id";
		List<?> lists = doc.selectNodes(xpathExpression);
		warnln("//@id 有:", lists.size(), "个");
		for (Iterator<?> i = lists.iterator(); i.hasNext();) {
			Node n = (Node) i.next();
			warnln(' ', "select element: ", n.getName(), n.getUniquePath(), n.valueOf("@name"));
		}
	}	
	
	@Test
	/**
	 * select the all elements EEE which have attribute id.
	 */
	public void testExample12(){
		Document doc = XMLUtil.getDocument("c:\\xml\\demo5.xml");
		String text = doc.asXML();
		println(text);
		String xpathExpression = "//EEE[@id]";
		List<?> lists = doc.selectNodes(xpathExpression);
		warnln("//EEE[@id] 有:", lists.size(), "个");
		for (Iterator<?> i = lists.iterator(); i.hasNext();) {
			Node n = (Node) i.next();
			warnln(' ', "select element: ", n.getName(), n.getUniquePath(), n.valueOf("@name"));
		}
	}
	
	@Test
	/**
	 * select the all elements EEE which have attribute id.
	 */
	public void testExample13(){
		Document doc = XMLUtil.getDocument("c:\\xml\\demo5.xml");
		String text = doc.asXML();
		println(text);
		String xpathExpression = "//EEE[@id]";
		List<?> lists = doc.selectNodes(xpathExpression);
		warnln("//EEE[@id] 有:", lists.size(), "个");
		for (Iterator<?> i = lists.iterator(); i.hasNext();) {
			Node n = (Node) i.next();
			warnln(' ', "select element: ", n.getName(), n.getUniquePath(), n.valueOf("@name"));
		}
	}
	
	@Test
	/**
	 * select all the element which has the attribute id with value 'last'.
	 */
	public void testExample14(){
		Document doc = XMLUtil.getDocument("c:\\xml\\demo5.xml");
		String text = doc.asXML();
		println(text);
		String xpathExpression = "//DDD[@name='last']";
		List<?> lists = doc.selectNodes(xpathExpression);
		warnln("//DDD[@name='last'] 有:", lists.size(), "个");
		for (Iterator<?> i = lists.iterator(); i.hasNext();) {
			Node n = (Node) i.next();
			warnln(' ', "select element: ", n.getName(), n.getUniquePath(), n.valueOf("@name"));
		}
	}
}
