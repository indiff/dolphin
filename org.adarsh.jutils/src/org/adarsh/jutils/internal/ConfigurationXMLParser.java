/*
 * JUtils ToString Generator for Eclipse
 * 
 * Copyright (C) 2007  Adarsh Ramamurthy
 * 
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this library; if not, write to the Free Software Foundation, 
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA.
 * 
 * Plugin Home Page: http://eclipse-jutils.sourceforge.net
 */

package org.adarsh.jutils.internal;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Parses and fetches values from the configuration file which is an XML.
 * 
 * @author Adarsh
 * 
 * @version 1.0, 2005
 * 
 * @version 2.0, 14th April 2006
 * 
 * @version 3.0, 10th December 2006
 */
public class ConfigurationXMLParser {
	/**
	 * Retrieves the content of the child tag if it's found enclosed by the
	 * parent tag.
	 * 
	 * @param parentTag
	 *            the name of the parent tag.
	 * @param childTag
	 *            the name of the child tag.
	 * @return a <tt>String</tt> containing the data.
	 */
	public static String retrieveTagContents(String parentTag, String childTag) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		String contents = "";

		try {
			DocumentBuilder docBuilder = factory.newDocumentBuilder();

			InputStream input = ConfigurationXMLParser.class
					.getResourceAsStream(ConfigurationConstants.DEFAULT_CONFIG_FILE);

			Document document = docBuilder.parse(input);

			NodeList parentNodeList = document.getElementsByTagName(parentTag);

			// there can't be duplicate elements at this level.
			Element parentElement = (Element) parentNodeList.item(0);

			NodeList childNodeList = parentElement
					.getElementsByTagName(childTag);

			Element childElement = (Element) childNodeList.item(0);

			contents = childElement.getFirstChild().getNodeValue();

			input.close();
		} catch (ParserConfigurationException e) {
			Logger.error("Error parsing configuration XML", e);
		} catch (SAXException e) {
			Logger.error("Error parsing configuration XML", e);
		} catch (IOException e) {
			Logger.error("Error parsing configuration XML", e);
		}

		return contents.trim() + "\n";
	}

	/**
	 * Retrieves the content of the child tag if it's found enclosed by the
	 * parent tag, given the type attribute value.
	 * 
	 * @param parentTag
	 *            the name of the parent tag.
	 * @param childTag
	 *            the name of the child tag.
	 * @param type
	 *            the value of the type attribute.
	 * @return a <tt>String</tt> containing the data.
	 */
	public static String retrieveTagContents(String parentTag, String childTag,
			String type) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		String contents = "";

		try {
			DocumentBuilder docBuilder = factory.newDocumentBuilder();

			InputStream input = ConfigurationXMLParser.class
					.getResourceAsStream(ConfigurationConstants.DEFAULT_CONFIG_FILE);

			Document document = docBuilder.parse(input);

			NodeList parentNodeList = document.getElementsByTagName(parentTag);

			// there can't be duplicate elements at this level.
			Element parentElement = (Element) parentNodeList.item(0);

			NodeList childNodeList = parentElement
					.getElementsByTagName(childTag);

			Element childElement = null;

			for (int i = 0; i < childNodeList.getLength(); i++) {
				childElement = (Element) childNodeList.item(i);

				if (type.equals(childElement
						.getAttribute(ConfigurationConstants.TYPE_ATTRIBUTE))) {
					break;
				}
			}

			contents = childElement.getFirstChild().getNodeValue();

			input.close();
		} catch (ParserConfigurationException e) {
			Logger.error("Error parsing configuration XML", e);
		} catch (SAXException e) {
			Logger.error("Error parsing configuration XML", e);
		} catch (IOException e) {
			Logger.error("Error parsing configuration XML", e);
		}

		return contents.trim() + "\n";
	}
}
