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

/**
 * A static utility class used across the project.
 * 
 * @author Adarsh
 * 
 * @version 1.0, 2005
 * 
 * @version 2.0, 14th April 2006
 */
public class Util {
	/**
	 * Determines if a string is an invalid string.
	 * 
	 * @param string
	 *            the input to be tested.
	 * @return <tt>true</tt> if invalid.
	 */
	public static boolean isNullString(String string) {
		return string == null || string.equals("");
	}

	/**
	 * Retrieves the default toString JavaDoc.
	 * 
	 * @return a <tt>String</tt> containing the JavaDoc.
	 */
	public static String getDefaultToStringJavaDoc() {
		return ConfigurationXMLParser.retrieveTagContents(
				ConfigurationConstants.TOSTRING_TAG,
				ConfigurationConstants.JAVADOC_TAG);
	}

	/**
	 * Retrieves the default toString implementation.
	 * 
	 * @return a <tt>String</tt> containing the implementation.
	 */
	public static String getDefaultToStringImplementation() {
		return ConfigurationXMLParser.retrieveTagContents(
				ConfigurationConstants.TOSTRING_TAG,
				ConfigurationConstants.IMPLEMENTATION_TAG);
	}

	/**
	 * Retrieves the default toString implementation based on the type.
	 * 
	 * @param type
	 *            a <tt>String</tt> containing the type.
	 * @return a <tt>String</tt> containing the implementation.
	 */
	public static String getDefaultToStringImplementation(String type) {
		return ConfigurationXMLParser.retrieveTagContents(
				ConfigurationConstants.TOSTRING_TAG,
				ConfigurationConstants.IMPLEMENTATION_TAG, type);
	}

	/**
	 * Retrieves the default copy constructor JavaDoc.
	 * 
	 * @return a <tt>String</tt> containing the JavaDoc.
	 */
	public static String getDefaultCopyConJavaDoc() {
		return ConfigurationXMLParser.retrieveTagContents(
				ConfigurationConstants.COPYCON_TAG,
				ConfigurationConstants.JAVADOC_TAG);
	}

	/**
	 * Retrieves the default copy constructor implementation.
	 * 
	 * @return a <tt>String</tt> containing the implementation.
	 */
	public static String getDefaultCopyConImplementation() {
		return ConfigurationXMLParser.retrieveTagContents(
				ConfigurationConstants.COPYCON_TAG,
				ConfigurationConstants.IMPLEMENTATION_TAG);
	}

	/**
	 * Tests if child is a part of parent.
	 * 
	 * @param parent
	 *            the parent <tt>String</tt>.
	 * @param child
	 *            the child <tt>String</tt>.
	 * @return <tt>true</tt> if containment is found.
	 */
	public static boolean contains(String parent, String child) {
		return parent != null && child != null && parent.indexOf(child) > -1;
	}
}
